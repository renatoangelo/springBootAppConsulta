package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.DisponibilidadeDTO;
import com.example.demo.entities.Disponibilidade;
import com.example.demo.entities.Medico;
import com.example.demo.repository.IMedicoRepository;
import com.example.demo.service.DisponibilidadeService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Disponibilidades", description = "Endpoints para gerenciamento das disponibilidades dos médicos")
@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "*")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeService disponibilidadeService;

    @Autowired
    private IMedicoRepository medicoRepository;

    @Operation(summary = "Registrar disponibilidade", description = "Registra horários disponíveis para um médico específico")
    @PostMapping("/{medicoId}/disponibilidades")
    public ResponseEntity<ApiResponse<DisponibilidadeDTO>> criarDisponibilidade(
            @PathVariable Long medicoId,
            @Valid @RequestBody Disponibilidade disponibilidade) {

        try {
            Optional<Medico> medicoOpt = medicoRepository.findById(medicoId);
            if (medicoOpt.isEmpty()) {
                ErrorResponse error = new ErrorResponse("Médico não encontrado",
                        "Não existe médico cadastrado com o ID " + medicoId);
                ApiResponse<DisponibilidadeDTO> response = new ApiResponse<>(error);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            disponibilidade.setMedico(medicoOpt.get());

            DisponibilidadeDTO nova = disponibilidadeService.criarDisponibilidade(disponibilidade);
            ApiResponse<DisponibilidadeDTO> response = new ApiResponse<>(nova);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<DisponibilidadeDTO> response = new ApiResponse<>(error);
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<DisponibilidadeDTO> response = new ApiResponse<>(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Listar disponibilidades de um médico", description = "Retorna todas as disponibilidades cadastradas para o médico informado")
    @GetMapping("/{medicoId}/disponibilidades")
    public ResponseEntity<ApiResponse<List<DisponibilidadeDTO>>> listarDisponibilidadesPorMedico(
            @PathVariable Long medicoId) {

        try {
            if (!medicoRepository.existsById(medicoId)) {
                ErrorResponse error = new ErrorResponse("Médico não encontrado",
                        "Não existe médico cadastrado com o ID " + medicoId);
                ApiResponse<List<DisponibilidadeDTO>> response = new ApiResponse<>(error);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<DisponibilidadeDTO> lista = disponibilidadeService.listarPorMedico(medicoId);
            ApiResponse<List<DisponibilidadeDTO>> response = new ApiResponse<>(lista);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<List<DisponibilidadeDTO>> response = new ApiResponse<>(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Remover disponibilidade", description = "Exclui uma disponibilidade específica pelo ID")
    @DeleteMapping("/disponibilidades/{id}")
    public ResponseEntity<ApiResponse<Void>> deletarDisponibilidade(@PathVariable Long id) {
        try {
            disponibilidadeService.deletarDisponibilidade(id);
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse("ID inválido", e.getMessage());
            ApiResponse<Void> response = new ApiResponse<>(error);
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<Void> response = new ApiResponse<>(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
