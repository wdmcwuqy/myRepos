package com.boco.irms.app.rms.export;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.boco.core.bean.SpringContextUtil;
import com.boco.irms.app.rms.export.consumer.ConsumerV3_Excel;
import com.boco.irms.app.rms.export.consumer.ConsumerV7_Excel;
import com.boco.irms.app.rms.export.consumer.ConsumerV7_Sheet;
import com.boco.irms.app.rms.export.consumer.Consumer_CSV;
import com.boco.irms.app.rms.export.consumer.IConsumer;
import com.boco.irms.app.rms.export.model.ExportCfg;
import com.boco.irms.app.rms.export.model.ExportResultInfo;
import com.boco.irms.app.rms.export.producer.JDBCProducer;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
* @ClassName: ExportManagerHelper
* @Description: 多线程--大数据量导出helper类
* @author duanjiao
* @date 2019年2月13日 下午3:03:41
*
 */
@Component
public final class ExportManagerHelper {

	private static final Logger LOG = LoggerFactory.getLogger(ExportManagerHelper.class);

	private static final ExecutorService POOLS = Executors.newFixedThreadPool(4);
	
	public static ExportResultInfo export(ExportCfg cfg) {
		return export(cfg,getDefaultDataSource());
	}
	
	public static ExportResultInfo export(ExportCfg cfg,DataSource dataSource) {
		long startTime = System.currentTimeMillis();
		ExportResultInfo result = null;
		IConsumer<ExportResultInfo> consumer = null;
		JDBCProducer producer = new JDBCProducer(cfg, dataSource);
		
		switch (cfg.getExprotType()) {
		case CSV:
			 consumer = new Consumer_CSV(producer);
			break;
		case V3:
			if(cfg.isSplitFlag()) {
				 consumer = new ConsumerV3_Excel(producer);
			}else {
				 consumer = new ConsumerV3_Excel(producer);
				 LOG.warn("----03版Sheet页导出,内存消耗巨大,不建议使用 出,强制切换为分Excel导出----");
			}
			break;
		case V7:
			if(cfg.isSplitFlag()) {
				 consumer = new ConsumerV7_Excel(producer);
			}else {
				 consumer = new ConsumerV7_Sheet(producer);
			}
			break;
		default:
			LOG.warn("不支持的导出类型,请检查版本---");
			break;
		}
		
		Future<ExportResultInfo> future = POOLS.submit(consumer);
		producer.start();
		try {
			result = future.get();
			result.setSuccess(true);
			result.setDescription("导出成功!");
			result.setCostTimeMilli(System.currentTimeMillis() - startTime);
			ObjectMapper mapper = new ObjectMapper();
			LOG.info("ExportResultInfo:" + mapper.writeValueAsString(result));
		} catch (Exception e) {
			if (result == null) {
				result = new ExportResultInfo(false, e.getMessage());
			}
		}
		LOG.info("导出数据总过花费时间:" + (System.currentTimeMillis() - startTime));
		LOG.info("===================================");
		return result;
	}
	
	private static DataSource getDefaultDataSource(){
		return (DataSource)SpringContextUtil.getBean("exportDataSource");
	}
}
