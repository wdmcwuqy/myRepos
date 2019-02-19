package com.boco.irms.app.rms.export.model;

import java.io.Serializable;
import java.util.List;

public class ExportCfg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4909394716694867692L;

	/**
	 * 导出文件路径
	 */
	private String path;
	/**
	 * 导出文件名
	 */
	private String fileName;
	/*
	 * excel sheet名称
	 */
	private String sheetName;
	
	/*
	 * 导出Sql
	 */
	private String sql;

	/*
	 * 导出Excel表头
	 */
	private List<ExportExcelHeader> headers;

	/*
	 * 预计导出数据量
	 */
	private int estimatedSize;

	/*
	 * 超过 65535 或100万时分文件导出
	 */
	private boolean splitFlag = true;

	/*
	 * 导出文件类型
	 */
	private ExportEnum exprotType = ExportEnum.V7;

	public ExportCfg(String sql, List<ExportExcelHeader> headers, int estimatedSize) {
		if (sql == null || sql.trim().length() == 0) {
			throw new IllegalArgumentException("excel 导出配置项,sql 无效");
		}
		if (headers == null || headers.size() == 0) {
			throw new IllegalArgumentException("excel 导出配置项,headers 无效");
		}
		if (estimatedSize <= 0) {
			throw new IllegalArgumentException("excel 导出配置项,导出Excel预计导出size不能小于0");
		}
		this.sql = sql;
		this.setHeaders(headers);
		this.estimatedSize = estimatedSize;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getEstimatedSize() {
		return estimatedSize;
	}

	public void setEstimatedSize(int estimatedSize) {
		this.estimatedSize = estimatedSize;
	}

	public ExportEnum getExprotType() {
		return exprotType;
	}

	public void setExprotType(ExportEnum exprotType) {
		this.exprotType = exprotType;
	}

	public List<ExportExcelHeader> getHeaders() {
		return headers;
	}

	public void setHeaders(List<ExportExcelHeader> headers) {
		this.headers = headers;
	}

	public boolean isSplitFlag() {
		return splitFlag;
	}

	public void setSplitFlag(boolean splitFlag) {
		this.splitFlag = splitFlag;
	}

	public enum ExportEnum {

		V3("V3", ".xls", "03版本"), 
		V7("V7", ".xlsx", "07版本"),
		CSV("CSV",".csv","CSV");

		private String suffix;

		private String value;

		private String description;

		ExportEnum(String value, String suffix, String description) {
			this.value = value;
			this.setSuffix(suffix);
			this.setDescription(description);
		}

		public static ExportEnum valuesEnum(String value) {
			for (ExportEnum type : ExportEnum.values()) {
				if (value == type.getValue()) {
					return type;
				}
			}
			return null;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getSuffix() {
			return suffix;
		}

		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}
	}
}
