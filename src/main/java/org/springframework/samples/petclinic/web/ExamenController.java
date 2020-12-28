package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.samples.petclinic.model.Examen;
import org.springframework.samples.petclinic.model.Intento;
import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.samples.petclinic.model.Respuesta;
import org.springframework.samples.petclinic.model.TipoTest;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ExamenService;
import org.springframework.samples.petclinic.service.IntentoService;
import org.springframework.samples.petclinic.service.OpcionService;
import org.springframework.samples.petclinic.service.PreguntaService;
import org.springframework.samples.petclinic.service.RespuestaService;
import org.springframework.samples.petclinic.service.TipoTestService;
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

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/examenes")
@Slf4j
@SpringBootApplication
public class ExamenController {

	public static final String EXAMENES_FORM = "examenes/createOrUpdateExamenesForm";
	public static final String EXAMENES_LISTING = "examenes/ExamenesListing";
	public static final String EXAMEN_DETAILS = "examenes/ExamenDetails";
	public static final String EXAMEN_TRY = "examenes/examenTry";

	@Autowired
	ExamenService examenService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	IntentoService intentoService;
	@Autowired
	RespuestaService respuestaService;
	@Autowired
	PreguntaService preguntaService;
	@Autowired
	OpcionService opcionService;
	@Autowired
	TipoTestService tipoTestService;

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
		List<Pregunta> preguntas = new ArrayList<>(examen.getPreguntas());
		Usuario usuario = examen.getUsuario();
		List<Examen> examenes = new ArrayList<>(usuario.getExamenes());
		examenes.remove(examen);
		usuario.setExamenes(new HashSet<>(examenes));
		for(Pregunta pregunta: preguntas) {
			TipoTest tipoTest = pregunta.getTipoTest();
			if(tipoTest!=null) {
				List<Opcion> opciones = tipoTest.getOpciones();
				for(int i=opciones.size()-1;i>=0;i--) {
					Opcion opcion = opciones.get(i);
					opciones.remove(i);
					tipoTest.setOpciones(opciones);
					opcionService.delete(opcion);
				}
				pregunta.setTipoTest(null);
				preguntaService.save(pregunta);
				tipoTestService.delete(tipoTest);
			}
			preguntas.remove(pregunta);
			examen.setPreguntas(preguntas);
			examenService.save(examen);
			preguntaService.delete(pregunta);
		}
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
	
	@GetMapping("/{id}/{intento_id}/{numero_pregunta}/newTry")
	public String examenTry(@PathVariable("id") int id, @PathVariable("intento_id") int intento_id, @PathVariable("numero_pregunta") int numero_pregunta, ModelMap model) {
		Intento intento = new Intento();
		Respuesta respuesta = new Respuesta();
		List<Pregunta> preguntas = examenService.findById(id).getPreguntas();
		if(numero_pregunta!=0) {
			intento = intentoService.findById(intento_id);
		}else if(numero_pregunta==0) {
			intento.setPuntuacion(null);
			intento.setFecha(LocalDate.now());
			intento.setExamen(examenService.findById(id));
			intento.setRespuestas(new ArrayList<Respuesta>());
			intentoService.save(intento);
		}
		respuesta.setTextoRespuesta("");
		respuestaService.save(respuesta);
		Pregunta pregunta = preguntas.get(numero_pregunta);
		List<Opcion> opciones = new ArrayList<Opcion>();
		if(pregunta.getTipoTest()!=null) {
			opciones = pregunta.getTipoTest().getOpciones();
		}
		model.addAttribute("examen", examenService.findById(id));
		model.addAttribute("pregunta", pregunta.getContenido());
		model.addAttribute("intento", intento);
		model.addAttribute("opciones", opciones);
		model.addAttribute("respuesta", respuesta);
		model.addAttribute("numero", numero_pregunta);
		return EXAMEN_TRY;
	}
	
	@PostMapping("/{id}/{intento_id}/{numero_pregunta}/newTry")
	public String examenTry(@PathVariable("id") int id, @PathVariable("intento_id") int intento_id, @PathVariable("numero_pregunta") int numero_pregunta, Respuesta respuesta, ModelMap model) {
		
        Intento intento = intentoService.findById(intento_id);
        List<Respuesta> respuestas = intento.getRespuestas();
        respuestas.add(respuesta);
        intento.setRespuestas(respuestas);
		intentoService.save(intento);
		Examen examen = examenService.findById(id);
		log.info("--------------------------------------------------------------------------" + numero_pregunta + " " + examen.getPreguntas().size());
		if(numero_pregunta>=examen.getPreguntas().size()-1) {
			model.addAttribute("message", "You finished the exam!");
			return listExamenes(model);
		}else {
			numero_pregunta++;
			return examenTry(id, intento_id, numero_pregunta, model);
		}
	}
	
//	@GetMapping("/{id}/{intento_id}/{numero_pregunta}/try")
//	public String examenTryGet(@PathVariable("id") int id, @PathVariable("intento_id") int intento_id, @PathVariable("numero_pregunta") int numero_pregunta, ModelMap model) {
//		numero_pregunta++;
//		Intento intento = intentoService.findById(intento_id);
//		Examen examen = examenService.findById(id);
//		Pregunta pregunta = examen.getPreguntas().get(numero_pregunta);
//		List<Opcion> opciones = new ArrayList<Opcion>();
//		if(pregunta.getTipoTest()!=null) {
//			opciones = pregunta.getTipoTest().getOpciones();
//		}
//
//		Respuesta respuesta = new Respuesta();
//		model.addAttribute("examen", examen);
//		model.addAttribute("pregunta", pregunta.getContenido());
//		model.addAttribute("intento", intento);
//		model.addAttribute("opciones", opciones);
//		model.addAttribute("respuesta", respuesta);
//		model.addAttribute("numero", numero_pregunta);
//		return EXAMEN_TRY;
//	}
//	
//	@PostMapping("/{id}/{intento_id}/{numero_pregunta}/try")
//	public String examenTryPost(@PathVariable("id") int id, @PathVariable("intento_id") int intento_id, @PathVariable("numero_pregunta") int numero_pregunta, Respuesta respuesta, ModelMap model) {
//
//        Intento intento = intentoService.findById(intento_id);
//        List<Respuesta> respuestas = intento.getRespuestas();
//        respuestas.add(respuesta);
//        intento.setRespuestas(respuestas);
//		intentoService.save(intento);
//		Examen examen = examenService.findById(id);
//		log.info("--------------------------------------------------------------------------" + numero_pregunta + " " + examen.getPreguntas().size());
//		if(numero_pregunta>=examen.getPreguntas().size()-1) {
//			model.addAttribute("message", "You finished the exam!");
//			return listExamenes(model);
//		}else {
//			return examenTryGet(id, intento_id, numero_pregunta, model);
//		}
//	}

}
