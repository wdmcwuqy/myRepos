package com.duanjiao.designPattern.pipeLine;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.duanjiao.designPattern.pipeLine.bo.AssembleDataBO;
import com.duanjiao.designPattern.pipeLine.bo.FilterDataBO;
import com.duanjiao.designPattern.pipeLine.impl.SimplePipeline;

public class ThreadPoolBasedPipeExample {
	
    public static void main(String[] args) {
    	
    	/*
    	 * 线程执行者
    	 */
        final ThreadPoolExecutor threadPoolExecutor;
        
        {
        	 threadPoolExecutor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors()*2, 60, TimeUnit.MINUTES,
                     new SynchronousQueue<Runnable>(), 
                     new ThreadPoolExecutor.CallerRunsPolicy());
        }
       
        /*
         * 简单 管道线
         */
        final SimplePipeline<String, String> pipeLine = new SimplePipeline<String, String>();
        
/*        
         * 第一个处理
         
        Pipe<String, String> pipe = new AbsractPipe<String, String>() {
            @Override
            public String doProcess(String input) throws PipeException {
                String result = input + "->[pipe1, " + Thread.currentThread().getName() + "]";
                System.out.println(result);
                return result;
            }
        };

        pipeLine.addAsThreadBasedPipe(pipe, threadPoolExecutor);
        
        
         * 第二个处理
         
        pipe = new AbsractPipe<String, String>() {
            @Override
            public String doProcess(String input) throws PipeException {
                String result = input + "->[pipe2, " + Thread.currentThread().getName() + "]";
                System.out.println(result);

                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return result;
            }
        };

        pipeLine.addAsThreadBasedPipe(pipe, threadPoolExecutor);
        
        
         * 第三个处理
         
        pipe = new AbsractPipe<String, String>() {
            @Override
            public String doProcess(String input) throws PipeException {
                String result = input + "->[pipe3, " + Thread.currentThread().getName() + "]";
                System.out.println(result);

                try {
                    Thread.sleep(new Random().nextInt(200));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return result;
            }

            @Override
            public void shutdown(long timeout, TimeUnit unit) {
                threadPoolExecutor.shutdown();

                try {
                    threadPoolExecutor.awaitTermination(timeout, unit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        pipeLine.addAsThreadBasedPipe(pipe, threadPoolExecutor);*/
        
        
        
        Pipe<String, String> filterPipe = new FilterDataBO();
        
        Pipe<String, String> assemblePipe = new AssembleDataBO();
        
        
        pipeLine.addAsThreadBasedPipe(filterPipe, threadPoolExecutor);
        pipeLine.addAsThreadBasedPipe(assemblePipe, threadPoolExecutor);
        /*
         * 
         */
        pipeLine.init(pipeLine.newDefaultPipeContext());

        int N = 10;
        try {
            for(int i=0; i<N; i++){
                pipeLine.process("Task-" + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pipeLine.shutdown(10, TimeUnit.SECONDS);
        
    }
}