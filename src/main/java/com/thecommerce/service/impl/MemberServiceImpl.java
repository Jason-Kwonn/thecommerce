package com.thecommerce.service.impl;

import com.thecommerce.common.CalculateTimeAgo;
import com.thecommerce.domain.dto.MemberDTO;
import com.thecommerce.domain.dto.SearchDTO;
import com.thecommerce.domain.entity.Member;
import com.thecommerce.repository.MemberRepository;
import com.thecommerce.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

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
    public MemberDTO getMemberById(Long id) {
        log.info("MemberService: getMemberById, id: {}", id);
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            return MemberDTO.builder()
                    .id(member.getId())
                    .memberId(member.getMemberId())
                    .password(member.getPassword())
                    .nickName(member.getNickName())
                    .name(member.getName())
                    .phoneNumber(member.getPhoneNumber())
                    .mailAddress(member.getMailAddress())
                    .regDate(member.getRegDate())
                    .build();
        }
        log.warn("No member found with id: {}", id);
        return null;
    }


    @Override
    public void updateMember(String memberId, MemberDTO memberDTO) {
        log.info("MemberService: updateMember, MemberID: {}", memberId);
        Member existingMember = memberRepository.findByMemberId(memberId);

        if (existingMember != null) {
            Member updatedMember = Member.builder()
                    .id(existingMember.getId())
                    .memberId(memberId)
                    .password(memberDTO.getPassword())
                    .nickName(memberDTO.getNickName())
                    .name(memberDTO.getName())
                    .phoneNumber(memberDTO.getPhoneNumber())
                    .mailAddress(memberDTO.getMailAddress())
                    .regDate(existingMember.getRegDate())
                    .build();
            memberRepository.save(updatedMember);
        }
    }


    @Override
    public List<MemberDTO> getMemberList(SearchDTO searchDTO) {
        // 회원정보 목록 조회
        log.info("MemberService: getMemberList: {}", searchDTO);
        Sort sort = Sort.by(Sort.Order.desc("regDate"), Sort.Order.asc("name"));
        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getPageSize(), sort);

        Page<Member> members;
        if (searchDTO.getSearchKeyword() == null || searchDTO.getSearchKeyword().isEmpty()) {
            members = memberRepository.findAll(pageable);
        } else {
            members = memberRepository.findAllByNameContaining(searchDTO.getSearchKeyword(), pageable);
        }
        log.info("getMemberList DTO build complete");
        return members.getContent().stream().map(member -> MemberDTO.builder()
                        .id(member.getId())
                        .memberId(member.getMemberId())
                        .password(member.getPassword())
                        .nickName(member.getNickName())
                        .name(member.getName())
                        .phoneNumber(member.getPhoneNumber())
                        .mailAddress(member.getMailAddress())
                        .regDate(member.getRegDate())
                        .timeAgo(CalculateTimeAgo.calculateTimeDifferenceString(member.getRegDate()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        Member member = memberRepository.findByMemberId(memberDTO.getMemberId());
        if (member != null && member.getPassword().equals(memberDTO.getPassword())) {
            return MemberDTO.builder()
                    .id(member.getId())
                    .memberId(member.getMemberId())
                    .password(member.getPassword())
                    .nickName(member.getNickName())
                    .name(member.getName())
                    .phoneNumber(member.getPhoneNumber())
                    .mailAddress(member.getMailAddress())
                    .regDate(member.getRegDate())
                    .build();
        } else {
            return null;
        }
    }



}
