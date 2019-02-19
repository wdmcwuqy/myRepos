package com.duanjiao.dpsTest.crossDomainProxy.auth;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;

import com.duanjiao.dpsTest.crossDomainProxy.HttpClientUtils;
import com.duanjiao.dpsTest.crossDomainProxy.RequestParamBean;

public class CommonUserPWD implements IAuthVerification{
	
	private String loginUrl;
	
	private String userName;
	
	private String passdword;
	
	public CommonUserPWD(String loginUrl,String userName,String passdword) {
		this.loginUrl =loginUrl;
		this.userName =userName;
		this.passdword =passdword;
	}
	
	@Override
	public boolean authVerify(HttpClient client,HttpMethod method,HttpServletResponse response) {
		 Header[] headers = (Header[]) method.getResponseHeaders();
		 for(Header header:headers) {
			 if("Location".equalsIgnoreCase(header.getName()) && header.getValue().equalsIgnoreCase(loginUrl)) {
				 	RequestParamBean requestBean = new RequestParamBean();
					requestBean.setRemoteUrl(loginUrl);
					requestBean.setRequestMethod("POST");
					 HttpClientUtils client302 = new HttpClientUtils(requestBean);
					 client.getState().setCredentials(AuthScope.ANY, 
								new UsernamePasswordCredentials(userName, passdword));
					 client302.execute(response);
					 return true;
			 }
		 }
		 return false;
	}
}
