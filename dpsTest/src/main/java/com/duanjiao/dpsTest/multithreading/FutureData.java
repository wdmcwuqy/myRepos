package com.duanjiao.dpsTest.multithreading;


public class FutureData implements Data{
    private RealData realData;
    private boolean isReady = false;

    public synchronized void setRealData(RealData realData){
        //如果已经装载完毕则直接返回
        if(isReady){
            return;
        }
        //如果未装载，进行装载真实数据
        this.realData = realData;
        isReady = true;
        //通知
        this.notify();
    }

    @Override 
    public synchronized String getRequest() {
        //如果未装载好一直处于阻塞状态
        while (!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //装载好直接返回数据即可
        return this.realData.getRequest();
    }
}