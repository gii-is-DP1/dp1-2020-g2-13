package org.springframework.samples.petclinic.web;


import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.samples.petclinic.service.PreguntaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/preguntas")
public class PreguntaController {

	public static final String PREGUNTAS_FORM = "preguntas/createOrUpdatePreguntasForm";
	public static final String PREGUNTAS_LISTING = "preguntas/PreguntasListing";

	@Autowired
	PreguntaService preguntaService;

	@GetMapping
	public String listPreguntas(ModelMap model) {
		model.addAttribute("preguntas", preguntaService.findAll());
		return PREGUNTAS_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String editPregunta(@PathVariable("id") int id, ModelMap model) {
		Pregunta pregunta = preguntaService.findById(id);
		model.addAttribute("pregunta", pregunta);
		return PREGUNTAS_FORM;
	}

	@PostMapping("/{id}/edit")
	public String editPregunta(@PathVariable("id") int id, @Valid Pregunta modifiedPregunta, BindingResult binding,
			ModelMap model) {
		Pregunta pregunta = preguntaService.findById(id);
		if (binding.hasErrors()) {
			return PREGUNTAS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedPregunta, pregunta, "id");
			preguntaService.save(pregunta);
			model.addAttribute("message", "Thread updated succesfully!");
			return listPreguntas(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deletePregunta(@PathVariable("id") int id, ModelMap model) {
		Pregunta pregunta = preguntaService.findById(id);
		preguntaService.delete(pregunta);
		model.addAttribute("message", "The question was deleted successfully!");
		return listPreguntas(model);
	}

	@GetMapping("/new")
	public String editNewPregunta(ModelMap model) {
		model.addAttribute("pregunta", new Pregunta());
		return PREGUNTAS_FORM;
	}

	@PostMapping("/new")
	public String saveNewPreguntas(@Valid Pregunta pregunta, BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			return PREGUNTAS_FORM;
		} else {
			preguntaService.save(pregunta);
			model.addAttribute("message", "The question was created successfully!");
			return listPreguntas(model);
		}
	}


}
