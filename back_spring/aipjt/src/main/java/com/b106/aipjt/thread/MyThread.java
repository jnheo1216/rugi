package com.b106.aipjt.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


//실제로 사용하게 될지는 모르지만 예시를 만들어 놓았음
@Slf4j
public class MyThread implements Runnable {

    @Override
    @SneakyThrows(InterruptedException.class)
    public void run() {
        Thread.sleep(200);
        log.error("1");
        Thread.sleep(200);
        log.error("2");
        Thread.sleep(200);
        log.error("3");
        Thread.sleep(200);
        log.error("4");
        Thread.sleep(200);
        log.error("5");
    }
}
/*
    실 사용 예
    @GetMapping("/test")
    public String getUser() {
        Thread myThread1 = new Thread(new MyThread());
        myThread1.start();
        Thread myThread2 = new Thread(new MyThread());
        myThread2.start();
        return "완료";
    }
 */
