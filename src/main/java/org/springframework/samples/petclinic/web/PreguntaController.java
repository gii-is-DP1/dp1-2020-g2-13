package org.springframework.samples.petclinic.web;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Examen;
import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.samples.petclinic.service.ExamenService;
import org.springframework.samples.petclinic.service.PreguntaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.samples.petclinic.web.ExamenController;

@Controller
@RequestMapping("/preguntas")
public class PreguntaController {

	public static final String PREGUNTAS_FORM = "preguntas/createOrUpdatePreguntasForm";
	public static final String PREGUNTAS_LISTING = "preguntas/PreguntasListing";
	public static final String PREGUNTAS_DETAILS = "preguntas/PreguntasDetails";

	@Autowired
	PreguntaService preguntaService;
	
	@Autowired
	ExamenService examenService;
	
	@Autowired
	ExamenController examenController;

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
	
	@GetMapping("/{value}/new")
	public String editNewPregunta(@PathVariable("value") int id_examen, ModelMap model) {
		model.addAttribute("pregunta", new Pregunta());
		model.addAttribute("examen_id", id_examen);
		return PREGUNTAS_FORM;
	}
	
	@PostMapping("/{value}/new")
	public String saveNewPreguntas(@PathVariable("value") int id_examen, @Valid Pregunta pregunta, BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			return PREGUNTAS_FORM;
		} else {
			Examen examen = examenService.findById(id_examen);
			List<Pregunta> preguntas = examen.getPreguntas();
			preguntas.add(pregunta);
			examen.setPreguntas(preguntas);
			preguntaService.save(pregunta);
			examenService.save(examen);
			model.addAttribute("message", "The question was created successfully!");
			return examenController.examenDetails(id_examen, model);
		}
	}
	
	@GetMapping("/{id}/details")
	public String preguntaDetails(@PathVariable("id") int id, ModelMap model) {
		Pregunta pregunta = preguntaService.findById(id);
		model.addAttribute("pregunta", pregunta);
		return PREGUNTAS_DETAILS;
	}

}
