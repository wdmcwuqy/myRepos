package com.duanjiao.dpsTest.crossDomainProxy;

import java.io.Serializable;

public class RequestParamBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3874919042024095707L;
	
	private String remoteUrl;
	
	private String requestMethod;
	
	private Object requestParam;
	
	private String contentType;
	
	private int sessionOutTime;
	
	private boolean autoVerify = false;

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Object getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(Object requestParam) {
		this.requestParam = requestParam;
	}

	public int getSessionOutTime() {
		return sessionOutTime;
	}

	public void setSessionOutTime(int sessionOutTime) {
		this.sessionOutTime = sessionOutTime;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isAutoVerify() {
		return autoVerify;
	}

	public void setAutoVerify(boolean autoVerify) {
		this.autoVerify = autoVerify;
	}
}
