package com.duanjiao.dpsTest.crossDomainProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.params.HostParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.params.HttpParamsFactory;
import org.springframework.util.StringUtils;

public class BocoHttpParamsFactory implements HttpParamsFactory {

	private RequestParamBean paramBean;

	@SuppressWarnings("unchecked")
	@Override
	public HttpClientParams getDefaultParams() {
		HttpClientParams clientParam = new HttpClientParams(null);
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
		if (!StringUtils.isEmpty(paramBean.getContentType())) {
			headers.add(new Header("Content-Type", paramBean.getContentType()));
		}
		// 设置 请求 header
		clientParam.setParameter(HostParams.DEFAULT_HEADERS, headers);
		// 设置超时时间
		if (!StringUtils.isEmpty(paramBean.getSessionOutTime())) {
			clientParam.setParameter(HttpMethodParams.SO_TIMEOUT, paramBean.getSessionOutTime());
		}
		// 设置请求参数
		if (StringUtils.isEmpty(paramBean.getRequestParam())) {
			if (this.paramBean.getRequestParam() instanceof Map) {
				for (Map.Entry<String, String> entry : ((Map<String, String>) this.paramBean.getRequestParam())
						.entrySet()) {
					clientParam.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		return clientParam;
	}

	public RequestParamBean getParamBean() {
		return paramBean;
	}

	public void setParamBean(RequestParamBean paramBean) {
		this.paramBean = paramBean;
	}
}
