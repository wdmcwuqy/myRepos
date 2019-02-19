package com.duanjiao.dpsTest.crossDomainProxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.duanjiao.dpsTest.crossDomainProxy.auth.AuthorizationStrategy;

public class HttpClientUtils {

	private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtils.class);
	
	public static final String IS_AUTHO_VERFIY = "IS_AUTHO_VERFIY";

	private static final HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

	private final static Map<String, HttpClient> CLIENT_MAP = new HashMap<String, HttpClient>();

	private HttpClientParams clientParam;
	
	private AuthorizationStrategy authoVerifyStrategy;

	private String currentSystemName;

	private RequestParamBean paramBean;

	public HttpClientUtils(RequestParamBean paramBean) {
		URI uri = null;
		try {
			uri = new URI(paramBean.getRemoteUrl());
			this.currentSystemName = uri.getScheme() + uri.getHost() + uri.getPort();
		} catch (Exception e) {
			new IllegalArgumentException("RequestParamBean RemoteUrl不能为空或无效,创建HttpClient失败!");
		}
		HttpClient client = new HttpClient(connectionManager);
		if (CLIENT_MAP.containsKey(currentSystemName)) {
			LOG.warn("已经有创建的HttpClient,创建已忽略!");
		} else {
			CLIENT_MAP.put(currentSystemName, client);
			LOG.info("创建HttpClient成功!");
		}
		this.paramBean = paramBean;

		init();
	}

	private void init() {
		BocoHttpParamsFactory httpParamsFactory = new BocoHttpParamsFactory();
		httpParamsFactory.setParamBean(paramBean);
		this.clientParam= httpParamsFactory.getDefaultParams();
	}

	private HttpMethod buildHttpMethod() {
		if (StringUtils.isEmpty(paramBean.getRequestMethod())) {
			new IllegalArgumentException("requestMethod 不能为空,创建HttpMethod失败!");
		}
		String methodName = captureName(this.paramBean.getRequestMethod().toLowerCase());
		HttpMethod method = null;
		try {
			method = (HttpMethod) Class.forName("org.apache.commons.httpclient.methods." + methodName + "Method")
					.getConstructor(String.class).newInstance(this.paramBean.getRemoteUrl());
			method.setParams(this.clientParam);
		} catch (Exception e) {
			LOG.error("反射创建HttpMethod ERROR:" + e.getMessage());
		}
		return method;
	}

	public int execute(HttpServletResponse response) {
		HttpClient client = CLIENT_MAP.get(currentSystemName);
		HttpMethod method = buildHttpMethod();
		HttpClientResponseSwapper responseSwapper = new HttpClientResponseSwapper(client,method,response);
		int result = 0;
		try {
			result = client.executeMethod(method);
			switch (result) {
			// 200 成功
			case HttpStatus.SC_OK:
				responseSwapper.writeResponse();
				LOG.info("HttpClient executeMethod 处理成功!");
				break;
			// 302 暂时性重新向
			case HttpStatus.SC_MOVED_TEMPORARILY:
				if(client.getParams().getBooleanParameter(IS_AUTHO_VERFIY, false)) {
					responseSwapper.writeResponse();
				}else {
					client.getParams().setBooleanParameter(IS_AUTHO_VERFIY, true);
					
				}
				LOG.info("HttpClient executeMethod 处理失败,302 出现重定向!");
				break;
			// 400 请求错误
			case HttpStatus.SC_BAD_REQUEST:
				responseSwapper.writeResponse();
				LOG.info("HttpClient executeMethod 处理失败,400  请求错误!");
				break;
			// 500 服务错误
			case HttpStatus.SC_INTERNAL_SERVER_ERROR:
				responseSwapper.writeResponse();
				LOG.info("HttpClient executeMethod 处理失败,500  服务错误!");
				break;
			default:
				responseSwapper.writeResponse();
				LOG.info("HttpClient executeMethod 处理失败,其他服务错误!");
				break;
			}
		} catch (HttpException e) {
			LOG.error("HttpClient executeMethod 错误:" + e.getMessage());
		} catch (IOException e) {
			LOG.error("HttpClient executeMethod 错误:" + e.getMessage());
		}
		return result;
	}

	private static String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}

	public HttpClientParams getClientParam() {
		return clientParam;
	}

	public void setClientParam(HttpClientParams clientParam) {
		this.clientParam = clientParam;
	}

	public String getCurrentSystemName() {
		return currentSystemName;
	}

	public void setCurrentSystemName(String currentSystemName) {
		this.currentSystemName = currentSystemName;
	}

	public RequestParamBean getParamBean() {
		return paramBean;
	}

	public void setParamBean(RequestParamBean paramBean) {
		this.paramBean = paramBean;
	}
	
	private static final class HttpClientResponseSwapper {
		
		private final HttpClient client;

		private final HttpMethod method;

		/**
		 * 不使用 ServletResponse 
		 * 使用 HttpServletResponse
		 */
		private final HttpServletResponse response;

		public HttpClientResponseSwapper(final HttpClient client, final HttpMethod method, final HttpServletResponse response) {
			this.client = client;
			this.method = method;
			this.response = response;
		}

		public void writeResponse() {
			//response header
			Header[] headers= method.getResponseHeaders();
			for(Header header:headers) {
				response.addHeader(header.getName(), header.getValue());
			}
			//response cookie
			Cookie[] cookies = client.getState().getCookies();
			for(Cookie cookie:cookies) {
				javax.servlet.http.Cookie resposneCookie = new javax.servlet.http.Cookie(cookie.getName(),cookie.getValue());
				response.addCookie(resposneCookie);
			}
			//response body 
			OutputStream outputStream =null;
			InputStream inputStream = null;
			
			try {
				 outputStream = response.getOutputStream();
				 inputStream = method.getResponseBodyAsStream();
				int c;
				while ((c = inputStream.read()) != -1) {
					outputStream.write(c);
				}
				outputStream.flush();
			} catch (IOException e) {
				LOG.error("HttpClient 转换 HttpServletResponse Error:"+e.getMessage());
			}finally {
				try {
					outputStream.close();
					inputStream.close();
				} catch (IOException e) {
					LOG.error("HttpClient 转换 HttpServletResponse Stream Close ERROR:"+e.getMessage());
				}
				method.releaseConnection();
			}
		}

	}
}
