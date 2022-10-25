package com.projet.housing.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projet.housing.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{
    @Query(value = "SELECT * FROM member u WHERE u.nom = ?1 and u.prenom = ?2", nativeQuery = true)
    Optional<Member> checkIfMemberExistByNomAndPrenom(String nom, String prenom);
}
