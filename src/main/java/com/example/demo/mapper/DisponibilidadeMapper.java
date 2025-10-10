package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.DisponibilidadeDTO;
import com.example.demo.entities.Disponibilidade;

@Mapper(componentModel = "spring")
public interface DisponibilidadeMapper {
	
	default DisponibilidadeDTO toDTO(Disponibilidade disponibilidade) {
		DisponibilidadeDTO dto = new DisponibilidadeDTO();
		//dto.setNomeMedico(disponibilidade.getMedico().getNome());
		dto.setDiaSemana(disponibilidade.getDiaSemana());
		dto.setHorarioInicio(disponibilidade.getHorarioInicio());
		dto.setHorarioFim(disponibilidade.getHorarioFim());
		return dto;
	}
	
	List<DisponibilidadeDTO> toListDTOs(List<Disponibilidade> disponibilidades);
     
}
