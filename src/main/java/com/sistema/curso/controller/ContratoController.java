package com.sistema.curso.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sistema.curso.exception.ResourceNotFoundException;
import com.sistema.curso.model.Aluno;
import com.sistema.curso.model.Contrato;
import com.sistema.curso.model.Curso;
import com.sistema.curso.repository.AlunoRepository;
import com.sistema.curso.repository.ContratoRepository;
import com.sistema.curso.repository.CursoRepository;

@Controller
@RequestMapping("/contrato")
public class ContratoController {
	@Autowired
	private ContratoRepository contratoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private AlunoRepository alunoRepository;

	@RequestMapping(value = "/contratos", method = RequestMethod.GET)
	public String getAllAluno(@RequestParam(value = "id") Long alunoId, Model model) {
		Optional<Aluno> aluno = alunoRepository.findById(alunoId);
		List<Contrato> contratos = aluno.get().getContratos();
		if (!contratos.isEmpty()) {
			model.addAttribute("contratos", contratos);
			model.addAttribute("aluno", aluno.get());
		}
		return "ListarContratos";
	}

	@GetMapping("/contratos/{id}")
	public ResponseEntity<Contrato> getContratoById(@PathVariable(value = "id") Long contratoId)
			throws ResourceNotFoundException {
		Contrato contrato = contratoRepository.findById(contratoId)
				.orElseThrow(() -> new ResourceNotFoundException("Contrato não encontrato com o id :: " + contratoId));
		return ResponseEntity.ok().body(contrato);
	}

	@RequestMapping(value = "/novoContrato", method = RequestMethod.GET)
	public String getNovoContrato(@RequestParam(value = "id") Long alunoId, Model model) {
		Contrato contrato = new Contrato();
		List<Curso> cursos = cursoRepository.findAllByOrderByNomeAsc();
		if (!cursos.isEmpty()) {
			model.addAttribute("cursos", cursos);
			model.addAttribute("contrato", contrato);
			model.addAttribute("alunoId", alunoId);
		}
		return "CadastrarContrato";
	}

	@PostMapping("/novoContrato")
	public String criarContrato(@RequestParam(value = "id") Long alunoId, @Valid @ModelAttribute Contrato contrato,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "CadastrarContrato";
		} else {
			Optional<Aluno> aluno = alunoRepository.findById(alunoId);
			List<Contrato> contratos = aluno.get().getContratos();
			for(Contrato aux:contratos) {
				if(contrato.getCurso().getCodObjeto() == aux.getCurso().getCodObjeto()) {
					model.addAttribute("falha", "Já existe este curso cadastrado para este aluno");
					List<Curso> cursos = cursoRepository.findAllByOrderByNomeAsc();
					contrato = new Contrato();
					if (!cursos.isEmpty()) {
						model.addAttribute("cursos", cursos);
						model.addAttribute("contrato", contrato);
						model.addAttribute("alunoId", alunoId);
					}
					return "CadastrarContrato";
				}
			}
			contratoRepository.save(contrato);
			
			aluno.get().getContratos().add(contrato);
			alunoRepository.save(aluno.get());
			List<Aluno> alunos = alunoRepository.findAllByOrderByNomeAsc();
			if (!alunos.isEmpty()) {
				model.addAttribute("alunos", alunos);
			}
			model.addAttribute("sucesso", "Contrato adicionado com sucesso");
			return "ManterAluno";
		}
	}

}
