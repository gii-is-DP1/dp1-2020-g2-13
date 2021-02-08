package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.model.Notificacion;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.HiloService;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.samples.petclinic.service.NotificacionService;
import org.springframework.samples.petclinic.service.OwnerService;
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
public class ComentarioController {
	public static final String COMENTARIOS_FORM = "comentarios/createOrUpdateComentariosForm";
	public static final String COMENTARIOS_LISTING = "comentarios/ComentariosListing";
	public static final String MEJORAR_CUENTA = "usuarios/mejorarCuenta";
	public static final String LOGIN = "login";
	public static final String ERROR = "error";

	@Autowired
	ComentarioService comentarioService;
	@Autowired
	HiloService hiloService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	NotificacionService notificacionService;
	@Autowired
	LogroService logroService;
	@Autowired
	LogroController logroController;

	@InitBinder("comentario")
	public void initComentarioBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ComentarioValidator());
	}

	private String auxViewHilo(int id, ModelMap model) {
		Hilo hilo = hiloService.findById(id);
		Collection<Comentario> comentarios = comentarioService.findByHiloId(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuario = usuarioService.findByUsername(username);
		List<Notificacion> notificaciones = new ArrayList<>();
		notificaciones.addAll(notificacionService.findByUserId(usuario.getId()));
		for(Notificacion n : notificaciones) {
			try {
				if (n.getComentario().getHilo().equals(hilo)) {
					notificacionService.delete(n);
				}
			} catch (Exception e) {
				
			}
		}
		model.addAttribute("hilo", hilo);
		model.addAttribute("comentarios", comentarios);
		model.addAttribute("usuario", usuario);
		String authority = AuthController.highestLevel();
		model.addAttribute("authority", authority);
		return COMENTARIOS_LISTING;
	}

	@GetMapping("/{value}")
	public String viewHilo(@PathVariable("value") int id, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		return auxViewHilo(id, model);	
	}

	@GetMapping("/{value}/new")
	public String editNewComentario(ModelMap model, @PathVariable("value") int id) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Hilo hilo = hiloService.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuario = usuarioService.findByUsername(username);
		model.addAttribute("hilo", hilo);
		model.addAttribute("comentario", new Comentario());
		model.addAttribute("usuario", usuario);
		return COMENTARIOS_FORM;
	}

	@GetMapping("/{value}/{cita}/new")
	public String editNewComentarioConCita(ModelMap model, 
			@PathVariable("value") int id, @PathVariable("cita") int cita) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Hilo hilo = hiloService.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuario = usuarioService.findByUsername(username);
		model.addAttribute("hilo", hilo);
		model.addAttribute("comentario", new Comentario());
		model.addAttribute("cita", cita);
		model.addAttribute("usuario", usuario);
		return COMENTARIOS_FORM;
	}

	@GetMapping("/{value}/delete/{comment}")
	public String deleteComentario(@PathVariable("value") int id, @PathVariable("comment") int comment,
			ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Comentario comentario = comentarioService.findById(comment);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if (!AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		int idlog = comentario.getId();
		comentarioService.delete(comentario);
		log.info("El usuario " +username + " ha eliminado el comentario " +idlog);
		model.addAttribute("message", "El comentario ha sido eliminado");
		return viewHilo(id, model);
	}

	@PostMapping("/{value}/new")
	public String saveNewComentario(@PathVariable("value") int id, @Valid Comentario comentario, BindingResult binding,
			ModelMap model) {
		if (binding.hasErrors()) {
			Collection<Usuario> usuarios = usuarioService.findAll();
			model.addAttribute("usuarios", usuarios);
			return COMENTARIOS_FORM;
		} else {
			comentario.setVersion(0);
			Logro logro = logroService.findByName("Primer comentario");
			logroController.addLogro(logro);
			comentarioService.save(comentario);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String usuarioActual = authentication.getName();
			log.info("El usuario " +usuarioActual + " ha creado el comentario " +comentario.getId() + " con versión " + comentario.getVersion());
			model.addAttribute("message", "El comentario se ha creado exitosamente");
			return viewHilo(id, model);
		}
	}

	@PostMapping("/{value}/{cita}/new")
	public String saveNewComentarioConCita(@PathVariable("value") int id, @Valid Comentario comentario, BindingResult binding,
			ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}


		if (binding.hasErrors()) {
			Collection<Usuario> usuarios = usuarioService.findAll();
			model.addAttribute("usuarios", usuarios);
			return COMENTARIOS_FORM;
		} else {
			comentario.setVersion(0);
			Logro logro = logroService.findByName("Primer comentario");
			logroController.addLogro(logro);
			comentarioService.save(comentario);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String usuarioActual = authentication.getName();
			log.info("El usuario " +usuarioActual + " ha creado el comentario " +comentario.getId() + " con versión " + comentario.getVersion());
			model.addAttribute("message", "El comentario se ha creado de manera exitosa");
			return viewHilo(id, model);
		}
	}

//	@GetMapping("/{value}/edit/{comment}")
	public String editComentario(@PathVariable("value") int value, @PathVariable("comment") int comment, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Comentario comentario = comentarioService.findById(comment);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if (!comentario.getUsuario().equals(usuarioLoggeado) && !AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		Hilo hilo = hiloService.findById(value);
		model.addAttribute("hilo", hilo);
		model.addAttribute("usuario", usuarioLoggeado);
		model.addAttribute("comentario", comentario);
		return COMENTARIOS_FORM;
	}

//	@PostMapping("/{value}/edit/{comment}")
	public String editComentario(@PathVariable("comment") int comment, @PathVariable("value") int id, 
			@Valid Comentario modifiedComentario, BindingResult binding, ModelMap model,
			@RequestParam(value="version", required= false) Integer version) {
		Comentario comentario = comentarioService.findById(comment);
		if (binding.hasErrors()) {
			Collection<Usuario> usuarios = usuarioService.findAll();
			model.addAttribute("usuarios", usuarios);
			return COMENTARIOS_FORM;
			
		} else {
			modifiedComentario.setVersion(version+1);
			BeanUtils.copyProperties(modifiedComentario, comentario, "id");
			comentarioService.save(comentario);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String usuarioActual = authentication.getName();
			log.info("El usuario " +usuarioActual + " ha editado el comentario " +comentario.getId() + " con versión " + comentario.getVersion());
			model.addAttribute("message", "The comentario fue editado exitosamente");
			return viewHilo(id, model);
		}
	}

	@GetMapping("/{value}/subscribe")
	public String subsccribeToThread(@PathVariable("value") int value,
			ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		Hilo hilo = hiloService.findById(value);
		model.addAttribute("hilo", hilo);
		model.addAttribute("usuario", usuarioLoggeado);
		hiloService.suscribir(hilo, usuarioLoggeado);
		return COMENTARIOS_LISTING;
	}
}
