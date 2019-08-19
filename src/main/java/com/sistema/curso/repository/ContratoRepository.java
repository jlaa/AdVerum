package com.sistema.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.curso.model.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long>{

}
