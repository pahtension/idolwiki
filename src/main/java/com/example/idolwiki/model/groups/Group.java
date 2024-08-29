package com.example.idolwiki.model.groups;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
    private int seq;
    private String name;
    private String content;
    private String img;
    private String youtube;
    private LocalDateTime regDate;
}
