package com.sistema.curso.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "ALUNO")
public class Aluno {

	private long codObjeto;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	private List<Contrato> contratos;

	public Aluno() {

	}

	public Aluno(String nome, String email, String cpf, String telefone,List<Contrato> contratos) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.telefone = telefone;
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
	@NotBlank(message = "O campo nome é obrigatório")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "EMAIL", nullable = false , unique=true)
	@NotBlank(message = "O campo email é obrigatório")
	@Email(message="O email não é válido")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CPF", nullable = false,unique=true)
	@NotBlank(message = "O campo CPF é obrigatório")
	@CPF(message="O CPF não é válido")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "TELEFONE", nullable = false)
	@NotBlank(message = "O campo telefone é obrigatório")
	@Pattern(regexp="^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$",message="O telefone não é válido")
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@OneToMany
	@Column(name="COD_OBJETO_CONTRATO")
	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}
	
	

}
