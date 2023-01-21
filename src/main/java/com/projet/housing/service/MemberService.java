package com.projet.housing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projet.housing.db.MemberRepository;
import com.projet.housing.db.MinisterRepository;
import com.projet.housing.model.Member;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MinisterRepository ministerRepository;

    @Autowired
    EntityManager em;

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

    public Page<Member> getSearchMembers(String motCle, Pageable pageable) {
        return memberRepository.checkIfMemberExist(motCle, pageable);
    }

    /**
     * @param motCle
     * @param sexe
     * @param minister
     * @param pageable
     * @return
     */
    public Page<Member> getSearchMembersMultiCriteria(String motCle, String sexe, String minister, Pageable pageable) {
        return memberRepository.checkIfMemberExistMultiCriteria(motCle, sexe, minister, pageable);
    }

    public List<Member> getSearchMembersForPrint(String nomPrenom, String sexe, String minister) {
        // return memberRepository.checkIfMemberExistMultiCriteriaPrint(motCle, sexe, minister);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
    
        Root<Member> memb = cq.from(Member.class);
        List<Predicate> predicates = new ArrayList<>();

        if(sexe.isEmpty()) sexe = null;
        if(minister.isEmpty()) minister = null;
        // if(nomPrenom.isEmpty()) nomPrenom = null;
        
        if (sexe != null) {
            predicates.add(cb.equal(memb.get("sexe"), sexe));
        }
        if (minister != null) {
            predicates.add(cb.equal(memb.get("ministere"), ministerRepository.findById(minister).get()));
        }
        if (nomPrenom != null) {
            predicates.add(cb.like(memb.get("nom"), "%" + nomPrenom + "%"));
            predicates.add(cb.like(memb.get("prenom"), "%" + nomPrenom + "%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    /**
     * @param nomPrenom
     * @param sexe
     * @param minister
     * @param pageable
     * @return
     */
    public Page<Member> findMembersByNomPrenomSexeAndMinister(String nomPrenom, String sexe, String minister, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
    
        Root<Member> memb = cq.from(Member.class);
        List<Predicate> predicates = new ArrayList<>();

        if(sexe.isEmpty()) sexe = null;
        if(minister.isEmpty()) minister = null;
        //if(nomPrenom.isEmpty()) nomPrenom = null;
        
        if (sexe != null) {
            predicates.add(cb.equal(memb.get("sexe"), sexe));
        }
        if (minister != null) {
            predicates.add(cb.equal(memb.get("ministere"), ministerRepository.findById(minister).get()));
        }
        if (nomPrenom != null) {
            predicates.add(cb.like(memb.get("nom"), "%" + nomPrenom + "%"));
            predicates.add(cb.like(memb.get("prenom"), "%" + nomPrenom + "%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        Page<Member> pages = new PageImpl<Member>(em.createQuery(cq).getResultList(), pageable, em.createQuery(cq).getResultList().size());
        return pages;
    }
 
}
