package com.projet.housing.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet.housing.db.MemberRepository;
import com.projet.housing.dto.ApiError;
import com.projet.housing.dto.MemberDTO;
import com.projet.housing.model.Member;
import com.projet.housing.service.MemberService;


@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
@RequestMapping("/api/manager")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberRepository mRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private Environment environment;

    @RequestMapping("/liste-des-membres")
    public List<Member> listUtilisateur() {
        return this.mRepository.findAll();
    }

    /**
     * Create - Add a new member
     *
     * @param member An object member
     * @return The member object saved
     */
    @PreAuthorize("hasAuthority('PM_ADD_ME')")
    @PostMapping("/save-member")
    public Object createMember(@Valid @RequestBody MemberDTO member) {
        Optional<Member> us = mRepository.checkIfMemberExistByNomAndPrenom(member.getNom(), member.getPrenom());
        if (us.isPresent()) {
            final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    environment.getProperty("unique.membername"), environment.getProperty("unique.membername"));
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        } else {
            return memberService.saveMember(us.get());
        }
    }

    /**
     * Read - Get one member
     *
     * @param id The id of the member
     * @return An Member object full filled
     */
    @GetMapping("/member/{id}")
    public Member getMember(@PathVariable("id") final String id) {
        Optional<Member> member = memberService.getMember(id);
        if (member.isPresent()) {
            return member.get();
        } else {
            return null;
        }
    }

    /**
     * Read - Get all members
     *
     * @return - An Iterable object of Member full filled
     */
    @GetMapping("/members")
    public Iterable<Member> getMembers() {
        return memberService.getMembers();
    }

    /**
     * Update - Update an existing member
     *
     * @param id - The id of the member to update
     * @param member - The member object updated
     * @return
     */
    @PreAuthorize("hasAuthority('PM_EDI_ME')")
    @PutMapping("/edit-member/{id}")
    public Member updateMember(@PathVariable("id") final String id, @Valid @RequestBody MemberDTO member) {
        Optional<Member> e = memberService.getMember(id);
        if (e.isPresent()) {
            Member currentMember = e.get();

            String membername = member.getNom();
            if (membername != null) {
                currentMember.setNom(membername);
            }
            String prenom = member.getPrenom();
            if (prenom != null) {
                currentMember.setPrenom(prenom);
            }
            String sexe = member.getSexe();
            if (sexe != null) {
                currentMember.setSexe(sexe);
            }
            String dateNaiss = member.getDateDeNaissance();
            if (dateNaiss != null) {
                currentMember.setDateDeNaissance(dateNaiss);
            }
            String adress = member.getAdresse();
            if (adress != null) {
                currentMember.setAdresse(adress);
            }
            String tel = member.getTelephone();
            if (tel != null) {
                currentMember.setTelephone(tel);
            }
            String photo = member.getPhoto();
            if (photo != null) {
                currentMember.setPhoto(photo);
            }
            
            memberService.saveMember(currentMember);
            return currentMember;
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete an member
     *
     * @param id - The id of the member to delete
     */
    @PreAuthorize("hasAuthority('PM_DEL_ME')")
    @DeleteMapping("/delete-member/{id}")
    public void deleteMember(@PathVariable("id") final String id) {
        memberService.deleteMember(id);
    }

}
