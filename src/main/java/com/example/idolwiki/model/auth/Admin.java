package com.example.idolwiki.model.auth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Admin { // POJO
    private int seq;
    private String id;
    private String password;
    private LocalDateTime loginDate;
}
