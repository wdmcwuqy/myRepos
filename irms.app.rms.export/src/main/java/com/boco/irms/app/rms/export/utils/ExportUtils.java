package com.boco.irms.app.rms.export.utils;

import java.io.File;

public final class ExportUtils {
	
	/**
	 * 
	* @Title: getFetchSizeByEstimatedSize
	* @Description: 针对不同数据量对 FetchSize 优化
	* @param @param estimatedSize
	* @param @return
	* @return int
	* @author duanjiao
	* @date 2019年1月30日
	* @throws
	 */
	public static int getFetchSizeByEstimatedSize(int estimatedSize) {
		int fetchSize = 10;
		if(estimatedSize >= 500) {
			fetchSize = 100;
		}else if(estimatedSize >= 5000) {
			fetchSize = 1000;
		}else if(estimatedSize >= 5*10000) {
			fetchSize = 2000;
		}else if(estimatedSize >= 10*10000) {
			fetchSize = 10000;
		}
		return fetchSize;
	}
	
	/**
	 * 
	* @Title: getQueueSizeByEstimatedSize
	* @Description: 针对不同数据量对 队列内存  优化
	* @param @param estimatedSize
	* @param @return
	* @return int
	* @author duanjiao
	* @date 2019年1月30日
	* @throws
	 */
	public static int getQueueSizeByEstimatedSize(int estimatedSize) {
		int queueSize = 100;
		if(estimatedSize >= 5000) {
			queueSize = 1000;
		}else if(estimatedSize >= 5*10000) {
			queueSize = 5000;
		}else if(estimatedSize >= 10*10000) {
			queueSize = 10000;
		} else if(estimatedSize >= 100*10000) {
			queueSize = 100000;
		}
		return queueSize;
	}
	
	/**
	 * 
	* @Title: checkFilePathIsExist
	* @Description: 检查文件目录是否存在
	* @param @param filePath
	* @return void
	* @author duanjiao
	* @date 2019年1月31日
	* @throws
	 */
	public static void checkFilePathIsExist(String filePath) {
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
	}

}
