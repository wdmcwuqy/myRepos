package com.duanjiao.designPattern.pipeLine.impl;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.duanjiao.designPattern.pipeLine.PipeException;



public abstract class AbstractParallePipe<IN, OUT, V> extends AbsractPipe<IN, OUT> {
	
    private final ExecutorService executorService;

    public AbstractParallePipe(BlockingQueue<IN> queue, ExecutorService executorService) {
        super();
        this.executorService = executorService;
    }

    /**
     * 留给子类实现，用于根据指定的输入元素input构造一组子任务
     * @param input
     * @return
     * @throws Exception
     */
    protected abstract List<Callable<V>> buildTasks(IN input) throws Exception;

    /**
     * 留给子类实现，对各个子任务的处理结果进行合并，形成相应输入元素的输出结果
     * @param subTaskResults
     * @return
     * @throws Exception
     */
    protected abstract OUT combineResults(List<Future<V>> subTaskResults) throws Exception;

    /**
     * 以并行的方式执行一组子任务
     * @param tasks
     * @return
     * @throws Exception
     */
    protected List<Future<V>> invokeParallel(List<Callable<V>> tasks) throws Exception{
        return executorService.invokeAll(tasks);
    }


    @Override
    public OUT doProcess(IN input) throws PipeException {
        OUT out = null;
        try {
            out = combineResults(invokeParallel(buildTasks(input)));
        } catch (Exception e) {
            throw new PipeException(this, input, "Task failed", e);
        }
        return out;
    }

}