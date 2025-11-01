package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EspecialidadeDTO {

    @NotBlank(message = "Nome da especialidade é obrigatório")
    private String nome;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}



