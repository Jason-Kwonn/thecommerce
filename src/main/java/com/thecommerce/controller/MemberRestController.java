package com.thecommerce.controller;

import com.thecommerce.domain.dto.MemberDTO;
import com.thecommerce.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberRestController {

    private final MemberService memberService;

    @Transactional
    @PostMapping("/addMember")
    public ResponseEntity<?> addMember(@RequestBody MemberDTO memberDTO) throws Exception{
        // 회원가입을 한다.
        log.info("/member/addMember : POST");
        memberService.addOrUpdateMember(memberDTO);

        return ResponseEntity.ok("회원가입 완료");
    }

    @PutMapping("/updateMember")
    public ResponseEntity<?> updateMember(@RequestBody MemberDTO memberDTO) throws Exception{
        // 회원정보를 수정한다.
        log.info("/member/updateMember : PUT");
        memberService.addOrUpdateMember(memberDTO);

        return ResponseEntity.ok("회원정보 수정 완료");
    }

    @GetMapping("/getMember/{memberId}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable Long memberId) throws Exception{
        // 회원정보를 조회한다.
        log.info("/member/getMember : GET");
        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }

    @DeleteMapping("/deleteMember/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberId) throws Exception{
        // 회원을 탈퇴한다.
        log.info("/member/deleteMember : DELETE");
        memberService.deleteMemberById(memberId);

        return ResponseEntity.ok("회원탈퇴 완료");
    }

}
