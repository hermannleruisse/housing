package com.projet.housing.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.housing.db.MemberRepository;
import com.projet.housing.model.Member;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Optional<Member> getMember(String id) {
        return memberRepository.findById(id);
    }
    
    public Iterable<Member> getMembers() {
        return memberRepository.findAll();
    }

    public void deleteMember(String id) {
        memberRepository.deleteById(id);
    }

    public Member saveMember(Member member) {
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public Iterable<Member> getSearchMembers(String motCle) {
        return memberRepository.checkIfMemberExist(motCle);
    }
}
