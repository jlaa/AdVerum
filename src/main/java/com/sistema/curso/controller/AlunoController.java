package com.sistema.curso.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sistema.curso.exception.ResourceNotFoundException;
import com.sistema.curso.model.Aluno;
import com.sistema.curso.model.Contrato;
import com.sistema.curso.repository.AlunoRepository;

@Controller
@RequestMapping("/aluno")
public class AlunoController {
	@Autowired
	private AlunoRepository alunoRepository;

	@RequestMapping(value = "/alunos", method = RequestMethod.GET)
	public String getAllAluno(Model model) {
		List<Aluno> alunos = alunoRepository.findAllByOrderByNomeAsc();
		if (!alunos.isEmpty()) {
			model.addAttribute("alunos", alunos);
		}
		return "ManterAluno";
	}

	@RequestMapping(value = "/alunoAlterar", method = RequestMethod.GET)
	public String getAlunoById(@RequestParam(value = "id") Long alunoId, Model model) throws ResourceNotFoundException {
		Optional<Aluno> aluno = alunoRepository.findById(alunoId);
		if (aluno.isPresent()) {
			model.addAttribute("aluno", aluno.get());
			return "IncluirAlterarAluno";
		} else {
			Aluno aux = new Aluno();
			model.addAttribute("aluno", aux);
			return "IncluirAlterarAluno";
		}

	}

	@PostMapping("/novoAluno")
	public String criarAluno(@Valid @ModelAttribute Aluno aluno, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "IncluirAlterarAluno";
		} else {
			Aluno aux = alunoRepository.findBycpf(aluno.getCpf());
			if (aux != null) {
				model.addAttribute("falha", "Já existe um aluno com esse cpf");
				return "IncluirAlterarAluno";
			}
			aux = alunoRepository.findByEmail(aluno.getEmail());
			if (aux != null) {
				model.addAttribute("falha", "Já existe um aluno com esse email");
				return "IncluirAlterarAluno";
			}
			aluno.setContratos(new ArrayList<Contrato>());
			try {
			alunoRepository.save(aluno);
			} catch(Exception e) {
				return "error-500";
			}
			List<Aluno> alunos = alunoRepository.findAllByOrderByNomeAsc();
			if (!alunos.isEmpty()) {
				model.addAttribute("alunos", alunos);
			}
			model.addAttribute("sucesso","Aluno cadastrado com suceso!");
			return "ManterAluno";
		}
	}

	@RequestMapping(value = "/alunoAlterar", method = RequestMethod.POST)
	public String alterarAluno(@RequestParam(value = "id") Long alunoId,
			@ModelAttribute(value = "aluno") @Valid Aluno alunoDetails, BindingResult bindingResult, Model model)
			throws ResourceNotFoundException {
		if (bindingResult.hasErrors()) {
			alunoDetails.setCodObjeto(alunoId);
			model.addAttribute("aluno",alunoDetails);
			return "IncluirAlterarAluno";
		} else {
			Aluno aux = alunoRepository.findBycpf(alunoDetails.getCpf());
			if (aux!=null && aux.getCodObjeto() != alunoId) {
					model.addAttribute("falha", "Já existe um aluno com esse cpf");
					alunoDetails.setCodObjeto(alunoId);
					model.addAttribute("aluno",alunoDetails);
					return "IncluirAlterarAluno";
			}
			aux = alunoRepository.findByEmail(alunoDetails.getEmail());
			if (aux!=null && aux.getCodObjeto() != alunoId) {
					model.addAttribute("falha", "Já existe um aluno com esse email");
					alunoDetails.setCodObjeto(alunoId);
					model.addAttribute("aluno",alunoDetails);
					return "IncluirAlterarAluno";
			}

			Aluno aluno = alunoRepository.findById(alunoId)
					.orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com o id :: " + alunoId));

			aluno.setCpf(alunoDetails.getCpf());
			aluno.setEmail(alunoDetails.getEmail());
			aluno.setNome(alunoDetails.getNome());
			aluno.setTelefone(alunoDetails.getTelefone());
			try {
			alunoRepository.save(aluno);
			} catch(Exception e) {
				return "error-500";
			}
			List<Aluno> alunos = alunoRepository.findAllByOrderByNomeAsc();
			if (!alunos.isEmpty()) {
				model.addAttribute("alunos", alunos);
			}
			model.addAttribute("sucesso","Aluno alterado com suceso!");
			return "ManterAluno";
		}
	}

	@RequestMapping(value = "/alunoExcluir", method = RequestMethod.GET)
	public String deleteAluno(@RequestParam(value = "id") Long alunoId, Model model) throws ResourceNotFoundException {
		Optional<Aluno> aluno = alunoRepository.findById(alunoId);
		try {
		alunoRepository.delete(aluno.get());
		} catch(Exception e) {
			return "error-500";
		}
		List<Aluno> alunos = alunoRepository.findAllByOrderByNomeAsc();
		if (!alunos.isEmpty()) {
			model.addAttribute("alunos", alunos);
		}
		model.addAttribute("sucesso","Aluno deletado com suceso!");
		return "ManterAluno";
	}
}
