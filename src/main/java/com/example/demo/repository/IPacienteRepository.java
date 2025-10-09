package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Paciente;

import java.util.Optional;

@Repository

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByNome(String nome);
}
