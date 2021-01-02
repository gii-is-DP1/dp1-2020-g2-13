package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Notificacion;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.HiloService;
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

@Controller
@RequestMapping("/hilos")
public class ComentarioController {
	public static final String COMENTARIOS_FORM = "comentarios/createOrUpdateComentariosForm";
	public static final String COMENTARIOS_LISTING = "comentarios/ComentariosListing";
	public static final String LOGIN = "login";

	@Autowired
	ComentarioService comentarioService;
	@Autowired
	HiloService hiloService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	NotificacionService notificacionService;
	@Autowired
	OwnerService ownerService;

	@InitBinder("comentario")
	public void initComentarioBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ComentarioValidator());
	}

	private String auxViewHilo(int id, ModelMap model) {
		Hilo hilo = hiloService.findById(id);
		Collection<Comentario> comentarios = comentarioService.findByHiloId(id);
		model.addAttribute("hilo", hilo);
		model.addAttribute("comentarios", comentarios);
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
		return COMENTARIOS_LISTING;
	}

	@GetMapping("/{value}")
	public String viewHilo(@PathVariable("value") int id, ModelMap model) {
		if (!AuthController.hasPaid()) {
			return "redirect:/" + LOGIN;
		}
		System.out.println(AuthController.isAuthenticated());
		return auxViewHilo(id, model);	
	}

	@GetMapping("/{value}/new")
	public String editNewComentario(ModelMap model, @PathVariable("value") int id) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
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
		Hilo hilo = hiloService.findById(id);
		Collection<Usuario> usuarios = usuarioService.findAll();
		model.addAttribute("hilo", hilo);
		model.addAttribute("comentario", new Comentario());
		model.addAttribute("cita", cita);
		model.addAttribute("usuarios", usuarios);
		return COMENTARIOS_FORM;
	}

	@GetMapping("/{value}/{cita}/new")
	public String editNewComentarioConCita(ModelMap model, 
			@PathVariable("value") int id, @PathVariable("cita") int cita) {
		Hilo hilo = hiloService.findById(id);
		Collection<Usuario> usuarios = usuarioService.findAll();
		model.addAttribute("hilo", hilo);
		model.addAttribute("comentario", new Comentario());
		model.addAttribute("cita", cita);
		model.addAttribute("usuarios", usuarios);
		return COMENTARIOS_FORM;
	}

	@GetMapping("/{value}/delete/{comment}")
	public String deleteComentario(@PathVariable("value") int id, @PathVariable("comment") int comment,
			ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		Comentario comentario = comentarioService.findById(comment);
		comentarioService.delete(comentario);
		model.addAttribute("message", "El comentario ha sido eliminado");
		return viewHilo(id, model);
	}

	@PostMapping("/{value}/new")
	public String saveNewComentario(@PathVariable("value") int id, @Valid Comentario comentario, BindingResult binding,
			ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (binding.hasErrors()) {
			Collection<Usuario> usuarios = usuarioService.findAll();
			model.addAttribute("usuarios", usuarios);
			return COMENTARIOS_FORM;
		} else {
			comentarioService.save(comentario);
			model.addAttribute("message", "The comentario was created successfully!");
			return viewHilo(id, model);
		}
	}

	@PostMapping("/{value}/{cita}/new")
	public String saveNewComentarioConCita(@PathVariable("value") int id, @Valid Comentario comentario, BindingResult binding,
			ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (binding.hasErrors()) {
			Collection<Usuario> usuarios = usuarioService.findAll();
			model.addAttribute("usuarios", usuarios);
			return COMENTARIOS_FORM;
		} else {
			comentarioService.save(comentario);
			model.addAttribute("message", "The comentario was created successfully!");
			return viewHilo(id, model);
		}
	}

	@PostMapping("/{value}/{cita}/new")
	public String saveNewComentarioConCita(@PathVariable("value") int id, @Valid Comentario comentario, BindingResult binding,
			ModelMap model) {
		if (binding.hasErrors()) {
			Collection<Usuario> usuarios = usuarioService.findAll();
			model.addAttribute("usuarios", usuarios);
			return COMENTARIOS_FORM;
		} else {
			comentarioService.save(comentario);
			model.addAttribute("message", "The comentario was created successfully!");
			return viewHilo(id, model);
		}
	}

	@GetMapping("/{value}/edit/{comment}")
	public String editComentario(@PathVariable("value") int value, @PathVariable("comment") int comment, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		Hilo hilo = hiloService.findById(value);
		Comentario comentario = comentarioService.findById(comment);
		Collection<Usuario> usuarios = usuarioService.findAll();
		model.addAttribute("hilo", hilo);
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("comentario", comentario);
		return COMENTARIOS_FORM;
	}

	@PostMapping("/{value}/edit/{id}")
	public String editComentario(@PathVariable("id") int id, @Valid Comentario modifiedComentario,
			BindingResult binding, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		Comentario comentario = comentarioService.findById(id);
		if (binding.hasErrors()) {
			Collection<Usuario> usuarios = usuarioService.findAll();
			model.addAttribute("usuarios", usuarios);
			return COMENTARIOS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedComentario, comentario, "id");
			comentarioService.save(comentario);
			model.addAttribute("message", "The comentario was created successfully!");
			return viewHilo(id, model);
		}
	}
}
