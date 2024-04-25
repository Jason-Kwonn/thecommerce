package com.thecommerce.service.impl;

import com.thecommerce.common.CalculateTimeAgo;
import com.thecommerce.domain.dto.MemberDTO;
import com.thecommerce.domain.dto.SearchDTO;
import com.thecommerce.domain.entity.Member;
import com.thecommerce.repository.MemberRepository;
import com.thecommerce.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void addMember(MemberDTO memberDTO) {
        // 회원가입: C
        log.info("MemberService: addMember");
        Member member = Member.builder()
                .memberId(memberDTO.getMemberId())
                .password(memberDTO.getPassword())
                .nickName(memberDTO.getNickName())
                .name(memberDTO.getName())
                .phoneNumber(memberDTO.getPhoneNumber())
                .mailAddress(memberDTO.getMailAddress())
                .regDate(LocalDateTime.now())
                .build();
        memberRepository.save(member);
    }

    @Override
    public void updateMember(MemberDTO memberDTO) {
        // 회원정보수정: U
        log.info("MemberService: updateMember");
        Member member = Member.builder()
                .id(memberDTO.getId())
                .memberId(memberDTO.getMemberId())
                .password(memberDTO.getPassword())
                .nickName(memberDTO.getNickName())
                .name(memberDTO.getName())
                .phoneNumber(memberDTO.getPhoneNumber())
                .mailAddress(memberDTO.getMailAddress())
                .build();
        memberRepository.save(member);
    }

    @Override
    public List<MemberDTO> getMemberList(SearchDTO searchDTO) {
        // 회원정보 목록 조회
        log.info("MemberService: getMemberList");
        Sort sort = Sort.by(Sort.Direction.DESC, "regDate"); // 정렬 조건 설정
        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getPageSize(), sort);

        List<Member> members = memberRepository.findAllByNameContaining(searchDTO.getSearchKeyword(), pageable).getContent();
        List<MemberDTO> memberDTOs = new ArrayList<>();
        for (Member member : members) {
            String timeAgo = CalculateTimeAgo.calculateTimeDifferenceString(member.getRegDate());
            MemberDTO dto = MemberDTO.builder()
                    .id(member.getId())
                    .memberId(member.getMemberId())
                    .password(member.getPassword())
                    .nickName(member.getNickName())
                    .name(member.getName())
                    .phoneNumber(member.getPhoneNumber())
                    .mailAddress(member.getMailAddress())
                    .timeAgo(timeAgo)
                    .build();
            memberDTOs.add(dto);
        }
        return memberDTOs;
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        MemberDTO returnedMemberDTO = memberRepository.findByMemberId(memberDTO.getMemberId());

        if (returnedMemberDTO != null && passwordEncoder.matches(memberDTO.getPassword(), returnedMemberDTO.getPassword())) {
            return returnedMemberDTO;
        } else {
            return null;
        }
    }


}
