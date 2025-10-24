package com.example.demo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConsultaDTO {
    @NotBlank(message = "Medico Obrigatorio")
    private String nomeMedico;

    @NotBlank(message = "Paciente Obrigatõrio")
    private String nomePaciente;

    @NotBlank(message = "Adicione a data da Consulta")
    private LocalDateTime dataConsulta;

    @NotBlank(message = "O Status da consulta é obrigatório")
    private String statusConsulta;


}
