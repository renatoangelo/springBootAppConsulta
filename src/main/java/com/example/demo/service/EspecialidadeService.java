package com.example.demo.service;


import com.example.demo.dto.EspecialidadeDTO;
import com.example.demo.entities.Especialidade;
import com.example.demo.mapper.EspecialidadeMapper;
import com.example.demo.repository.IEspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EspecialidadeService {

    @Autowired
    private IEspecialidadeRepository especialidadeRepository;

    @Autowired
    private EspecialidadeMapper especialidadeMapper;
    

    public List<EspecialidadeDTO> listarTodos() {
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        return especialidadeMapper.toDTOList(especialidades);

    }

    public Optional<EspecialidadeDTO> buscarPorId(Long id) {
        return especialidadeRepository.findById(id).map(especialidadeMapper::toDTO);
    }

    public EspecialidadeDTO salvar(EspecialidadeDTO especialidadeDTO) {
        Especialidade especialidade = especialidadeMapper.toEntity(especialidadeDTO);
        return especialidadeMapper.toDTO(especialidadeRepository.save(especialidade));
    }

    public void deletar(Long id) {
        especialidadeRepository.deleteById(id);
    }
}

