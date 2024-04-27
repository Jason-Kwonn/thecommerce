package com.thecommerce.restcontroller;

import com.thecommerce.domain.dto.MemberDTO;
import com.thecommerce.domain.dto.SearchDTO;
import com.thecommerce.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberRestController {

    @Value("${pageSize}")
    int pageSize;
    private final MemberService memberService;

    @Transactional
    @PostMapping("/join")
    public ResponseEntity<?> addMember(@RequestBody MemberDTO memberDTO) throws Exception{
        // 회원가입을 한다.
        log.info("/api/user/addMember : POST");
        memberService.addMember(memberDTO);

        return ResponseEntity
                .status(201)
                .body("회원가입 완료!");
    }

    @GetMapping("/getMember/{id}")
    public ResponseEntity<?> getMember(@PathVariable Long id) {
        // 회원 정보를 조회한다.
        log.info("/api/user/getMember : GET");
        MemberDTO memberDTO = memberService.getMemberById(id);
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found with ID: " + id);
        }
    }

    @Transactional
    @PutMapping("/updateMember")
    public ResponseEntity<?> updateMember(@RequestParam("memberId") String memberId, @RequestBody MemberDTO memberDTO) throws Exception{
        // 회원정보를 수정한다.
        log.info("/api/user/updateMember : PUT, memberId: {}", memberId);
        memberService.updateMember(memberId, memberDTO);

        return ResponseEntity
                .status(200)
                .body("회원정보 수정 완료!");
    }

    @GetMapping("/list")
    public ResponseEntity<List<MemberDTO>> getMemberList(@ModelAttribute SearchDTO searchDTO) throws Exception{
        // 회원정보를 목록 조회한다.
        log.info("/api/user/getMemberList : GET, searchDTO: {}", searchDTO);
        searchDTO.setPageSize(pageSize);

        return ResponseEntity
                .status(200)
                .body(memberService.getMemberList(searchDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO) {
        log.info("/api/user/login : POST, MemberID: {}", memberDTO.getMemberId());
        MemberDTO returnedMemberDTO = memberService.login(memberDTO);
        if (returnedMemberDTO != null) {
            return ResponseEntity.ok(returnedMemberDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }



}
