package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.samples.petclinic.model.Examen;
import org.springframework.samples.petclinic.model.Intento;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.samples.petclinic.model.Respuesta;
import org.springframework.samples.petclinic.model.TipoTest;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ExamenService;
import org.springframework.samples.petclinic.service.IntentoService;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.samples.petclinic.service.OpcionService;
import org.springframework.samples.petclinic.service.PreguntaService;
import org.springframework.samples.petclinic.service.RespuestaService;
import org.springframework.samples.petclinic.service.TipoTestService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/examenes")
@Slf4j
//@SpringBootApplication
public class ExamenController {

	public static final String EXAMENES_FORM = "examenes/createOrUpdateExamenesForm";
	public static final String EXAMENES_LISTING = "examenes/ExamenesListing";
	public static final String EXAMEN_DETAILS = "examenes/ExamenDetails";
	public static final String EXAMEN_TRY = "examenes/examenTry";
	public static final String LOGIN = "login";
	public static final String ERROR = "error";

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
	@Autowired
	LogroService logroService;
	@Autowired
	LogroController logroController;

	@InitBinder("examen")
	public void initExamenBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ExamenValidator());
	}

	@GetMapping
	public String listExamenes(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		Collection<Usuario> usuarios = usuarioService.findAll();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuario = usuarioService.findByUsername(username);
		model.addAttribute("usuario", usuario);
		model.addAttribute("examenes", examenService.findAll());
		return EXAMENES_LISTING;
		
	}

	@GetMapping("/{id}/edit")
	public String editExamen(@PathVariable("id") int id, ModelMap model) {
		Examen examen = examenService.findById(id);
		Usuario usuario = examen.getUsuario();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if(usuario!=usuarioLoggeado) {
			return "redirect:/" + ERROR;
		}
		model.addAttribute("examen", examen);
		model.addAttribute("usuario", usuarioLoggeado);
		return EXAMENES_FORM;
	}

	@PostMapping("/{id}/edit")
	public String editExamen(@PathVariable("id") int id, @Valid Examen modifiedExamen, BindingResult binding,
			ModelMap model,@RequestParam(value="version", required= false) Integer version) {
		Examen examen = examenService.findById(id);
		if(examen.getVersion()!=version) {	
			model.put("message", "Alguien ha modificado simultáneamente el usuario, prueba otra vez");
			return editExamen(id, model);
		}
		if (binding.hasErrors()) {
			Usuario usuario = examen.getUsuario();
			model.addAttribute("usuario", usuario);
			return EXAMENES_FORM;
		} else {
			List<Pregunta> preguntas = examen.getPreguntas();
			modifiedExamen.setPreguntas(preguntas);
			modifiedExamen.setVersion(version+1);
			BeanUtils.copyProperties(modifiedExamen, examen, "id");
			examenService.save(examen);
			model.addAttribute("message", "Examen editado satisfactoriamente");
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			log.info("El examen " + examen.getId() + " fue editado por el usuario " + username+"con la version: "+version+1);
			return listExamenes(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteExamen(@PathVariable("id") int id, ModelMap model) {
		Examen examen = examenService.findById(id);
		List<Pregunta> preguntas = new ArrayList<>(examen.getPreguntas());
		Usuario usuario = examen.getUsuario();
		List<Examen> examenes = new ArrayList<>(usuario.getExamenes());
		
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		examenes.remove(examen);
		int idlogs= examen.getId();
		examenService.delete(examen);
		usuarioService.save(usuario);
		log.info("El examen " + idlogs + " fue eliminado por el usuario " + username);
		model.addAttribute("message", "El examen fue eliminado exitosamente");
		return listExamenes(model);
	}

	@GetMapping("/new")
	public String editNewExamen(ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		model.addAttribute("examen", new Examen());
		model.addAttribute("usuario", usuarioLoggeado);
		return EXAMENES_FORM;
	}

	@PostMapping("/new")
	public String saveNewExamen(@Valid Examen examen, BindingResult binding, ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
	
		if (binding.hasErrors()) {
			return EXAMENES_FORM;
		} else {
			examen.setVersion(0);
			examenService.save(examen);
			log.info("El examen " + examen.getId() + " fue creado por el usuario " + username+"con la version: 0");
			model.addAttribute("message", "El examen fue creado exitosamente");
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
		Examen examen = examenService.findById(id);
		Usuario usuario = examen.getUsuario();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if(usuario!=usuarioLoggeado) {
			return "redirect:/" + ERROR;
		}
		model.addAttribute("examen", examenService.findById(id));
		model.addAttribute("preguntas", preguntas);
		model.addAttribute("opciones", opciones);

		return EXAMEN_DETAILS;
	}
	
	@GetMapping("/{examen_id}/newTry")
	public String examenTry(@PathVariable("examen_id") int examen_id, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		int numero_pregunta;
		if(model.getAttribute("numero_pregunta")==null) {
			numero_pregunta = 0;
		}else {
			numero_pregunta = Integer.valueOf(String.valueOf(model.getAttribute("numero_pregunta")));
		}
		List<Pregunta> preguntas = examenService.findById(examen_id).getPreguntas();
		Pregunta pregunta = preguntas.get(numero_pregunta);
		List<Opcion> opciones = new ArrayList<Opcion>();
		if(pregunta.getTipoTest()!=null) {
			opciones = pregunta.getTipoTest().getOpciones();
		}
		Respuesta respuesta = new Respuesta();
		int numeroOpciones = opciones.size();
		Examen examen = examenService.findById(examen_id);
		int size = examen.getPreguntas().size();
		Intento intento = new Intento();
		intento.setPuntuacion(null);
		intento.setFecha(LocalDate.now());
		intento.setExamen(examenService.findById(examen_id));
		intento.setRespuestas(new ArrayList<Respuesta>());
		intentoService.save(intento);
		model.addAttribute("examen", examen);
		model.addAttribute("intento", intento);
		model.addAttribute("pregunta", pregunta.getContenido());
		model.addAttribute("opciones", opciones);
		model.addAttribute("respuesta", respuesta);
		model.addAttribute("numero_pregunta", numero_pregunta);
		model.addAttribute("size", size);
		model.addAttribute("numeroOpciones", numeroOpciones);
		return EXAMEN_TRY;
	}
	
	@PostMapping("/{examen_id}/newTry")
	public String examenTry(@PathVariable("examen_id") int examen_id, 
			Respuesta respuesta, ModelMap model, 
			@RequestParam(value="intento", required= true) Integer intentoId) {
		System.out.println("Antes de guardar");
		respuestaService.save(respuesta);
		System.out.println("Se guarda");
		Integer numero_pregunta = respuesta.getNumeroPregunta();
		System.out.println("Coge el número de la pregunta");
		respuesta.setTextoRespuesta("");
        Intento intento = intentoService.findById(intentoId);
		System.out.println("Localiza el intento");
        List<Respuesta> respuestas = new ArrayList<Respuesta>();
        respuestas.addAll(intento.getRespuestas());
        respuestas.add(respuesta);
		System.out.println("Añade la respuesta al conjunto de respuestas del intento");
        intento.setRespuestas(respuestas);
		System.out.println(intento.getId());
		intentoService.save(intento);
		System.out.println("Guarda el intento");
		Examen examen = examenService.findById(examen_id);
		System.out.println("Encuentra el examen");
		if(numero_pregunta>=examen.getPreguntas().size()-1) {
			Logro logro = logroService.findByName("Hiciste un examen");
			logroController.addLogro(logro);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			log.info("El usuario " + username + "terminó el intento " + intento.getId() + " del examen " + examen_id);
			Usuario usuario = usuarioService.findByUsername(username);
			usuario.getIntentos().add(intento);
			usuarioService.save(usuario);
			model.addAttribute("message", "You finished the exam!");
			return listExamenes(model);
		}else {
			numero_pregunta++;
			model.addAttribute("numero_pregunta", numero_pregunta);
			return "redirect:/" + intento.getId() + "/continue";
		}
	}
	

	
	@GetMapping("/{intento_id}/continue")
	public String examenContinue(@PathVariable("intento_id") int intento_id, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		int numero_pregunta;
		if(model.getAttribute("numero_pregunta")==null) {
			numero_pregunta = 0;
		}else {
			numero_pregunta = Integer.valueOf(String.valueOf(model.getAttribute("numero_pregunta")));
		}
		Intento intento = intentoService.findById(intento_id);
		Respuesta respuesta = new Respuesta();
		List<Pregunta> preguntas =  intento.getExamen().getPreguntas();
		respuesta.setTextoRespuesta("");
		Pregunta pregunta = preguntas.get(numero_pregunta);
		List<Opcion> opciones = new ArrayList<Opcion>();
		if(pregunta.getTipoTest()!=null) {
			opciones = pregunta.getTipoTest().getOpciones();
		}
		int numeroOpciones = opciones.size();
		Examen examen = examenService.findById(intento.getExamen().getId());
		int size = examen.getPreguntas().size();
		model.addAttribute("examen", examen);
		model.addAttribute("pregunta", pregunta.getContenido());
		model.addAttribute("intento", intento);
		model.addAttribute("opciones", opciones);
		model.addAttribute("respuesta", respuesta);
		model.addAttribute("numero_pregunta", numero_pregunta);
		model.addAttribute("size", size);
		model.addAttribute("numeroOpciones", numeroOpciones);
		return EXAMEN_TRY;
	}
	

	
	@PostMapping("/{intento_id}/continue")
	public String examenContinue(@PathVariable("intento_id") int intento_id, Respuesta respuesta, ModelMap model) {
		Integer numero_pregunta = respuesta.getNumeroPregunta();
		respuestaService.save(respuesta);
        Intento intento = intentoService.findById(intento_id);
        List<Respuesta> respuestas = intento.getRespuestas();
        respuestas.add(respuesta);
        intento.setRespuestas(respuestas);
		intentoService.save(intento);
		Examen examen = intento.getExamen();
		if(numero_pregunta>=examen.getPreguntas().size()-1) {
			Logro logro = logroService.findByName("Hiciste un examen");
			logroController.addLogro(logro);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			log.info("El usuario " + username + "terminó el intento " + intento_id + " del examen " + intento.getExamen().getId());
			Usuario usuario = usuarioService.findByUsername(username);
			usuario.getIntentos().add(intento);
			usuarioService.save(usuario);
			model.addAttribute("message", "You finished the exam!");
			return listExamenes(model);
		}else {
			numero_pregunta++;
			model.addAttribute("numero_pregunta", numero_pregunta);
			return "redirect:/" + intento.getId() + "/continue";
		}
	}

}
