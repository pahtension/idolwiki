package com.example.idolwiki.model.groups.form;

import com.example.idolwiki.model.member.form.MemberViewForm;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GroupViewForm {
    private int seq;
    private String name;
    private String content;
    private String img;
    private String youtube;
    private LocalDateTime regDate;

    List<MemberViewForm> members;

}
