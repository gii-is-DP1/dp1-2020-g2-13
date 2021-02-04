package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.HiloService;
import org.springframework.samples.petclinic.service.LogroService;
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
@Slf4j
@RequestMapping("/hilos")
public class HiloController {
	public static final String HILOS_FORM = "hilos/createOrUpdateHilosForm";
	public static final String HILOS_LISTING = "hilos/HilosListing";
	public static final String HILO_VISTA = "hilos/vistaHilo";
	public static final String MEJORAR_CUENTA = "usuarios/mejorarCuenta";
	public static final String LOGIN = "login";
	public static final String ERROR = "error";
	
//	private String auxViewHilo(int id, ModelMap model) {
//		Hilo hilo = hiloService.findById(id);
//		Collection<Comentario> comentarios = comentarioService.findByHiloId(id);
//		Collection<Usuario> usuarios = usuarioService.findAll();
//		model.addAttribute("hilo", hilo);
//		model.addAttribute("comentario", new Comentario());
//		model.addAttribute("comentarios", comentarios);
//		model.addAttribute("usuarios", usuarios);
//		return HILO_VISTA;
//	}

	@Autowired
	HiloService hiloService;
	@Autowired
	ComentarioService comentarioService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	LogroService logroService;
	@Autowired
	LogroController logroController;
	

	@InitBinder("hilo")
	public void initHiloBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new HiloValidator());
	}

	@GetMapping
	public String listHilos(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		model.addAttribute("hilos", hiloService.findAll());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuario = usuarioService.findByUsername(username);
		model.addAttribute("usuario", usuario);
		String authority = AuthController.highestLevel();
		model.addAttribute("authority", authority);
		return HILOS_LISTING;
	}
	
	@GetMapping("/{id}/edit")
	public String editHilo(@PathVariable("id") int id, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Hilo hilo = hiloService.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if (!hilo.getUsuario().equals(usuarioLoggeado) && !AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		Collection<Usuario> usuarios = usuarioService.findAll();
		model.addAttribute("hilo", hilo);
		model.addAttribute("usuario", usuarioLoggeado);
		return HILOS_FORM;
	}

//	@GetMapping("/{id}")
//	public String viewHilo(@PathVariable("id") int id, ModelMap model) {
//		return auxViewHilo(id, model);
//	}

	@PostMapping("/{id}/edit")
	public String editHilo(@PathVariable("id") int id, @Valid Hilo modifiedHilo, BindingResult binding,
			ModelMap model, @RequestParam(value="version", required= false) Integer version) {
		Hilo hilo = hiloService.findById(id);
		if(hilo.getVersion()!=version) {	
			model.put("message", "Alguien ha modificado simultáneamente el hilo, prueba otra vez");
			return editHilo(id, model);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();	
		if (binding.hasErrors()) {
			return HILOS_FORM;
		} else {
			modifiedHilo.setVersion(version+1);
			log.info("Editado el hilo con id: "+id+ " y con versión actual " + modifiedHilo.getVersion()+" por el usuario: "+username);
			BeanUtils.copyProperties(modifiedHilo, hilo, "id");
			hiloService.save(hilo);
			model.addAttribute("message", "Hilo actualizado");
			return "redirect:/" +"hilos";
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteHilo(@PathVariable("id") int id,ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Hilo hilo = hiloService.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if (!hilo.getUsuario().equals(usuarioLoggeado) && !AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		hiloService.delete(hilo);
		model.addAttribute("message","Hilo eliminado");
		
		log.info("Hilo con id "+ id + " fue eliminado por el usuario " + username);	
		return "redirect:/" +"hilos";
	}
	 
	@GetMapping("/new")
	public String editNewHilo(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		model.addAttribute("hilo",new Hilo());
		model.addAttribute("usuario", usuarioLoggeado);
		return HILOS_FORM;
	}
	
	@PostMapping("/new")
	public String saveNewHilo(@Valid Hilo hilo, BindingResult binding, ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if(binding.hasErrors()) {	
			Collection<Usuario> usuarios = usuarioService.findAll();
			model.addAttribute("usuarios", usuarios);		
			return HILOS_FORM;
		}else {
			Logro logro = logroService.findByName("Creaste un hilo");
			logroController.addLogro(logro);
			hilo.setUsuario(usuarioLoggeado); 
			hilo.setVersion(0);
			hiloService.save(hilo);	
			model.addAttribute("message", "Nuevo hilo añadido");
			log.info("Un nuevo hilo con id "+ hilo.getId() + " fue creado por el usuario " + username + " con versión " + hilo.getVersion());
			return "redirect:/" +"hilos";
		}
	}
}
