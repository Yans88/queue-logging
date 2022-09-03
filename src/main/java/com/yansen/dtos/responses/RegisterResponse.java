package com.yansen.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RegisterResponse implements Serializable {
    private Long memberId;
    private String username;
    private String email;
    private String password;
}
