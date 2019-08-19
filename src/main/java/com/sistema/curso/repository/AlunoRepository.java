package com.sistema.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.curso.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{
    public List<Aluno> findAllByOrderByNomeAsc();
    
    public Aluno findBycpf(String cpf);
    
    public Aluno findByEmail(String email);

}
