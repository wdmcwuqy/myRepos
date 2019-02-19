package com.duanjiao.dpsTest.crossDomainProxy.auth;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;

public interface IAuthVerification {

	public boolean authVerify(HttpClient client,HttpMethod method,HttpServletResponse response);
		
}
