package com.boco.irms.app.rms.export.producer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boco.irms.app.rms.export.model.ExportCfg;
import com.boco.irms.app.rms.export.utils.ExportUtils;

public class JDBCProducer {

	private static final Logger LOG = LoggerFactory.getLogger(JDBCProducer.class);

	private BlockingQueue<Map<String, Object>> blockingQueue;

	private DataSource dataSource;

	private ExportCfg cfg;

	private int fetchSize;

	private volatile boolean finish = false;

	public JDBCProducer(ExportCfg cfg, DataSource dataSource) {
		if (cfg == null) {
			throw new IllegalArgumentException("导出Excel配置项为null");
		}
		this.setCfg(cfg);
		this.dataSource = dataSource;
		this.fetchSize = ExportUtils.getFetchSizeByEstimatedSize(cfg.getEstimatedSize());
		int size = ExportUtils.getQueueSizeByEstimatedSize(cfg.getEstimatedSize());
		this.blockingQueue = new ArrayBlockingQueue<Map<String, Object>>(size);
	}

	public void start() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("JDBC 导出数据开始。。。。。");
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try {
			conn = dataSource.getConnection();
			conn.setReadOnly(true);
			pstmt = conn.prepareStatement(cfg.getSql(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			pstmt.setFetchSize(fetchSize);
			result = pstmt.executeQuery();
			result.setFetchSize(fetchSize);
			ResultSetMetaData rsmd = result.getMetaData();
			int colCount = rsmd.getColumnCount();
			List<String> colNameList = new ArrayList<String>();
			for (int i = 0; i < colCount; i++) {
				colNameList.add(rsmd.getColumnName(i + 1));
			}
			while (result.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < colCount; i++) {
					String key = colNameList.get(i);
					Object value = result.getObject(colNameList.get(i));
					map.put(key, value);
				}
				blockingQueue.put(map);
			}
			LOG.debug("JDBC 读取数据完成。。。。。。");
		} catch (SQLException e) {
			LOG.error("JDBC 获取数据Error:" + e.getMessage());
			throw new RuntimeException("JDBC 获取数据Error:" + e.getMessage());
		} catch (InterruptedException err) {
			LOG.error("JDBC 获取数据中断Error:" + err.getMessage());
			throw new RuntimeException("JDBC 获取数据中断Error:" + err.getMessage());
		} finally {
			if (LOG.isDebugEnabled()) {
				LOG.debug("JDBC 导出数据完成。。。。。");
			}
			this.finish = true;
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (result != null) {
					result.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	public ExportCfg getCfg() {
		return cfg;
	}

	public void setCfg(ExportCfg cfg) {
		this.cfg = cfg;
	}

	public BlockingQueue<Map<String, Object>> getBlockingQueue() {
		return blockingQueue;
	}

	public void setBlockingQueue(BlockingQueue<Map<String, Object>> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}
}
