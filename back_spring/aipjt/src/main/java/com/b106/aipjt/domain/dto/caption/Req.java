package com.b106.aipjt.domain.dto.caption;

import lombok.Data;

@Data // 제네릭타입으로 원하는 JSON형태 생성
public class Req<T> {
    private Header header;
    private T reponseBody;

    @Data
    public static class Header{
        private String resCode;
    }
}
