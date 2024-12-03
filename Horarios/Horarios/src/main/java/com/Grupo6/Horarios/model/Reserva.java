package com.Grupo6.Horarios.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "reservas")
public class Reserva {
    @Id
    private String id;
    private String dia;
    private String horario;
    private LocalDate fecha; // Fecha específica, ej. "2024-12-02"
    private List<String> gruposReservados = new ArrayList<>(); // Lista de grupos que reservaron



    // Getters y setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getGruposReservados() {
        return gruposReservados;
    }

    public void setGruposReservados(List<String> gruposReservados) {
        this.gruposReservados = gruposReservados;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}