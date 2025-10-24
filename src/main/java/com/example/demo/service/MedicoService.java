package com.example.demo.service;

import com.example.demo.dto.MedicoDTO;
import com.example.demo.entities.Especialidade;
import com.example.demo.entities.Medico;
import com.example.demo.mapper.MedicoMapper;
import com.example.demo.repository.IEspecialidadeRepository;
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
    private IEspecialidadeRepository especilidadeRepository;

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

        Especialidade especialidade = especilidadeRepository.findByNome(medicoDTO.getEspecialidade())
                .orElseThrow(()-> new IllegalArgumentException("Especialidade não encontrada!"));

        medico.setEspecialidade(especialidade);
        return medicoMapper.toDTO(medicoRepository.save(medico));
    }

    public void deletar(Long id) {
        medicoRepository.deleteById(id);
    }

}
