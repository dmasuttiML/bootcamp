package com.company;

public class Timer {
    private long startTime = 0;
    private long stopTime = 0;

    public void start(){
        startTime = System.currentTimeMillis();
    }

    public void stop(){
        stopTime = System.currentTimeMillis();
    }

    public long elapsedTime(){
        return stopTime - startTime;
    }
}
