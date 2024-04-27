package com.thecommerce.repository;

import com.thecommerce.domain.dto.MemberDTO;
import com.thecommerce.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findAllByNameContaining(String searchKeyword, Pageable pageable);

    Member findByMemberId(String memberId);
}
