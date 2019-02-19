package com.duanjiao.dpsTest.em;

public enum LoginResultStateEnum {
	
	LOGIN_SUCCESS(0,"登录成功"),
	
	LOGIN_FAILURE_PASSWORD_ERROR(1,"登录失败-密码错误"),
	
	LOGIN_FAILURE_USERNAME_ERROR(2,"登录失败-账号不存在"),
	
	LOGIN_FAILURE_CODE_ERROR(3,"登录失败-验证码错误"),
	
	LOGIN_FAILURE_USER_IS_LOCK(4,"登陆失败-用户锁定");
	
	private int index;
	
	private String describe;
	
	LoginResultStateEnum(int index,String describe){
		this.index = index;
		this.describe =describe;
	}
	
	public static LoginResultStateEnum enumByIndex(int index) {
		for (LoginResultStateEnum type : LoginResultStateEnum.values()) {
			if (index == type.getIndex()) {
				return type;
			}
		}
		return null;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	
}
