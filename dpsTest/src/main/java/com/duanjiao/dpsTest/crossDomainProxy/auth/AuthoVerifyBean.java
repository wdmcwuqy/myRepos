package com.duanjiao.dpsTest.crossDomainProxy.auth;

import java.io.Serializable;

public class AuthoVerifyBean implements Serializable{
	
	public static enum AuthoVerifyTypeEnum {
		
		USER_PWD_LOGIN(0,"普通用户名密码登录"),
		
		CAS_LOGIN(1,"CAS登录");

		private int loginType;
		
		private String describe;
		
		AuthoVerifyTypeEnum(int loginType,String describe) {
			this.loginType = loginType;
			this.describe = describe;
		}
		
		public static AuthoVerifyTypeEnum enumByLoginType(int loginType) {
			for (AuthoVerifyTypeEnum type : AuthoVerifyTypeEnum.values()) {
				if (loginType == type.getLoginType()) {
					return type;
				}
			}
			return null;
		}
		
		public int getLoginType() {
			return this.loginType;
		}
		
		public String getDescribe() {
			return this.describe;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String passWord;
	
	private String loginUrl;
	
	private AuthoVerifyTypeEnum loginType;
	
	public AuthoVerifyBean(String userName,String passWord,String loginUrl,int loginType) {
		this.userName =userName;
		this.passWord =passWord;
		this.loginUrl = loginUrl;
		this.loginType = AuthoVerifyTypeEnum.enumByLoginType(loginType);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public AuthoVerifyTypeEnum getLoginType() {
		return loginType;
	}

	public void setLoginType(AuthoVerifyTypeEnum loginType) {
		this.loginType = loginType;
	}
}
