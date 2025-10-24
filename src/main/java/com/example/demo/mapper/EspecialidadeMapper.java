package com.example.demo.mapper;

import com.example.demo.dto.EspecialidadeDTO;
import com.example.demo.entities.Especialidade;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface EspecialidadeMapper {
    EspecialidadeDTO toDTO(Especialidade especialidade);

    Especialidade toEntity(EspecialidadeDTO especialidadeDTO);

    default List<EspecialidadeDTO> toDTOList (List<Especialidade> especialidades){
        return especialidades.stream().map(e -> {
            EspecialidadeDTO dto = new EspecialidadeDTO();
            dto.setId(e.getId());
            dto.setNome(e.getNome());
            return dto;
        }).collect(Collectors.toList());
    }
}
