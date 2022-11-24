package com.projet.housing.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<Member> listMember() {
        return memberRepository.findAll();
    }

    public Page<Member> getMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
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
