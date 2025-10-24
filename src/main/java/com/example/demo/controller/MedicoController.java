package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.MedicoDTO;
import com.example.demo.service.MedicoService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Medicos", description = "Endpoints para gerenciamento de médicos")
@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Operation(summary = "Lista todos os médicos", description = "Retorna uma lista com todos os médicos cadastrados")
    @GetMapping
    public ResponseEntity<List<MedicoDTO>> listarMedicos() {
        List<MedicoDTO> medicos = medicoService.listarTodos();
        return ResponseEntity.ok(medicos);
    }

    @Operation(summary = "Busca um médico por ID", description = "Retorna os detalhes de um médico específico")
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> buscarPorId(@PathVariable Long id) {
        Optional<MedicoDTO> medicoDTO = medicoService.buscarPorId(id);
        return medicoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo médico", description = "Cadastra um novo médico no sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<MedicoDTO>> criarMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
        try {
            // Tenta salvar o usuário
            MedicoDTO savedMedico = medicoService.salvar(medicoDTO);

            // Retorna sucesso com o MedicoDTO salvo
            ApiResponse<MedicoDTO> response = new ApiResponse<>(savedMedico);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Cria um erro com a mensagem específica
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<MedicoDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            // Cria um erro genérico
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<MedicoDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Deleta um médico", description = "Remove um médico do sistema pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMedico(@PathVariable Long id) {
        medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
