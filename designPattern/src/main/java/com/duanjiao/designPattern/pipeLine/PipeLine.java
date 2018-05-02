package com.duanjiao.designPattern.pipeLine;

public interface PipeLine<IN, OUT> extends Pipe<IN, OUT> {
    void addPipe(Pipe<?,?> pipe);
}