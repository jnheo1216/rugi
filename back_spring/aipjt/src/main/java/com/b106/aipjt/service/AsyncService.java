package com.b106.aipjt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/*
    서비스에서 Async 메소드를 혼용할 경우 헷갈릴 것이라는 생각에 만든 전용 서비스.
    헷갈림 방지를 위해 Async는 따로 서비스를 분리해서 사용하는 것이 좋아보임. 의견받습니다.
 */
@Slf4j
@Service
public class AsyncService {

    @Async
    public void execute() throws InterruptedException {
        log.info("========================쓰레드 시작========================");
        Thread.sleep(5000);
        log.info("========================쓰레드 종료========================");
    }
}
