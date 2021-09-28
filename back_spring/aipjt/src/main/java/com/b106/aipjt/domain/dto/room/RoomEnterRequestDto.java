package com.b106.aipjt.domain.dto.room;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomEnterRequestDto {
    @NotEmpty(message = "아바타를 골라야 합니다")
    private String avatar;
    @Size(min = 2, max = 8, message = "닉네임은 2자리 이상, 8자리 이하입니다")
    @NotEmpty(message = "닉네임을 적어주세요")
    private String nickname;
}
