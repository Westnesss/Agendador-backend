package com.Grupo6.Horarios.repository;

import com.Grupo6.Horarios.model.Grupo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GrupoRepository extends MongoRepository<Grupo, String> {
    Grupo findByNombre(String nombre);
}