package com.sistema.curso.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "CURSO")
public class Curso {
	
	private long codObjeto;
    private String nome;
    private Date dataInclusao;
    private Date dataAlteracao;
    private String descricao;
    private List<Contrato> contratos;
    
    public Curso() {
    	
    }
    
    
    public Curso(String nome, Date dataInclusao, Date dataAlteracao, String descricao, List<Contrato> contratos) {
		super();
		this.nome = nome;
		this.dataInclusao = dataInclusao;
		this.dataAlteracao = dataAlteracao;
		this.descricao = descricao;
		this.contratos = contratos;
	}


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getCodObjeto() {
		return codObjeto;
	}

	public void setCodObjeto(long codObjeto) {
		this.codObjeto = codObjeto;
	}

	@Column(name = "NOME", nullable = false)
	@NotBlank(message="O campo nome é obrigatório")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name="DATA_INCLUSAO", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	@Column(name="DATA_ALTERACAO", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	@Column(name="DESCRICAO")
	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "curso")
	@Column(name="COD_OBJETO_CONTRATO")
	public List<Contrato> getContratos() {
		return contratos;
	}


	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}
    
    
	

}
