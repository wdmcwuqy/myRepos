package com.duanjiao.designPattern.pipeLine.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.duanjiao.designPattern.pipeLine.Pipe;
import com.duanjiao.designPattern.pipeLine.PipeContext;

public class WorkThreadPipeDecorator<IN, OUT> implements Pipe<IN, OUT> {
	
    protected final BlockingQueue<IN> workQueue;
    
    protected final Set<AbstractTerminatableThread> workerThreads = new HashSet<AbstractTerminatableThread>();
    
    protected final TerminationToken terminationToken = new TerminationToken();

    private final Pipe<IN, OUT> delegate;

    public WorkThreadPipeDecorator(Pipe<IN, OUT> delegate, int workerCount){
        this(new SynchronousQueue<IN>(), delegate, workerCount);
    }

    public WorkThreadPipeDecorator(BlockingQueue<IN> workQueue, Pipe<IN, OUT> delegate, int workerCount) {
        if(workerCount <= 0){
            throw new IllegalArgumentException("workerCount should be positive!");
        }

        this.workQueue = workQueue;
        this.delegate = delegate;
        for(int i=0; i<workerCount; i++){
            workerThreads.add(new AbstractTerminatableThread() {

                @Override
                protected void doRun() throws Exception {
                    try {
                        dispatch();
                    }finally {
                        terminationToken.reservations.decrementAndGet();
                    }
                }
            });
        }
    }

    private void dispatch() throws InterruptedException {
        IN input = workQueue.take();
        delegate.process(input);
    }

    @Override
    public void setNextPipe(Pipe<?, ?> nextPipe) {
        delegate.setNextPipe(nextPipe);
    }

    @Override
    public void process(IN input) throws InterruptedException {
        workQueue.put(input);
        terminationToken.reservations.incrementAndGet();
    }

    @Override
    public void init(PipeContext pipeCtx) {
        delegate.init(pipeCtx);
        for(AbstractTerminatableThread thread : workerThreads){
            thread.start();
        }
    }

    @Override
    public void shutdown(long timeout, TimeUnit unit) {
        for(AbstractTerminatableThread thread : workerThreads){
            thread.terminate();
            try {
                thread.join(TimeUnit.MILLISECONDS.convert(timeout, unit));
            } catch (InterruptedException e) {
            }
        }
        delegate.shutdown(timeout, unit);
    }
    
  private class AbstractTerminatableThread extends Thread{
	  
	public void run() {
		try {
			doRun();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doRun() throws Exception {
	}
	  
	 protected void terminate(){
		  this.stop();
	  }
  }
    
 private static class TerminationToken {
    	
    	private  boolean toShutDown =false;
    	
    	private  AtomicInteger reservations = new AtomicInteger();
    	
        private final static ConcurrentHashMap<ExecutorService, TerminationToken> 
        
            INSTANCE_MAP = new ConcurrentHashMap<ExecutorService, TerminationToken>();

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
