package com.example.demo.service;

import com.example.demo.dto.PacienteDTO;
import com.example.demo.entities.Paciente;
import com.example.demo.mapper.PacienteMapper;
import com.example.demo.repository.IPacienteRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service

public class PacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private PacienteMapper pacienteMapper;
    

    public List<PacienteDTO> listarTodos() {
        return pacienteMapper.toDTOList(pacienteRepository.findAll());
    }

    public Optional<PacienteDTO> buscarPorId(Long id) {
        return pacienteRepository.findById(id).map(pacienteMapper::toDTO);
    }

    public PacienteDTO salvar(PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        return pacienteMapper.toDTO(pacienteRepository.save(paciente));
    }

    public void deletar(Long id) {
        pacienteRepository.deleteById(id);
    }
}
