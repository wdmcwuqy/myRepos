/*
 * warn
 * 双线程--生成者消费者模式
 * 只能有一个消费者,否则次方法不可行
 * 
 * 消费者分开写，尽量少继承
 *  
 */

package com.boco.irms.app.rms.export.consumer;


import java.util.concurrent.Callable;

/**
 * 
* @ClassName: IConsumer
* @Description: 单线程消费者接口
* @author duanjiao
* @date 2019年2月13日 下午3:07:05
*
* @param <ExportResultInfo>
 */
public interface IConsumer<ExportResultInfo> extends Callable<ExportResultInfo>{

}
