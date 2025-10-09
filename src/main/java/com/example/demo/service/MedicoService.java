package com.example.demo.service;

import com.example.demo.dto.MedicoDTO;
import com.example.demo.entities.Medico;
import com.example.demo.mapper.MedicoMapper;
import com.example.demo.repository.IMedicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MedicoService {
    @Autowired
    private IMedicoRepository medicoRepository;

    @Autowired
    private MedicoMapper medicoMapper;
    

    public List<MedicoDTO> listarTodos() {
        return medicoMapper.toDTOList(medicoRepository.findAll());
    }

    public Optional<MedicoDTO> buscarPorId(Long id) {
        return medicoRepository.findById(id).map(medicoMapper::toDTO);
    }

    public MedicoDTO salvar(MedicoDTO medicoDTO) {
        Medico medico = medicoMapper.toEntity(medicoDTO);
        return medicoMapper.toDTO(medicoRepository.save(medico));
    }

    public void deletar(Long id) {
        medicoRepository.deleteById(id);
    }

}
