package com.example.demo.mapper;

import com.example.demo.dto.ConsultaDTO;
import com.example.demo.entities.Consulta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {
    
    default ConsultaDTO toDTO(Consulta consulta) {
        if (consulta == null) return null;

        ConsultaDTO dto = new ConsultaDTO();
        dto.setNomeMedico(consulta.getMedicoId() != null ? consulta.getMedicoId().getNome() : "N/A");
        dto.setNomePaciente(consulta.getPacienteId() != null ? consulta.getPacienteId().getNome() : "N/A");
        dto.setDataConsulta(consulta.getDataHora());
        dto.setStatusConsulta(consulta.getStatus() != null ? consulta.getStatus().name() : "AGENDADO");
        return dto;
    }

    Consulta toEntity(ConsultaDTO consultaDTO);

    List<ConsultaDTO> toDTOList(List<Consulta> consultas);
}
