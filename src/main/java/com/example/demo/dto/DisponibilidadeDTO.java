package com.example.demo.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisponibilidadeDTO {

	private Long id;

	@NotBlank(message = "Adicione o medico na disponibilidade")
	private String nomeMedico;

	@NotBlank(message = "Adicione falta a especialidade")
	private String nomeEspecialidade;

	@NotBlank(message = "Qual dia da semana?")
	private String diaSemana;

	@NotBlank(message = "Adicione um horário Inicio")
	private LocalTime horarioInicio;

	@NotBlank(message = "Adicione um horário Final")
	private LocalTime horarioFim;

	public String getNomeMedico() {
		return nomeMedico;
	}

	public void setNomeMedico(String nomeMedico) {
		this.nomeMedico = nomeMedico;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public LocalTime getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(LocalTime horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public LocalTime getHorarioFim() {
		return horarioFim;
	}

	public void setHorarioFim(LocalTime horarioFim) {
		this.horarioFim = horarioFim;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeEspecialidade() {
		return nomeEspecialidade;
	}

	public void setNomeEspecialidade(String nomeEspecialidade) {
		this.nomeEspecialidade = nomeEspecialidade;
	}

}
