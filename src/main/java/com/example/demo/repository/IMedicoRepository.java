package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Medico;

import java.util.Optional;

@Repository
public interface IMedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByNome(String nome);
}



