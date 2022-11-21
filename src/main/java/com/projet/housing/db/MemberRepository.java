package com.projet.housing.db;

import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projet.housing.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    @Query(value = "SELECT * FROM member u WHERE u.nom = ?1 and u.prenom = ?2", nativeQuery = true)
    Optional<Member> checkIfMemberExistByNomAndPrenom(String nom, String prenom);

    @Query(value = "SELECT * FROM member u WHERE u.nom LIKE %:mot% OR u.prenom LIKE %:mot%", nativeQuery = true)
    Iterable<Member> checkIfMemberExist(@Param("mot") String mot);

    // Pagination
    // @Query(value = "SELECT m FROM Member m ORDER BY nom ASC")
    // Page<Member> findAllMemberWithPagination(Pageable pageable);

    // @Query(value = "SELECT * FROM member ORDER BY nom ASC", countQuery = "SELECT count(*) FROM member", nativeQuery = true)
    // Page<Member> findAllMemberWithPaginationNative(Pageable pageable);
}
