package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Examen;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ExamenService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/examenes")
public class ExamenController {
	
	public static final String EXAMENES_FORM = "examenes/createOrUpdateExamenesForm";
	public static final String EXAMENES_LISTING = "examenes/ExamenesListing";

	@Autowired
	ExamenService examenService;
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping
	public String listExamenes(ModelMap model) {
		model.addAttribute("examenes", examenService.findAll());
		return EXAMENES_LISTING;
	}
	
	@GetMapping("/{id}/edit")
	public String editExamen(@PathVariable("id") int id, ModelMap model) {
		Optional<Examen> examen = examenService.findById(id);
		Collection<Usuario> usuarios = usuarioService.findAll();
		if (examen.isPresent()) {
			model.addAttribute("examen", examen.get());
			model.addAttribute("usuarios", usuarios);
			return EXAMENES_FORM;
		} else {
			model.addAttribute("message", "We cannot find the exam you tried to edit!");
			return listExamenes(model);
		}
	}
	
	@PostMapping("/{id}/edit")
	public String editExamen(@PathVariable("id") int id, @Valid Examen modifiedExamen, BindingResult binding,
			ModelMap model) {
		Optional<Examen> examen = examenService.findById(id);
		if (binding.hasErrors()) {
			return EXAMENES_FORM;
		} else {
			BeanUtils.copyProperties(modifiedExamen, examen.get(), "id");
			examenService.save(examen.get());
			model.addAttribute("message", "Thread updated succesfully!");
			return listExamenes(model);
		}
	}
	
	@GetMapping("/{id}/delete")
	public String deleteExamen(@PathVariable("id") int id,ModelMap model) {
		Optional<Examen> examen=examenService.findById(id);
		if(examen.isPresent()) {
			examenService.delete(examen.get());
			model.addAttribute("message","The exam was deleted successfully!");
			return listExamenes(model);
		}else {
			model.addAttribute("message","We cannot find the exam you tried to delete!");
			return listExamenes(model);
		}
	}
	
	@GetMapping("/new")
	public String editNewExamen(ModelMap model) {
		Collection<Usuario> usuarios = usuarioService.findAll();
		model.addAttribute("examen",new Examen());
		model.addAttribute("usuarios", usuarios);
		return EXAMENES_FORM;
	}
	
	@PostMapping("/new")
	public String saveNewExamen(@Valid Examen examen, BindingResult binding,ModelMap model) {
		if(binding.hasErrors()) {			
			return EXAMENES_FORM;
		}else {
			examenService.save(examen);
			model.addAttribute("message", "The exam was created successfully!");			
			return listExamenes(model);
		}
	}
	
	
}
