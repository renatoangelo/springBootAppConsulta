package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DisponibilidadeDTO;
import com.example.demo.entities.Disponibilidade;
import com.example.demo.mapper.DisponibilidadeMapper;
import com.example.demo.repository.IDisponibilidadeRepository;

import jakarta.transaction.Transactional;

@Service
public class DisponibilidadeService {
	
	@Autowired
	private IDisponibilidadeRepository disponibilidadeRepository;
	
	@Autowired
	private DisponibilidadeMapper disponibilidadeMapper;
	
	
	public DisponibilidadeDTO criarDisponibilidade(Disponibilidade disponibilidade) {
        return disponibilidadeMapper.toDTO(disponibilidadeRepository.save(disponibilidade));
    }
	
	public List<DisponibilidadeDTO> findByMedicoId(Long medicoId) {
        List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByMedicoId(medicoId);
        return disponibilidadeMapper.toListDTOs(disponibilidades);
    }
	
	@Transactional
	public void deletarDisponibilidade(Long id) {
		disponibilidadeRepository.deleteById(id);
	}
	
}
