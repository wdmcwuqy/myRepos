package com.boco.irms.app.rms.export.model;

import java.io.Serializable;

/**
 * 
* @ClassName: ExportExcelHeader
* @Description: 导出Excel header 配置项
* @author duanjiao
* @date 2019年1月29日 下午4:31:49
*
 */
public class ExportExcelHeader implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8675817403706421481L;
	
	/*
	 * Excel header 显示名称
	 */
	private String headerName;
	/*
	 * 对应的 数据库中 字段值
	 */
	private String dataIndex;
	
	/*
	 * 格式化显示
	 */
	private String format;
	
	/*
	 * 跨列 表头支持
	 * 当 subHeaders 不为空时,以 subHeaders dataIndex 数据对应
	 */
	//private List<ExportExcelHeader> subHeaders;
	
	public ExportExcelHeader(String headerName,String dataIndex) {
		this.headerName = headerName;
		this.dataIndex = dataIndex;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	/*public List<ExportExcelHeader> getSubHeaders() {
		return subHeaders;
	}

	public void setSubHeaders(List<ExportExcelHeader> subHeaders) {
		this.subHeaders = subHeaders;
	}*/
}
