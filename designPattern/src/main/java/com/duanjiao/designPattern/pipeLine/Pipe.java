package com.duanjiao.designPattern.pipeLine;

import java.util.concurrent.TimeUnit;

public interface Pipe<IN, OUT> {
    /**
     * 设置当前Pipe实例的下个Pipe实例
     * @param nextPipe
     */
    public void setNextPipe(Pipe<?,?> nextKnot);

    /**
     * 对输入的元素进行处理，并将处理结果作为下一个Pipe实例的输入
     * @param input
     * @throws InterruptedException
     */
    public void process(IN input) throws InterruptedException;

    public void init(PipeContext KnotCtx);
    
    public void shutdown(long timeout, TimeUnit unit);
}
