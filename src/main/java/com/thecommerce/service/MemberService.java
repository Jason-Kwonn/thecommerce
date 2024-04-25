package com.thecommerce.service;

import com.thecommerce.domain.dto.MemberDTO;
import com.thecommerce.domain.dto.SearchDTO;
import com.thecommerce.domain.entity.Member;

import java.util.List;

public interface MemberService {

    // 회원가입: C
    public void addMember(MemberDTO memberDTO);

    // 회원정보수정: U
    public void updateMember(MemberDTO memberDTO);

    // 나의 회원정보조회: R
    public List<MemberDTO> getMemberList(SearchDTO searchDTO);

    // 로그인
    public MemberDTO login(MemberDTO memberDTO);

}
