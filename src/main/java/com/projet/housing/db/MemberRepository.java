package com.projet.housing.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.housing.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{
    
}
