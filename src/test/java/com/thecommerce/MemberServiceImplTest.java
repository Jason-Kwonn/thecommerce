package com.thecommerce;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.thecommerce.domain.dto.MemberDTO;
import com.thecommerce.domain.dto.SearchDTO;
import com.thecommerce.domain.entity.Member;
import com.thecommerce.repository.MemberRepository;
import com.thecommerce.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMember() {
        MemberDTO memberDTO = MemberDTO.builder()
                .id(1L)
                .memberId("user1")
                .password("newPassword")
                .nickName("NickNew")
                .name("NameNew")
                .phoneNumber("01098765432")
                .mailAddress("new@example.com")
                .regDate(LocalDateTime.now())
                .build();


        memberService.addMember(memberDTO);

        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void getMemberById() {
        // Given
        Long memberId = 1L;
        Member mockMember = new Member();
        mockMember.setId(memberId);
        mockMember.setMemberId("user1");
        mockMember.setPassword("password1");
        mockMember.setNickName("nick1");
        mockMember.setName("name1");
        mockMember.setPhoneNumber("01012345678");
        mockMember.setMailAddress("email1@example.com");
        mockMember.setRegDate(LocalDateTime.now());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

        // When
        MemberDTO result = memberService.getMemberById(memberId);

        // Then
        assertNotNull(result, "조회 결과는 null 이 되면 안됀다.");
        assertEquals("user1", result.getMemberId(), "memberId 가 일치해야함.");
        assertEquals("nick1", result.getNickName(), "nickName 이 일치해야함.");
        assertEquals("name1", result.getName(), "name 이 일치해야함.");
        assertEquals("01012345678", result.getPhoneNumber(), "phoneNumber 가 일치해야함.");
        assertEquals("email1@example.com", result.getMailAddress(), "mailAddress 가 일치해야함.");
        assertNotNull(result.getRegDate(), "regDate 는 null 이 되면 안됀다.");
    }

    @Test
    void testUpdateMember() {
        String memberId = "user1";
        Member existingMember = new Member();
        existingMember.setId(1L);
        existingMember.setMemberId(memberId);
        existingMember.setPassword("oldPassword");
        existingMember.setNickName("OldNick");
        existingMember.setName("OldName");
        existingMember.setPhoneNumber("01000000000");
        existingMember.setMailAddress("old@example.com");
        existingMember.setRegDate(LocalDateTime.now());

        MemberDTO memberDTO = MemberDTO.builder()
                .password("newPassword")
                .nickName("NickNew")
                .name("NameNew")
                .phoneNumber("01098765432")
                .mailAddress("new@example.com")
                .build();

        when(memberRepository.findByMemberId(memberId)).thenReturn(existingMember);

        memberService.updateMember(memberId, memberDTO);

        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void testGetMemberList() {
        SearchDTO searchDTO = new SearchDTO(null, 0, 5);
        List<Member> members = Collections.singletonList(new Member(1L, "user1", "password", "Nick1", "Name1", "01012345678", "user1@example.com", LocalDateTime.now()));
        Page<Member> page = new PageImpl<>(members);

        when(memberRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<MemberDTO> memberDTOs = memberService.getMemberList(searchDTO);

        assertFalse(memberDTOs.isEmpty());
        assertEquals(1, memberDTOs.size());
        assertEquals("user1", memberDTOs.get(0).getMemberId());
    }

    @Test
    void testLogin() {
        MemberDTO memberDTO = new MemberDTO(null, "user1", "password", null, null, null, null, null, null);
        Member member = new Member(1L, "user1", "password", "Nick1", "Name1", "01012345678", "user1@example.com", LocalDateTime.now());

        when(memberRepository.findByMemberId("user1")).thenReturn(member);

        MemberDTO loggedMemberDTO = memberService.login(memberDTO);

        assertNotNull(loggedMemberDTO);
        assertEquals("user1", loggedMemberDTO.getMemberId());
        assertEquals("password", loggedMemberDTO.getPassword());
    }
}
