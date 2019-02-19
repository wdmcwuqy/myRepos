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
* @ClassName: ConsumerV3_Excel
* @Description: 导出 03版本  excel,超过65500后分Excel导出
* @author duanjiao
* @date 2019年1月31日 下午3:22:24
*
 */
public class ConsumerV3_Excel implements IConsumer<ExportResultInfo>{
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsumerV3_Excel.class);

	private static final String SEPA = java.io.File.separator;

	private volatile JDBCProducer producer;

	private static final int TRIGGERWRITESIZE = 65500;
	
	/*
	 * 默认初始Excel 标示,防止超量,分excel 使用后缀
	 */
	private int excelIndex = 1;
	
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
	
	public ConsumerV3_Excel(JDBCProducer producer) {
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
	}
	

	@Override
	public ExportResultInfo call() throws Exception {
		List<List<Object>> arrayData = new ArrayList<List<Object>>((int) (TRIGGERWRITESIZE*1.5)+1);
		List<String> fileList = new ArrayList<String>();
		try {
			BlockingQueue<Map<String, Object>> blockingQueue = producer.getBlockingQueue();
			int listDataSize = (int) ((headerIndex.size()*1.5)+1);
			int num = 0;
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
					writeExcel(arrayData,fileList);
					num = 0;
				}
			}
			if(arrayData.size() != 0) {
				writeExcel(arrayData,fileList);
			}
			exportResult.setFileNameList(fileList);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
	public void writeExcel(List<List<Object>> arrayData,List<String> fileList) {
		OutputStream out = null;
		String fileName = builderExcelFileName();
		try {
			out = new FileOutputStream(fileName);
			ExcelWriter writer = EasyExcelFactory.getWriter(out,ExcelTypeEnum.XLS,true);
			Sheet sheet = new Sheet(1);
			sheet.setSheetName(this.producer.getCfg().getSheetName());
			sheet.setHead(headers);
			writer.write1(arrayData, sheet);
			writer.finish();
			excelIndex ++;
			fileList.add(fileName);
			arrayData.clear();
			LOG.debug("ExportConsumerV3_Sheet 写入文件。。。。。");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOG.error("exprot Excel创建文件--:"+fileName+",ErrorMessage:"+ e.getMessage());
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	private String builderExcelFileName() {
		ExportCfg cfg = this.producer.getCfg();
		 return cfg.getPath()+SEPA+cfg.getSheetName()+this.excelIndex+cfg.getExprotType().getSuffix();
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
}
