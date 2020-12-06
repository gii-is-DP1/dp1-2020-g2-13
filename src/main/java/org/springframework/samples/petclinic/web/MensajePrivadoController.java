package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.model.MensajePrivado;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.samples.petclinic.service.MensajePrivadoService;
import org.springframework.samples.petclinic.service.UserService;
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
@RequestMapping("/mensajesPrivados")
public class MensajePrivadoController {
	public static final String MENSAJES_PRIVADOS_FORM = "mensajesPrivados/createOrUpdateMensajePrivadoForm";
	public static final String MENSAJES_PRIVADOS_LISTING = "mensajesPrivados/mensajesPrivadosListing";

	@Autowired
	MensajePrivadoService mensajePrivadoService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	UserService userService;

	@GetMapping("/{value}")
	public String listMensajesPrivados(@PathVariable("value") int receptor, ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Collection<Usuario> usuarios = usuarioService.findAll();
		Usuario emisor = null;
		for (Usuario u : usuarios) {
			if (u.getUser().getUsername().equals(username)) {
				emisor = u;
			}
		}
		model.addAttribute("mensajesPrivados", mensajePrivadoService.findByUsersId(emisor.getId(), receptor));
		model.addAttribute("emisor", emisor);
		model.addAttribute("receptor", usuarioService.findById(receptor));
		return MENSAJES_PRIVADOS_LISTING;
	}

	@GetMapping("{value}/new")
	public String editNewMensajesPrivados(@PathVariable("value") int receptor, ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Collection<Usuario> usuarios = usuarioService.findAll();
		Usuario emisor = null;
		for (Usuario u : usuarios) {
			if (u.getUser().getUsername().equals(username)) {
				emisor = u;
			}
		}
		model.addAttribute("mensajePrivado", new MensajePrivado());
		model.addAttribute("emisor", emisor);
		model.addAttribute("receptor", usuarioService.findById(receptor));
		return MENSAJES_PRIVADOS_FORM;
	}

	@PostMapping("{value}/new")
	public String saveNewMensajesPrivados(@PathVariable("value") int receptor, @Valid MensajePrivado mensajePrivado,
			BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Collection<Usuario> usuarios = usuarioService.findAll();
			Usuario emisor = null;
			for (Usuario u : usuarios) {
				if (u.getUser().getUsername().equals(username)) {
					emisor = u;
				}
			}
			model.addAttribute("mensajePrivado", new MensajePrivado());
			model.addAttribute("emisor", emisor);
			model.addAttribute("receptor", usuarioService.findById(receptor));
			return MENSAJES_PRIVADOS_FORM;
		} else {
			mensajePrivadoService.save(mensajePrivado);
			model.addAttribute("message", "El mensaje ha sido enviado con éxito.");
			return listMensajesPrivados(receptor, model);
		}
	}

	@GetMapping("/{value}/delete")
	public String deleteMensajesPrivados(@PathVariable("value") int id, ModelMap model) {
		MensajePrivado mensajePrivado = mensajePrivadoService.findById(id);
		mensajePrivadoService.delete(mensajePrivado);
		model.addAttribute("message", "El mensaje ha sido eliminado");
		return listMensajesPrivados(id, model);
	}
	
	

	@PostMapping("/{value}/edit")
	public String editMensajesPrivados(@PathVariable("value") int id, @Valid Comentario modifiedMensajePrivado,
			BindingResult binding, ModelMap model) {
		MensajePrivado mensajePrivado = mensajePrivadoService.findById(id);
		if (binding.hasErrors()) {
			Collection<Usuario> usuarios = usuarioService.findAll();
			model.addAttribute("usuarios", usuarios);
			return MENSAJES_PRIVADOS_LISTING;
		} else {
			BeanUtils.copyProperties(modifiedMensajePrivado, mensajePrivado, "id");
			mensajePrivadoService.save(mensajePrivado);
			model.addAttribute("message", "The comentario was created successfully!");
			return listMensajesPrivados(id, model);
		}
	}
}