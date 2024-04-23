package com.thecommerce.service.impl;

import com.thecommerce.domain.dto.MemberDTO;
import com.thecommerce.domain.entity.Member;
import com.thecommerce.repository.MemberRepository;
import com.thecommerce.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Log4j2
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void addOrUpdateMember(MemberDTO memberDTO) {
        // 회원가입/회원정보수정: C/U
        Member member = Member.builder()
                .id(memberDTO.getId())
                .memberId(memberDTO.getMemberId())
                .memberPassword(memberDTO.getMemberPassword())
                .build();
        memberRepository.save(member);
    }

    @Override
    public MemberDTO getMemberById(Long id) {
        // 나의 회원정보조회: R
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            return new MemberDTO(
                    member.getId(),
                    member.getMemberId(),
                    member.getMemberPassword()
            );
        }
        return null;
    }

    @Override
    public void deleteMemberById(Long memberId) {
        // 회원 탈퇴: D
        memberRepository.deleteById(memberId);
    }
}
