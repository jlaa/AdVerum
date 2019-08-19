package com.sistema.curso.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import com.sistema.curso.model.Curso;
import com.sistema.curso.repository.AlunoRepository;
import com.sistema.curso.repository.ContratoRepository;
import com.sistema.curso.repository.CursoRepository;

@Controller
@RequestMapping("/curso")
public class CursoController {
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private ContratoRepository contratoRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;

	@RequestMapping(value = "/cursos", method = RequestMethod.GET)
	public String getAllCurso(Model model) {
		List<Curso> cursos = cursoRepository.findAllByOrderByNomeAsc();
		if (!cursos.isEmpty()) {
			model.addAttribute("cursos", cursos);
		}
		return "ManterCurso";
	}

	@RequestMapping(value = "/cursoIncluirAlterar", method = RequestMethod.GET)
	public String getCursoById(@RequestParam(value = "id") Long cursoId, Model model) throws ResourceNotFoundException {
		Optional<Curso> curso = cursoRepository.findById(cursoId);
		if (curso.isPresent()) {
			model.addAttribute("curso", curso.get());
			return "IncluirAlterarCurso";
		} else {
			Curso aux = new Curso();
			model.addAttribute("curso", aux);
			return "IncluirAlterarCurso";
		}
	}

	@PostMapping("/novoCurso")
	public String criarCurso(@Valid @ModelAttribute Curso curso, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "IncluirAlterarAluno";
		} else {
			LocalDate hoje = LocalDate.now();
			Date date = Date.from(hoje.atStartOfDay(ZoneId.systemDefault()).toInstant());
			curso.setDataAlteracao(date);
			curso.setDataInclusao(date);
			try {
			cursoRepository.save(curso);
			} catch(Exception e) {
				return "error-500";
			}
			List<Curso> cursos = cursoRepository.findAllByOrderByNomeAsc();
			if (!cursos.isEmpty()) {
				model.addAttribute("cursos", cursos);
			}
			model.addAttribute("sucesso","Curso adicionado com suceso!");
			return "ManterCurso";
		}
	}

	@PostMapping(value = "/cursoAlterar")
	public String alterarCurso(@RequestParam(value = "id") Long cursoId,
			@Valid @ModelAttribute("curso") Curso cursoDetails, BindingResult bindingResult, Model model)
			throws ResourceNotFoundException {
		if (bindingResult.hasErrors()) {
			return "IncluirAlterarAluno";
		} else {
			Curso curso = cursoRepository.findById(cursoId)
					.orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado com o id :: " + cursoId));
			LocalDate hoje = LocalDate.now();
			Date date = Date.from(hoje.atStartOfDay(ZoneId.systemDefault()).toInstant());
			curso.setDataAlteracao(date);
			curso.setNome(cursoDetails.getNome());
			curso.setDescricao(cursoDetails.getDescricao());
			try {
			cursoRepository.save(curso);
			} catch(Exception e) {
				return "error-500";
			}
			List<Curso> cursos = cursoRepository.findAllByOrderByNomeAsc();
			if (!cursos.isEmpty()) {
				model.addAttribute("cursos", cursos);
			}
			model.addAttribute("sucesso","Curso alterado com suceso!");
			return "ManterCurso";
		}
	}

	@RequestMapping(value = "/cursoExcluir", method = RequestMethod.GET)
	public String deleteCurso(@RequestParam(value = "id") Long cursoId, Model model) throws ResourceNotFoundException {
		Optional<Curso> curso = cursoRepository.findById(cursoId);
		try {
		List<Contrato> contratos = curso.get().getContratos();
		for(Contrato contrato:contratos) {
			Aluno aluno = contrato.getAluno();
			aluno.getContratos().remove(contrato);	
			alunoRepository.save(aluno);
		}
		contratoRepository.deleteAll(contratos);
		cursoRepository.delete(curso.get());
		} catch(Exception e) {
			return "error-500";
		}
		List<Curso> cursos = cursoRepository.findAllByOrderByNomeAsc();
		if (!cursos.isEmpty()) {
			model.addAttribute("cursos", cursos);
		}
		model.addAttribute("sucesso","Curso excluído com suceso!");
		return "ManterCurso";
	}

}
