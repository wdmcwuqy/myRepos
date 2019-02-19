package com.duanjiao.dpsTest.generate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringBufferDeleteTest {
	
	public static final int SQL_LIMIT = 3;
	
	public static List<String> list =Arrays.asList();
	
	
	public static void main(String[] args) {
		
		List<String> list1 = getDynamicSqls("111",list);
		
		list1.size();
	}
	
	public static List<String> getDynamicSqls(String sql,List<String> cuids) {
		List <String> sqlList =new ArrayList<String>(cuids.size()/SQL_LIMIT +1);
		for(int i =0; i < (cuids.size()/SQL_LIMIT+1);i++) {
			StringBuilder  finishSql = new StringBuilder();
			finishSql.append("SELECT * FROM ( ");
			finishSql.append(sql);
			finishSql.append(" ) WHERE CUID IN ( ");
			
			List<String> subList =cuids.subList(i*SQL_LIMIT, (i+1)*SQL_LIMIT > cuids.size()?cuids.size():(i+1)*SQL_LIMIT);
			
			
			for(String cuid :subList) {
				finishSql.append("'");
				finishSql.append(cuid);
				finishSql.append("',");
			}
			finishSql.append(")");
			
			finishSql.deleteCharAt(finishSql.length() -2);
			
			sqlList.add(finishSql.toString());
		}
		return sqlList;
	}
}
