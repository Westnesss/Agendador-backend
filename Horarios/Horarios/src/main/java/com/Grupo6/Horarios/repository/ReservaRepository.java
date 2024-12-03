package com.Grupo6.Horarios.repository;

import com.Grupo6.Horarios.model.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReservaRepository extends MongoRepository<Reserva, String> {
    List<Reserva> findByDiaAndHorario(String dia, String horario);
}
