package com.example.demo.repository;

import com.example.demo.entities.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEspecialidadeRepository extends JpaRepository<Especialidade, Long> {
}
