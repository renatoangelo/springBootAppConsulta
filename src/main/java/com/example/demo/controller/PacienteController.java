package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.PacienteDTO;
import com.example.demo.service.PacienteService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Pacientes", description = "Endpoints para gerenciamento de pacientes")
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Operation(summary = "Lista todos os pacientes", description = "Retorna uma lista com todos os pacientes cadastrados")
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarPacientes() {
        List<PacienteDTO> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes);
    }

    @Operation(summary = "Busca um paciente por ID", description = "Retorna os detalhes de um paciente específico")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Long id) {
        Optional<PacienteDTO> pacienteDTO = pacienteService.buscarPorId(id);
        return pacienteDTO.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo paciente", description = "Cadastra um novo paciente no sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<PacienteDTO>> criarPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        try {
            // Tenta salvar o usuário
            PacienteDTO savedPaciente = pacienteService.salvar(pacienteDTO);
            
            // Retorna sucesso com o PacienteDTO salvo
            ApiResponse<PacienteDTO> response = new ApiResponse<>(savedPaciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Cria um erro com a mensagem específica
            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<PacienteDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            // Cria um erro genérico
            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<PacienteDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Deleta um paciente", description = "Remove um paciente do sistema pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
