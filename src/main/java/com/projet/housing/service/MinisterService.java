package com.projet.housing.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.projet.housing.db.MinisterRepository;
import com.projet.housing.model.Minister;

public class MinisterService {
    @Autowired
    private MinisterRepository ministerRepository;

    public Optional<Minister> getMinister(String id) {
        return ministerRepository.findById(id);
    }
    
    public Iterable<Minister> getMinisters() {
        return ministerRepository.findAll();
    }

    public void deleteMinister(String id) {
        ministerRepository.deleteById(id);
    }

    public Minister saveMinister(Minister minister) {
        Minister savedMinister = ministerRepository.save(minister);
        return savedMinister;
    }
}
