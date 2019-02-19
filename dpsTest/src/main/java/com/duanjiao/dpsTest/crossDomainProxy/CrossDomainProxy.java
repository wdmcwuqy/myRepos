package com.duanjiao.dpsTest.crossDomainProxy;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class CrossDomainProxy {
	
	private static HttpServletResponse response;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		RequestParamBean requestBean = new RequestParamBean();
		requestBean.setRemoteUrl("http://192.168.8.21:9900/netGuards/TnmsReportInterface/handle.do");
		requestBean.setRequestMethod("POST");
		requestBean.setSessionOutTime(3000);
		JSONObject json= JSONObject.fromObject("{\"interfaceType\":\"NE_SERV_DETAIL_INFO\",\"relatedServCuid\":\"HBAQ_PTN_SERV-8a9e2d0e658a8e1701658c78d89e6934\",\"AnTime\":\"2018-09-05\"}");
		requestBean.setRequestParam(json);
		
		HttpClientUtils client = new HttpClientUtils(requestBean);
		client.execute(response);
	}
}