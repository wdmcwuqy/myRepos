package com.duanjiao.dpsTest.em;

public enum ServiceEnum  implements IService{
	
	USER_SERVICE(1,"用户服务"){
		@Override
		public void service(String name) {
			super.service(name);
			System.out.println("用户服务:"+name);
		}
	},
	
	LOG_SERVICE(2,"日志服务"){
		@Override
		public void service(String name) {
			super.service(name);
			System.out.println("日志服务:"+name);
		}
	},
	
	SMS_SERVICE(3,"短信服务"){
		@Override
		public void service(String name) {
			super.service(name);
			System.out.println("短信服务:"+name);
		}
	};
	
	private int index;
	
	private String description;
	
	private ServiceEnum(int index,String description) {
		this.index =index;
		this.description =description;
	}

	@Override
	public void service(String name) {
		System.out.println("主类:"+name);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
