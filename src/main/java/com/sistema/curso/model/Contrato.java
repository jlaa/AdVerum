package com.sistema.curso.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CONTRATO")
public class Contrato {

    private long codObjeto;
    private String codigoCompra;
    private Curso curso;
    private Aluno aluno;
 
    public Contrato() {
  
    }
 
    public Contrato(String codigoCompra, Curso curso) {
         this.codigoCompra = codigoCompra;
         this.curso = curso;
    }
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        public long getCodObjeto() {
        return codObjeto;
    }
    public void setCodObjeto(long codObjeto) {
        this.codObjeto = codObjeto;
    }
 
    @Column(name = "CODIGO_COMPRA")
    public String getCodigoCompra() {
        return codigoCompra;
    }
    public void setCodigoCompra(String codigoCompra) {
        this.codigoCompra = codigoCompra;
    }
 
    @ManyToOne
    @JoinColumn(name = "COD_OBJETO_CURSO")
    @NotNull(message="Selecione um curso")
    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    } 
    
    @ManyToOne
    @JoinColumn(name = "COD_OBJETO_ALUNO")
    public Aluno getAluno() {
        return aluno;
    }
    public void setAluno(Aluno Aluno) {
        this.aluno = Aluno;
    } 
    
    
}


