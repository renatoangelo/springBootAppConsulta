package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.MedicoDTO;
import com.example.demo.entities.Medico;


@Mapper(componentModel = "spring")
public interface MedicoMapper {
    MedicoDTO toDTO(Medico medico);

    Medico toEntity(MedicoDTO medicoDTO);

    List<MedicoDTO> toDTOList(List<Medico> medicos);

}
