package com.example.demo.service;

import com.example.demo.dto.ConsultaDTO;
import com.example.demo.entities.Consulta;
import com.example.demo.mapper.ConsultaMapper;
import com.example.demo.repository.IConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private IConsultaRepository consultaRepository;

    @Autowired
    private ConsultaMapper consultaMapper;

    public ConsultaDTO consultaSave(Consulta consulta) {
        return consultaMapper.toDTO(consultaRepository.save(consulta));
    }

    public List<ConsultaDTO> findAll() {
        return consultaMapper.toDTOList(consultaRepository.findAll());
    }

    public ConsultaDTO findById(Long id) {
        return consultaMapper.toDTO(consultaRepository.findById(id).get());
    }

    public ConsultaDTO update(Consulta consulta) {
        return consultaMapper.toDTO(consultaRepository.save(consulta));
    }

    public void deleteById(Consulta consulta) {
        consultaRepository.delete(consulta);
    }

}
