package com.example.demo.controller;

import com.example.demo.dto.EspecialidadeDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entities.Especialidade;
import com.example.demo.service.EspecialidadeService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Especialidades", description = "Endpoints para gerenciamento de especialidades")
@RestController
@RequestMapping("api/especialidades")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    @Operation(summary = "Lista todas ás Especialidades", description = "Retorna uma lista com todas as especialidades cadastradas")
    @GetMapping
    public ResponseEntity<List<EspecialidadeDTO>> listarEspecialidades() {
        List<EspecialidadeDTO> especialidadesDTO = especialidadeService.listarTodos();
        return ResponseEntity.ok(especialidadesDTO);
    }

    @Operation(summary = "Buscar uma especialidade por ID", description = "Retorna os detalhes de uma especialidade específica")
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeDTO> buscarPorId(@PathVariable Long id) {
        Optional<EspecialidadeDTO> especilidadeDTO = especialidadeService.buscarPorId(id);
        return especilidadeDTO.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria uma nova especialidade", description = "Cadastrar uma nova especialidade no sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<EspecialidadeDTO>> criarEspecialidade(@Valid @RequestBody EspecialidadeDTO especialidadeDTO) {
        try {

           EspecialidadeDTO savedEspecialidade = especialidadeService.salvar(especialidadeDTO);
            

            ApiResponse<EspecialidadeDTO> response = new ApiResponse<>(savedEspecialidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {

            ErrorResponse errorResponse = new ErrorResponse("Argumento inválido", e.getMessage());
            ApiResponse<EspecialidadeDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());
            ApiResponse<EspecialidadeDTO> response = new ApiResponse<>(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Deleta uma Especialidade", description = "Remove uma Especialidade do sistema pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEspecialidade(@PathVariable Long id) {
        especialidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}