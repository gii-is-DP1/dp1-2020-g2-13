package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Examen;
import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ExamenService;
import org.springframework.samples.petclinic.service.PreguntaService;
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
	public static final String EXAMEN_DETAILS = "examenes/ExamenDetails";
	public static final String EXAMEN_TRY = "examenes/examenTry";

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
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA   " + examen.getId() + " " + examen.getTitulos());
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
		Usuario usuario = examen.getUsuario();
		List<Examen> examenes = new ArrayList<>(usuario.getExamenes());
		examenes.remove(examen);
		usuario.setExamenes(new HashSet<>(examenes));
		examenService.delete(examen);
		usuarioService.save(usuario);
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
	
	@GetMapping("/{id}/details")
	public String examenDetails(@PathVariable("id") int id, ModelMap model) {
		List<Pregunta> preguntas = examenService.findById(id).getPreguntas();

		List<List<Opcion>> opciones= new ArrayList<List<Opcion>>();
		for(int i=0;i<preguntas.size();i++){
			if(preguntas.get(i).getTipoTest()!=null) {
				opciones.add(preguntas.get(i).getTipoTest().getOpciones());	
			}
			else {
				opciones.add(new ArrayList<Opcion>());
			}
		}
		model.addAttribute("examen", examenService.findById(id));
		model.addAttribute("preguntas", preguntas);
		model.addAttribute("opciones", opciones);

		return EXAMEN_DETAILS;
	}
	
	@GetMapping("/{id}/try")
	public String examenTry(@PathVariable("id") int id, ModelMap model) {
		List<Pregunta> preguntas = examenService.findById(id).getPreguntas();

		List<List<Opcion>> opciones= new ArrayList<List<Opcion>>();
		for(int i=0;i<preguntas.size();i++){
			if(preguntas.get(i).getTipoTest()!=null) {
				opciones.add(preguntas.get(i).getTipoTest().getOpciones());	
			}
			else {
				opciones.add(new ArrayList<Opcion>());
			}
			model.addAttribute("respuesta" + preguntas.get(i).getId(), "");
		}
		model.addAttribute("examen", examenService.findById(id));
		model.addAttribute("preguntas", preguntas);
		model.addAttribute("opciones", opciones);
		return EXAMEN_TRY;
	}
	


}
