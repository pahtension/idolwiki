package com.example.idolwiki.model.groups.form;

import com.example.idolwiki.model.member.form.MemberCreateForm;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupCreateForm {
    private String name;
    private String content;
    private String img;
    private String youtube;

    private List<MemberCreateForm> memberCreateFormList;
}
