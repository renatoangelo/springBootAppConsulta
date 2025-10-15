package com.example.demo.mapper;

import com.example.demo.dto.EspecialidadeDTO;
import com.example.demo.entities.Especialidade;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")

public interface EspecialidadeMapper {
    EspecialidadeDTO toDTO(Especialidade especialidade);

    Especialidade toEntity(EspecialidadeDTO especialidadeDTO);

    List<EspecialidadeDTO> toDTOList (List<Especialidade> especialidades);
}
