package com.example.idolwiki.model.member;


import com.example.idolwiki.model.member.form.MemberCreateForm;
import com.example.idolwiki.model.member.form.MemberResponseForm;
import com.example.idolwiki.model.member.form.MemberViewForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public void createMember(List<MemberCreateForm> memberCreateFormList, int seq) {
        memberMapper.createMember(memberCreateFormList, seq);
    }

    public List<MemberViewForm> getMembers(Integer seq) {
        return memberMapper.getMembers(seq);
    }

    public void deleteMembers(Integer seq) {
        memberMapper.deleteMember(seq);
    }


}
