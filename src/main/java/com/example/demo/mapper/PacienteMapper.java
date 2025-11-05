package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.PacienteDTO;
import com.example.demo.entities.Paciente;


@Mapper(componentModel = "spring")
public interface PacienteMapper {
    PacienteDTO toDTO(Paciente paciente);

    default Paciente toEntity(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setCpf(pacienteDTO.getCpf());
        paciente.setEmail(pacienteDTO.getEmail());
        paciente.setNome(pacienteDTO.getNome());
        paciente.setTelefone(pacienteDTO.getTelefone());

        return paciente;
    }

    List<PacienteDTO> toDTOList(List<Paciente> pacientes);
}
