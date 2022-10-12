package com.projet.housing.db;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projet.housing.model.Minister;

@Repository
public interface MinisterRepository extends JpaRepository<Minister, String>{
    @Query(value = "SELECT * FROM minister p WHERE p.code = ?1", nativeQuery = true)
    Optional<Minister> checkIfMinisterExistByCode(String scode);
}
