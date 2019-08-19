package com.sistema.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.curso.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{
    public List<Curso> findAllByOrderByNomeAsc();


}
