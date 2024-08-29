package com.example.idolwiki.model.groups;

import com.example.idolwiki.model.groups.form.*;
import com.example.idolwiki.model.member.MemberService;
import com.example.idolwiki.model.member.form.MemberCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class GroupService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final GroupMapper groupMapper;

    private final MemberService memberService;


    public List<GroupSimpleForm> groupsIndex(int pageStart, String search) {
        return groupMapper.groupsIndex(pageStart, search);
    }

    public GroupViewForm getGroup(Integer seq) {
//        Optional<Group> bySeq = groupMapper.getGroup(seq);
//        return bySeq.orElseThrow(() -> new IllegalArgumentException("(번호 - " + seq + ") 찾지 못했습니다."));
        GroupViewForm groupViewForm = groupMapper.getGroup(seq);
        groupViewForm.setMembers(memberService.getMembers(seq));

        return groupViewForm;
    }


    // CREATE
    public void createGroup(GroupCreateForm groupCreateForm) {
        Group group = Group.builder() // 빌더 패턴
                .name(groupCreateForm.getName())
                .content(groupCreateForm.getContent())
                .img(groupCreateForm.getImg())
                .youtube(groupCreateForm.getYoutube())
                .regDate(LocalDateTime.now())
                .build();

        groupMapper.createGroup(group);
        int groupSeq = groupMapper.lastSeq();

        memberService.createMember(groupCreateForm.getMemberCreateFormList(),groupSeq);
    }

    public GroupCreateForm convertToGroupCreateForm(String name, String content, MultipartFile img, String youtube,
                                                    List<String> memberName, MultipartFile[] memberImg) throws IOException {
        List<MemberCreateForm> memberCreateForms = new ArrayList<>();

        for(int i=0; i < memberName.size(); i++){
            MemberCreateForm memberCreateForm = new MemberCreateForm();
            memberCreateForm.setName(memberName.get(i));

            MultipartFile file = memberImg[i];

            String originalFilename = file.getOriginalFilename();

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + originalFilename);
            Files.write(path, bytes);

            memberCreateForm.setImg(originalFilename);
            memberCreateForms.add(memberCreateForm);
        }

        String originalFilename = img.getOriginalFilename();

        byte[] bytes = img.getBytes();
        Path path = Paths.get(uploadDir + originalFilename);
        Files.write(path, bytes);

        GroupCreateForm groupCreateForm = new GroupCreateForm();
        groupCreateForm.setName(name);
        groupCreateForm.setContent(content);
        groupCreateForm.setImg(originalFilename);
        groupCreateForm.setYoutube(youtube);
        groupCreateForm.setMemberCreateFormList(memberCreateForms);

        return groupCreateForm;
    }


    // UPDATE
    public void updateGroup(GroupUpdateForm groupUpdateForm, Integer seq) {

        Group group = Group.builder() // 빌더 패턴
                .name(groupUpdateForm.getName())
                .content(groupUpdateForm.getContent())
                .img(groupUpdateForm.getImg())
                .youtube(groupUpdateForm.getYoutube())
                .regDate(LocalDateTime.now())
                .build();

        groupMapper.updateGroup(group, seq);

        memberService.deleteMembers(seq);
        memberService.createMember(groupUpdateForm.getMemberCreateFormList(),seq);
    }
    public GroupUpdateForm convertToGroupUpdateForm(String name, String content, MultipartFile img,
                                                    String youtube, List<String> memberName,
                                                    MultipartFile[] memberImg) throws IOException{

        List<MemberCreateForm> memberCreateForms = new ArrayList<>();

        for(int i=0; i < memberName.size(); i++){
            MemberCreateForm memberCreateForm = new MemberCreateForm();
            memberCreateForm.setName(memberName.get(i));

            MultipartFile file = memberImg[i];

            String originalFilename = file.getOriginalFilename();

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + originalFilename);
            Files.write(path, bytes);

            memberCreateForm.setImg(originalFilename);

            memberCreateForms.add(memberCreateForm);
        }

        String originalFilename = img.getOriginalFilename();

        byte[] bytes = img.getBytes();
        Path path = Paths.get(uploadDir + originalFilename);
        Files.write(path, bytes);

        GroupUpdateForm groupUpdateForm = new GroupUpdateForm();
        groupUpdateForm.setName(name);
        groupUpdateForm.setContent(content);
        groupUpdateForm.setImg(originalFilename);
        groupUpdateForm.setYoutube(youtube);
        groupUpdateForm.setMemberCreateFormList(memberCreateForms);

        return groupUpdateForm;
    }

    public void deleteGroup(Integer seq) {
        memberService.deleteMembers(seq);
        groupMapper.deleteGroup(seq);
    }
}