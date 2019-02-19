package com.boco.irms.app.rms.export.model;

import java.io.Serializable;
import java.util.List;

public class ExportResultInfo implements Serializable{

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = -5563313609838640213L;
	
	private boolean success;
	
	private String description;
	
	private long costTimeMilli;
	
	private String filePath;
	
	private List<String> fileNameList;
	
	public ExportResultInfo() {
	}
	
	public ExportResultInfo(boolean success,String description) {
		this.success = success;
		this.description = description;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCostTimeMilli() {
		return costTimeMilli;
	}

	public void setCostTimeMilli(long costTimeMilli) {
		this.costTimeMilli = costTimeMilli;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<String> getFileNameList() {
		return fileNameList;
	}

	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}
}
