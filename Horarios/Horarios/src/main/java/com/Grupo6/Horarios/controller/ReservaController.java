package com.Grupo6.Horarios.controller;

import com.Grupo6.Horarios.model.Grupo;
import com.Grupo6.Horarios.model.Reserva;
import com.Grupo6.Horarios.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.time.*;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    private Map<String, List<String>> reservas = new HashMap<>();

    @GetMapping
    public Map<String, Object> getReservas() {
        Map<String, Object> response = new HashMap<>();
        Map<String, List<String>> reservasDesdeDB = reservaService.obtenerReservasMap();
        response.put("reservas", reservasDesdeDB);
        response.put("diasConFechas", generarDiasConFechas());
        return response;
    }

    @PostMapping
    public Map<String, List<String>> agregarReserva(@RequestBody Map<String, String> nuevaReserva) {
        String dia = nuevaReserva.get("dia");
        String horario = nuevaReserva.get("horario");
        String grupo = nuevaReserva.get("grupo");
        String clave = dia + "-" + horario;

        reservas.putIfAbsent(clave, new ArrayList<>());
        List<String> grupos = reservas.get(clave);

        if (grupos.size() < 4) {
            grupos.add(grupo);

            // Guardar en MongoDB
            Reserva reserva = new Reserva();
            reserva.setDia(dia);
            reserva.setFecha(LocalDate.now()); // Guardar la fecha actual
            reserva.setHorario(horario);
            reserva.setGruposReservados(grupos);
            reservaService.guardarReserva(reserva);
        } else {
            throw new IllegalStateException("El horario está lleno.");
        }

        return reservas;
    }

    @DeleteMapping
    public void borrarReservas() {
        reservas.clear();
    }

    // Método auxiliar para generar días con fechas
    private Map<String, String> generarDiasConFechas() {
        Map<String, String> diasConFechas = new LinkedHashMap<>();
        LocalDate hoy = LocalDate.now();
        diasConFechas.put("Lunes", obtenerFechaPorDia(hoy, DayOfWeek.MONDAY));
        diasConFechas.put("Martes", obtenerFechaPorDia(hoy, DayOfWeek.TUESDAY));
        diasConFechas.put("Miércoles", obtenerFechaPorDia(hoy, DayOfWeek.WEDNESDAY));
        diasConFechas.put("Jueves", obtenerFechaPorDia(hoy, DayOfWeek.THURSDAY));
        diasConFechas.put("Viernes", obtenerFechaPorDia(hoy, DayOfWeek.FRIDAY));
        return diasConFechas;
    }

    private String obtenerFechaPorDia(LocalDate hoy, DayOfWeek diaDeseado) {
        return hoy.with(TemporalAdjusters.nextOrSame(diaDeseado)).toString();
    }

//    @PostMapping("/grupos")
//    public Grupo registrarGrupo(@RequestBody Grupo grupo) {
//        return reservaService.establecerPasswordGrupo(
//                grupo.getNombre(),
//                grupo.getPassword(),
//                grupo.getEncargado(),
//                grupo.getIntegrantes()
//        );
//    }
}