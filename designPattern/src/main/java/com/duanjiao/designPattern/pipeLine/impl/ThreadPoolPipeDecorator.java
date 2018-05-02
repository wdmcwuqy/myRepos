package com.duanjiao.designPattern.pipeLine.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.duanjiao.designPattern.pipeLine.Pipe;
import com.duanjiao.designPattern.pipeLine.PipeContext;

public class ThreadPoolPipeDecorator<IN, OUT> implements Pipe<IN, OUT> {
	
    private final Pipe<IN, OUT> delegate;
    
    private final TerminationToken terminationToken;
    
    private final ExecutorService executorService;
    
    private final CountDownLatch stageProcessDoneLatch = new CountDownLatch(1);

    public ThreadPoolPipeDecorator(Pipe<IN, OUT> delegate, ExecutorService executorService) {
        super();
        this.delegate = delegate;
        this.executorService = executorService;
        terminationToken = TerminationToken.newInstance(executorService);
    }

    @Override
    public void setNextPipe(Pipe<?, ?> nextPipe) {
        delegate.setNextPipe(nextPipe);
    }

    @Override
    public void process(IN input) throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                int remainingReservations = -1;
                try {
                    delegate.process(input);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    remainingReservations = terminationToken.reservations.decrementAndGet();
                }

                if(terminationToken.isToShutDown()  &&  0 == remainingReservations){
                    //最后一个任务执行结束
                    stageProcessDoneLatch.countDown();
                }
            }
        };

        executorService.submit(task);
        terminationToken.reservations.incrementAndGet();
    }

    @Override
    public void init(PipeContext pipeCtx) {
        delegate.init(pipeCtx);
    }

    @Override
    public void shutdown(long timeout, TimeUnit unit) {
    	
        terminationToken.setToShutDown(true);
        if(terminationToken.reservations.get() > 0){
            try {
                if(stageProcessDoneLatch.getCount() > 0){
                    //保证线程池中的所有任务都已经执行结束才delegate.shutdown
                    stageProcessDoneLatch.await(timeout, unit);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        executorService.shutdown();
        
        delegate.shutdown(timeout, unit);
    }

    private static class TerminationToken {
    	
    	private boolean toShutDown =false;
    	
    	private AtomicInteger reservations = new AtomicInteger();
    	
        private final static Map<ExecutorService, TerminationToken>  INSTANCE_MAP = 
        		new ConcurrentHashMap<ExecutorService, TerminationToken>();

        private TerminationToken(){

        }
 
        static TerminationToken newInstance(ExecutorService executorService){
            TerminationToken token = INSTANCE_MAP.get(executorService);
            if(null == token){
                token = new TerminationToken();
                TerminationToken existingToken = INSTANCE_MAP.putIfAbsent(executorService, token);

                if(null != existingToken){
                    token = existingToken;
                }
            }

            return token;
        }

		public  boolean isToShutDown() {
			return this.toShutDown;
		}

		public void setToShutDown(boolean toShutDown) {
			this.toShutDown = toShutDown;
		}
    }
}
