package com.thecommerce.service;

import com.thecommerce.domain.dto.MemberDTO;

public interface MemberService {

    // 회원가입/회원정보수정: C/U
    public void addOrUpdateMember(MemberDTO memberDTO);

    // 나의 회원정보조회: R
    public MemberDTO getMemberById(Long id);

    // 회원 탈퇴: D
    public void deleteMemberById(Long id);

}
