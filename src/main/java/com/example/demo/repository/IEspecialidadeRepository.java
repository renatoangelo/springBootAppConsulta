package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Especialidade;

public interface IEspecialidadeRepository extends JpaRepository<Especialidade, Long> {

	Optional<Especialidade> findByNome(String especialidade);
	
}
