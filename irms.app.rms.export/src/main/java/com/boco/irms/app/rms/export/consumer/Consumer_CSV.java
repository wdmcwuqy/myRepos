package com.boco.irms.app.rms.export.consumer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boco.irms.app.rms.export.model.ExportCfg;
import com.boco.irms.app.rms.export.model.ExportExcelHeader;
import com.boco.irms.app.rms.export.model.ExportResultInfo;
import com.boco.irms.app.rms.export.producer.JDBCProducer;
import com.boco.irms.app.rms.export.utils.ExportUtils;

public class Consumer_CSV implements IConsumer<ExportResultInfo>{

	private static final Logger LOG = LoggerFactory.getLogger(Consumer_CSV.class);

	private static final String SEPA = java.io.File.separator;

	private volatile JDBCProducer producer;
	
	/*
	 * 静态 最大excel sheet 行数
	 */
	private static final int MAX_SHEET_ROW = 100*10000;
	
	/*
	 *  MAX_SHEET_ROW 分 TIMES 导出到Excel(临时缓存)
	 */
	private static final int TIMES = 20;
	
	/*
	 * 计算刷新到缓存 数据条数
	 */
	private static final int TRIGGERWRITESIZE = MAX_SHEET_ROW/TIMES;
	
	/*
	 * 初始 excel number
	 */
	private int csvIndex = 1;
	
	/*
	 * JDBC map key list
	 */
	private List<String> headerIndex;
	
	/*
	 * 导出Excel 表头信息
	 */
	private StringBuilder headers;
	
	/*
	 * 最后导出生成文件信息
	 */
	private ExportResultInfo exportResult;
	
	/*
	 * 中间过程--导出文件名称 list
	 */
	private List<String> fileList = new ArrayList<String>();
	
	private CsvWriter csvWriter;
	
	public Consumer_CSV(JDBCProducer producer) {
		this.producer = producer;
		initConsumer(producer.getCfg());
	}

	private void initConsumer(ExportCfg cfg) {
		if (cfg == null) {
			throw new IllegalArgumentException("导出CSV配置项为null");
		}
		ExportUtils.checkFilePathIsExist(cfg.getPath());
		builderHeader(cfg);
		exportResult = new ExportResultInfo();
		exportResult.setFilePath(cfg.getPath());
	}
	

	@Override
	public ExportResultInfo call() throws Exception {
		List<String> arrayData = new ArrayList<String>((int) (TRIGGERWRITESIZE*1.5)+1);
		try {
			BlockingQueue<Map<String, Object>> blockingQueue = producer.getBlockingQueue();
			int dataNum = 0;
			while (!producer.isFinish() || !blockingQueue.isEmpty()) {
				if(!blockingQueue.isEmpty()) {
					StringBuilder builder = new StringBuilder();
					Map<String, Object> data =blockingQueue.take();
					for(String dataIndex:headerIndex) {
						builder.append("\""+data.get(dataIndex)+"\",");
					}
					builder.deleteCharAt(builder.length()-1);
					arrayData.add(builder.toString());
					dataNum++;
				}			
				if (dataNum >= TRIGGERWRITESIZE ) {
					writeCSV(arrayData);
					dataNum = 0;
				}
			}
			if(arrayData.size() != 0) {
				writeCSV(arrayData);
			}
			csvWriter.finish();
			exportResult.setFileNameList(fileList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exportResult;
	}
	
	
	/**
	 * 
	* @Title: writeExcelV7
	* @Description: 数据写入Excel 文件
	* @param @param arrayData
	* @return void
	* @author duanjiao
	* @date 2019年1月31日
	* @throws
	 */
	public void writeCSV(List<String> arrayData) {
		if(csvWriter  == null) {
			csvWriter = new CsvWriter(this.producer.getCfg());
			csvIndex ++;
		}
		int times = csvWriter.writeCsv(arrayData);
		if(times >= TIMES) {
			csvWriter.finish();
			csvWriter = null;
		}
	}
	
	/**
	 * 
	* @Title: builderExcelFileName
	* @Description: 创建导出文件名称
	* @param @return
	* @return String
	* @author duanjiao
	* @date 2019年1月30日
	* @throws
	 */
	private String builderFileName() {
		ExportCfg cfg = this.producer.getCfg();
		 return cfg.getPath()+SEPA+cfg.getFileName()+this.csvIndex+cfg.getExprotType().getSuffix();
	}
	/**
	 * 
	* @Title: builderSheetHeader
	* @Description: 创建导出Header
	* @param @param cfg
	* @return void
	* @author duanjiao
	* @date 2019年1月30日
	* @throws
	 */
	private void builderHeader(ExportCfg cfg) {
		List<ExportExcelHeader> head = cfg.getHeaders();
		headerIndex = new ArrayList<String>((int) (head.size()*1.5)+1);
		headers= new StringBuilder();
		for (ExportExcelHeader header : head) {
			headerIndex.add(header.getDataIndex());
			headers.append("\""+header.getHeaderName()+"\",");
		}
		headers.deleteCharAt(headers.length()-1);
	}
	
	private class CsvWriter{
		
		private OutputStream out;
		
		private BufferedWriter writer; 
		
		private int wirteTimes = 0;
		
		public CsvWriter(ExportCfg cfg) {
			init(cfg);
		}
		
		private void init(ExportCfg cfg) {
			byte[] uft8bom={(byte)0xef,(byte)0xbb,(byte)0xbf};//BOM
			String fileName = builderFileName();
			try {
				out = new FileOutputStream(fileName);
				out.write(uft8bom);//先将BOM输出到文件中
				writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
				writeCsvHeader();
				fileList.add(fileName);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("exprot CSV创建文件--:"+fileName+",ErrorMessage:"+ e.getMessage());
			}
		}
		
		private void writeCsvHeader() {
			try {
				writer.write(headers+"\n");
				writer.flush();
			}catch(Exception err) {
				LOG.error("Consumer_CSV 内部类CsvWriter writeCsvHeader Error:"+err.getMessage());
			}
		}
		
		/**
		 * 
		* @Title: writeExcel
		* @Description: 数据写入Excel 文件
		* @param @param arrayData
		* @return void
		* @author duanjiao
		* @date 2019年1月30日
		* @throws
		 */
		public int writeCsv(List<String> arrayData) {
			try {
				for(String data:arrayData) {
					writer.write(data+"\n");
				}
				writer.flush();
				arrayData.clear();
			}catch(Exception err) {
				LOG.error("Consumer_CSV 内部类CsvWriter writeCsv Error:"+err.getMessage());
				throw new RuntimeException("Consumer_CSV 内部类CsvWriter writeCsv Error:"+err.getMessage());
			}
			wirteTimes ++;
			return wirteTimes;
		}
		
		public void finish() {
			try {
				writer.flush();
				writer.close();
				out.close();
				System.gc();
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error("Consumer_CSV 内部类CsvWriter Colse Error:"+e.getMessage());
			}
		}
	}
}
