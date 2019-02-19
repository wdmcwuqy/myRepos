package com.duanjiao.dpsTest.crossDomainProxy.auth;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;

public class AuthorizationStrategy {
	
	private List<IAuthVerification> authVerfyList = new ArrayList<IAuthVerification>();

	public AuthorizationStrategy(IAuthVerification authVerfy) {
		this.authVerfyList.add(authVerfy);
	}
	
	public boolean processAuthVerfy(HttpClient client,HttpMethod method,HttpServletResponse response) {
		for(IAuthVerification authVerfy:authVerfyList) {
			if(authVerfy.authVerify(client, method, response)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addAuthVerfy(IAuthVerification authVerfy) {
		return this.authVerfyList.add(authVerfy);
	}
}
