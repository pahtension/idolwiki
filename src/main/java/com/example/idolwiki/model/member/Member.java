package com.example.idolwiki.model.member;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    private int seq;

    private String name;

    private String img;

    private int groups_seq;

}