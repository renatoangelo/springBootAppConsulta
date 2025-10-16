package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.MedicoDTO;
import com.example.demo.entities.Medico;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MedicoMapper {
    @Mapping(target = "especialidade", source = "especialidade.nome")
    MedicoDTO toDTO(Medico medico);

    @Mapping(target = "especialidade.nome", source = "especialidade")
    Medico toEntity(MedicoDTO medicoDTO);

    List<MedicoDTO> toDTOList(List<Medico> medicos);

}
