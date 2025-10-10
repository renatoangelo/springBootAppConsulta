package com.example.demo.mapper;

import com.example.demo.dto.ConsultaDTO;
import com.example.demo.entities.Consulta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {

    ConsultaDTO toDTO(Consulta consulta);

    Consulta toEntity(ConsultaDTO consultaDTO);

    List<ConsultaDTO> toDTOList(List<Consulta> consultas);
}
