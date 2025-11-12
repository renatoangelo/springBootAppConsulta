package com.example.demo.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ConsultaDTO;
import com.example.demo.entities.Consulta;
import com.example.demo.service.ConsultaService;
import com.example.demo.service.StatusConsulta;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


//Checagem Point 12/11/2025 - 15:38

@Tag(name = "Consultas", description = "Endpoints para gerenciamento de consultas médicas")
@RestController
@RequestMapping("api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Operation(summary = "Agendar consulta", description = "Agenda uma nova consulta para um paciente e médico")
    @PostMapping
    public ResponseEntity<ApiResponse<ConsultaDTO>> agendarConsulta(@Valid @RequestBody Consulta consulta) {
        try {
            List<ConsultaDTO> consultasExistentes = consultaService.findAll();
            boolean conflito = consultasExistentes.stream()
                    .anyMatch(c -> c.getDataConsulta().equals(consulta.getDataHora())
                            && c.getNomeMedico().equalsIgnoreCase(consulta.getMedicoId().getNome()));

            if (conflito) {
                ErrorResponse errorResponse = new ErrorResponse("Conflito de horário", "O médico já possui uma consulta nesse horário.");
                ApiResponse<ConsultaDTO> response = new ApiResponse<>(errorResponse);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ConsultaDTO novaConsulta = consultaService.consultaSave(consulta);
            ApiResponse<ConsultaDTO> response = new ApiResponse<>(novaConsulta);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro de validação", e.getMessage());
            ApiResponse<ConsultaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ConsultaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar consultas", description = "Lista todas as consultas com filtros opcionais")
    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> listarConsultas(
            @RequestParam(required = false) String medico,
            @RequestParam(required = false) String paciente,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDateTime dataInicio,
            @RequestParam(required = false) LocalDateTime dataFim) {

        List<ConsultaDTO> consultas = consultaService.findAll();

        if (medico != null)
            consultas = consultas.stream().filter(c -> c.getNomeMedico().equalsIgnoreCase(medico)).toList();

        if (paciente != null)
            consultas = consultas.stream().filter(c -> c.getNomePaciente().equalsIgnoreCase(paciente)).toList();

        if (status != null)
            consultas = consultas.stream().filter(c -> c.getStatusConsulta().equalsIgnoreCase(status)).toList();

        if (dataInicio != null && dataFim != null)
            consultas = consultas.stream()
                    .filter(c -> c.getDataConsulta().isAfter(dataInicio) && c.getDataConsulta().isBefore(dataFim))
                    .toList();

        return ResponseEntity.ok(consultas);
    }

    @Operation(summary = "Buscar consulta", description = "Busca uma consulta específica pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConsultaDTO>> buscarPorId(@PathVariable Long id) {
        try {
            ConsultaDTO consulta = consultaService.findById(id);
            if (consulta == null) {
                ErrorResponse errorResponse = new ErrorResponse("Não encontrada", "Consulta não localizada.");
                ApiResponse<ConsultaDTO> response = new ApiResponse<>(errorResponse);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            ApiResponse<ConsultaDTO> response = new ApiResponse<>(consulta);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<ConsultaDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ConsultaDTO>> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String novoStatus) {

        try {
            Consulta consulta = consultaService.findEntityById(id);

            StatusConsulta statusEnum = StatusConsulta.valueOf(novoStatus.toUpperCase());

            consulta.setStatus(statusEnum);

            ConsultaDTO updated = consultaService.update(consulta);

            return ResponseEntity.ok(new ApiResponse<>(updated));

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    "Status inválido", "Valores aceitos: AGENDADO, CANCELADO, CONCLUIDO, FALTOU"
            );
            return ResponseEntity.badRequest().body(new ApiResponse<>(errorResponse));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(errorResponse));
        }
    }

    @Operation(summary = "Cancelar consulta", description = "Cancela uma consulta. Só é permitido até 24h antes do horário agendado")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelarConsulta(@PathVariable Long id) {
        try {
            ConsultaDTO consultaDTO = consultaService.findById(id);
            if (consultaDTO == null) {
                ErrorResponse errorResponse = new ErrorResponse("Não encontrada", "Consulta não localizada.");
                ApiResponse<Void> response = new ApiResponse<>(errorResponse);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Duration diff = Duration.between(LocalDateTime.now(), consultaDTO.getDataConsulta());
            if (diff.toHours() < 24) {
                ErrorResponse errorResponse = new ErrorResponse("Prazo excedido", "Cancelamento permitido apenas até 24h antes da consulta.");
                ApiResponse<Void> response = new ApiResponse<>(errorResponse);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Consulta consulta = new Consulta();
            consulta.setId(id);
            consultaService.deleteById(consulta);

            ApiResponse<Void> response = new ApiResponse<>((Void) null);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<Void> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}