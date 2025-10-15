package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EspecialidadeDTO {

    private Long id;

    @NotBlank(message = "Nome da especialidade é obrigatório")
    private String nome;
}



