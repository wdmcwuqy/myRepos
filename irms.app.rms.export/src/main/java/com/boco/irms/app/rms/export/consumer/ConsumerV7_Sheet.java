package com.boco.irms.app.rms.export.consumer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.boco.irms.app.rms.export.model.ExportCfg;
import com.boco.irms.app.rms.export.model.ExportExcelHeader;
import com.boco.irms.app.rms.export.model.ExportResultInfo;
import com.boco.irms.app.rms.export.producer.JDBCProducer;
import com.boco.irms.app.rms.export.utils.ExportUtils;

/**
 * 
* @ClassName: ConsumerV7_Sheet
* @Description: 导出 07版本  excel,超过100万后分sheet导出
* @author duanjiao
* @date 2019年2月13日 上午9:59:37
*
 */
public class ConsumerV7_Sheet implements IConsumer<ExportResultInfo>{
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsumerV7_Excel.class);

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
	 * 初始 sheet number
	 */
	private int sheetIndex = 1;
	
	/*
	 * JDBC map key list
	 */
	private List<String> headerIndex;
	
	/*
	 * 导出Excel 表头信息
	 */
	private List<List<String>> headers;
	
	/*
	 * 最后导出生成文件信息
	 */
	private ExportResultInfo exportResult;
	
	private List<String> fileList = new ArrayList<String>();
	
	private V7_SheetWriter sheetWriter;
	
	public ConsumerV7_Sheet(JDBCProducer producer) {
		this.producer = producer;
		initConsumer(producer.getCfg());
	}

	private void initConsumer(ExportCfg cfg) {
		if (cfg == null) {
			throw new IllegalArgumentException("导出Excel配置项为null");
		}
		ExportUtils.checkFilePathIsExist(cfg.getPath());
		builderSheetHeader(cfg);
		exportResult = new ExportResultInfo();
		exportResult.setFilePath(cfg.getPath());
		sheetWriter = new V7_SheetWriter(cfg);
	}
	

	@Override
	public ExportResultInfo call() throws Exception {
		List<List<Object>> arrayData = new ArrayList<List<Object>>((int) (TRIGGERWRITESIZE*1.5)+1);
		try {
			BlockingQueue<Map<String, Object>> blockingQueue = producer.getBlockingQueue();
			int num = 0;
			int listDataSize = (int) ((headerIndex.size()*1.5)+1);
			while (!producer.isFinish() || !blockingQueue.isEmpty()) {
				if(!blockingQueue.isEmpty()) {
					List<Object> listData = new ArrayList<Object>(listDataSize);
					Map<String, Object> data =blockingQueue.take();
					for(String dataIndex:headerIndex) {
						listData.add(data.get(dataIndex));
					}
					arrayData.add(listData);
					num++;
				}
				if (num >= TRIGGERWRITESIZE ) {
					writeSheet(arrayData);
					num = 0;
				}
			}
			if(arrayData.size() != 0) {
				writeSheet(arrayData);
			}
			sheetWriter.finish();
			exportResult.setFileNameList(fileList);
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOG.error("ConsumerV7_Sheet中断Error:"+e.getMessage());
		}catch (Exception err) {
			err.printStackTrace();
			LOG.error("ConsumerV7_Sheet导出 Excel Error:"+err.getMessage());
		}
		return exportResult;
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
	public void writeSheet(List<List<Object>> arrayData) {
		sheetWriter.writeSheet(arrayData);
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
	private String builderExcelFileName() {
		ExportCfg cfg = this.producer.getCfg();
		 return cfg.getPath()+SEPA+cfg.getFileName()+cfg.getExprotType().getSuffix();
	}
	
	/**
	 * 
	* @Title: buildSheetName
	* @Description: 创建导出Sheet名称
	* @param @return
	* @return String
	* @author duanjiao
	* @date 2019年2月13日
	* @throws
	 */
	private String buildSheetName() {
		ExportCfg cfg = this.producer.getCfg();
		return cfg.getSheetName()+this.sheetIndex;
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
	private void builderSheetHeader(ExportCfg cfg) {
		List<ExportExcelHeader> head = cfg.getHeaders();
		headerIndex = new ArrayList<String>((int) (head.size()*1.5)+1);
		headers= new ArrayList<List<String>>();
		for (ExportExcelHeader header : head) {
			List<String> headCoulumn = new ArrayList<String>();
			headCoulumn.add(header.getHeaderName());
			headerIndex.add(header.getDataIndex());
			headers.add(headCoulumn);
		}
	}
	
	private class V7_SheetWriter{
		
		private OutputStream out;
		
		private ExcelWriter writer;
		
		private Sheet sheet;
		
		private int wirteTimes = 0;
		
		public V7_SheetWriter(ExportCfg cfg) {
			init(cfg);
		}
		
		private void init(ExportCfg cfg) {
			String fileName = builderExcelFileName();
			try {
				out = new FileOutputStream(fileName);
				this.writer = EasyExcelFactory.getWriter(out,ExcelTypeEnum.XLSX,true);
				buildSheet();
				fileList.add(fileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				LOG.error("exprot Sheet创建文件--:"+fileName+",ErrorMessage:"+ e.getMessage());
			}
		}
		
		
		private void buildSheet() {
			Sheet sheet = new Sheet(sheetIndex);
			String sheetName = buildSheetName();
			sheet.setSheetName(sheetName);
			sheet.setHead(headers);
			this.sheet = sheet;
			sheetIndex ++;
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
		public void writeSheet(List<List<Object>> arrayData) {
			if(wirteTimes >= TIMES) {
				buildSheet();
				wirteTimes = 0;
			}
			writer.write1(arrayData, sheet);
			arrayData.clear();
			wirteTimes ++;
		}
		
		public void finish() {
			writer.finish();
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error("ConsumerV7_Excel 内部类V7_ExcelWriter Colse Error:"+e.getMessage());
			}
		}
	}
}
