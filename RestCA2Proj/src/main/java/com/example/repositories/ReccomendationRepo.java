package com.example.repositories;

import com.example.entities.Reccomendations;

import java.util.List;

public interface ReccomendationRepo {
    List<Reccomendations> findAll();
    Reccomendations findById(Long id);
    String delete(Long id);
}
