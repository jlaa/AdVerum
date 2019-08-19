package com.sistema.curso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sistema.curso.model.Curso;
import com.sistema.curso.repository.CursoRepository;

@Controller
public class SimpleController {
	@Autowired
    private CursoRepository cursoRepository;

    @GetMapping("/")
    public String getAllCurso(Model model) {
    	List<Curso> cursos = cursoRepository.findAllByOrderByNomeAsc();
    	if(!cursos.isEmpty()) {
    		model.addAttribute("cursos",cursos);
    	}
        return "index";
    }
}
