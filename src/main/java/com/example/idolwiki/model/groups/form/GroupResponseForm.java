package com.example.idolwiki.model.groups.form;

import com.example.idolwiki.model.member.form.MemberResponseForm;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupResponseForm {
    private int seq;
    private String name;
    private String content;
    private String img;
    private String youtube;

    List<MemberResponseForm> memberResponseForms;

}
