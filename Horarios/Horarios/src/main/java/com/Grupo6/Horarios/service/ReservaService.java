package com.Grupo6.Horarios.service;

import com.Grupo6.Horarios.model.Grupo;
import com.Grupo6.Horarios.model.Reserva;
import com.Grupo6.Horarios.repository.GrupoRepository;
import com.Grupo6.Horarios.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservaService {

    @Autowired
    private final ReservaRepository reservaRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    public Map<String, List<String>> obtenerReservasMap() {
        List<Reserva> reservas = reservaRepository.findAll();
        Map<String, List<String>> reservasMap = new HashMap<>();
        for (Reserva reserva : reservas) {
            String clave = reserva.getDia() + "-" + reserva.getHorario();
            reservasMap.putIfAbsent(clave, new ArrayList<>());
            reservasMap.get(clave).addAll(reserva.getGruposReservados());
            //reservasMap.put(clave, reserva.getGruposReservados());
        }
        return reservasMap;
    }


    public List<Reserva> obtenerReservasPorHorario(String dia, String horario) {
        return reservaRepository.findByDiaAndHorario(dia, horario);
    }

    public Reserva crearReserva(String dia, String horario, String grupo) {
        Reserva reserva = reservaRepository.findByDiaAndHorario(dia, horario)
                .stream()
                .findFirst()
                .orElse(new Reserva());
        reserva.setDia(dia);
        reserva.setHorario(horario);
        reserva.getGruposReservados().add(grupo);
        return reservaRepository.save(reserva);
    }

    public Grupo establecerPasswordGrupo(String grupo, String password,String encargado,List<String> integrantes) {
        Grupo nuevoGrupo = grupoRepository.findByNombre(grupo);
        if (nuevoGrupo == null) {
            nuevoGrupo = new Grupo();
            nuevoGrupo.setNombre(grupo);
        }
        nuevoGrupo.setPassword(password);

        return grupoRepository.save(nuevoGrupo);

    }

    public boolean autenticarGrupo(String grupo, String password) {
        Grupo grupoExistente = grupoRepository.findByNombre(grupo);
        return grupoExistente != null && grupoExistente.getPassword().equals(password);
    }
    public boolean validarGrupoExiste(String grupo) {
        return grupoRepository.findByNombre(grupo) != null;
    }
    public void borrarTodasLasReservas() {
        reservaRepository.deleteAll();
    }

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva guardarReserva(Reserva reserva) {
        return reservaRepository.save(reserva); // Guarda en MongoDB
    }


}