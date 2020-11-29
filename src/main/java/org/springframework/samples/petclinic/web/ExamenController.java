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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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

	@InitBinder("examen")
	public void initExamenBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ExamenValidator());
	}

	@GetMapping
	public String listExamenes(ModelMap model) {
		model.addAttribute("examenes", examenService.findAll());
		return EXAMENES_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String editExamen(@PathVariable("id") int id, ModelMap model) {
		Examen examen = examenService.findById(id);
		Collection<Usuario> usuarios = usuarioService.findAll();
		model.addAttribute("examen", examen);
		model.addAttribute("usuarios", usuarios);
		return EXAMENES_FORM;
	}

	@PostMapping("/{id}/edit")
	public String editExamen(@PathVariable("id") int id, @Valid Examen modifiedExamen, BindingResult binding,
			ModelMap model) {
		Examen examen = examenService.findById(id);
		if (binding.hasErrors()) {
			Collection<Usuario> usuarios = usuarioService.findAll();
			model.addAttribute("usuarios", usuarios);
			return EXAMENES_FORM;
		} else {
			BeanUtils.copyProperties(modifiedExamen, examen, "id");
			examenService.save(examen);
			model.addAttribute("message", "Thread updated succesfully!");
			return listExamenes(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteExamen(@PathVariable("id") int id, ModelMap model) {
		Examen examen = examenService.findById(id);
		examenService.delete(examen);
		model.addAttribute("message", "The exam was deleted successfully!");
		return listExamenes(model);
	}

	@GetMapping("/new")
	public String editNewExamen(ModelMap model) {
		Collection<Usuario> usuarios = usuarioService.findAll();
		model.addAttribute("examen", new Examen());
		model.addAttribute("usuarios", usuarios);
		return EXAMENES_FORM;
	}

	@PostMapping("/new")
	public String saveNewExamen(@Valid Examen examen, BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			Collection<Usuario> usuarios = usuarioService.findAll();
			model.addAttribute("usuarios", usuarios);
			return EXAMENES_FORM;
		} else {
			examenService.save(examen);
			model.addAttribute("message", "The exam was created successfully!");
			return listExamenes(model);
		}
	}

}
