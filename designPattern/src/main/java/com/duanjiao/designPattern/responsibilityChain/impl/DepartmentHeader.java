package com.duanjiao.designPattern.responsibilityChain.impl;

import com.duanjiao.designPattern.responsibilityChain.Chain;
import com.duanjiao.designPattern.responsibilityChain.Ratify;
import com.duanjiao.designPattern.responsibilityChain.pojo.Request;
import com.duanjiao.designPattern.responsibilityChain.pojo.Result;

/**
 * 部门领导
 *
 * @author lzy
 *
 */
public class DepartmentHeader implements Ratify {

     @Override
     public Result deal(Chain chain) {
          Request request = chain.request();
          System.out.println("DepartmentHeader=====>request:"+ request.toString());
          if (request.days() > 7) {
              return new Result(false, "你这个完全没必要");
          }
          return new Result(true, "DepartmentHeader：不要着急，把事情处理完再回来！");
     }

}
