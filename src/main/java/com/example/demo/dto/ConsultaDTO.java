package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
