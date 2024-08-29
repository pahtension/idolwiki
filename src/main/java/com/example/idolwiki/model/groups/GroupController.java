package com.example.idolwiki.model.groups;

import com.example.idolwiki.model.groups.form.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GroupController {
    private final GroupService groupService;

    @Value("${file.upload-dir}")
    private String uploadDir;


    // index
    @GetMapping("/common/group")
    public ResponseEntity<?> groupsIndex(@RequestParam("page") Optional<String> page,
                                         @RequestParam("search") Optional<String> search) {
        int pageLengh = 6;
        int currentPage = page.map(p -> {
            try {
                return Math.max(Integer.parseInt(p) * pageLengh - pageLengh, 0);
            } catch (NumberFormatException e) {
                return 0;
            }
        }).orElse(0);

        String currentSearch = search.orElse("");

        try{
            List<GroupSimpleForm> groups = groupService.groupsIndex(currentPage,currentSearch);
            return ResponseEntity.ok(groups);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("페이지 조회 실패");
        }

    }

    @GetMapping("/common/group/{seq}")
    public ResponseEntity<?> getGroup(@PathVariable("seq") Integer seq) {
        try {
//            Group group = groupService.getGroup(seq);
            GroupViewForm groupViewForm = groupService.getGroup(seq);
            return ResponseEntity.ok(groupViewForm);

        } catch (IllegalArgumentException e) {
            log.info("그룹 상세 정보 조회 실패: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/common/group/{seq}")
    public ResponseEntity<?> updateGroup(@PathVariable("seq") Integer seq,
                                         @RequestParam("name") String name,
                                         @RequestParam("content") String content,
                                         @RequestParam("img") MultipartFile img,
                                         @RequestParam("youtube") String youtube,
                                         @RequestParam("memberName") List<String> memberName,
                                         @RequestParam("memberImg") MultipartFile[] memberImg) {
        try {
            GroupUpdateForm groupUpdateForm =
                    groupService.convertToGroupUpdateForm(name, content, img, youtube, memberName, memberImg);
            groupService.updateGroup(groupUpdateForm,seq);
            return ResponseEntity.ok().body(true);
        }catch (IOException e){
            return ResponseEntity.badRequest().body("file업로드 실패");
        }
    }

    @PostMapping("/common/group")
    public ResponseEntity<?> createGroup(@RequestParam("name") String name,
                                         @RequestParam("content") String content,
                                         @RequestParam("img") MultipartFile img,
                                         @RequestParam("youtube") String youtube,
                                         @RequestParam("memberName") List<String> memberName,
                                         @RequestParam("memberImg") MultipartFile[] memberImg) {
        try {
            GroupCreateForm groupCreateForm =
                    groupService.convertToGroupCreateForm(name, content, img, youtube, memberName, memberImg);
            groupService.createGroup(groupCreateForm);
            return ResponseEntity.ok().body(true);
        }catch (IOException e){
            return ResponseEntity.badRequest().body("file업로드 실패");
        }

    }

    @DeleteMapping("/common/group")
    public ResponseEntity<?> deleteGroup(@RequestParam("seq") List<Integer> seq){
        System.out.println("delete");
        for(Integer i : seq){
            groupService.deleteGroup(i);
        }
        return ResponseEntity.ok().body(true);
    }


}
