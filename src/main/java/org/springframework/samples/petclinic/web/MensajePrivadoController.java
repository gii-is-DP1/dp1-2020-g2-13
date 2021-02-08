package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.MensajePrivado;
import org.springframework.samples.petclinic.model.Notificacion;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.MensajePrivadoService;
import org.springframework.samples.petclinic.service.NotificacionService;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mensajesPrivados")
public class MensajePrivadoController {
	public static final String MENSAJES_PRIVADOS_FORM = "mensajesPrivados/createOrUpdateMensajePrivadoForm";
	public static final String MENSAJES_PRIVADOS_LISTING = "mensajesPrivados/mensajesPrivadosListing";
	public static final String MEJORAR_CUENTA = "usuarios/mejorarCuenta";
	public static final String LOGIN = "login";
	public static final String ERROR = "error";

	@Autowired
	MensajePrivadoService mensajePrivadoService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	UserService userService;
	
	@Autowired
	NotificacionService notificacionService;
	
	@InitBinder("mensajePrivado")
	public void initMensajePrivadoBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new MensajePrivadoValidator());
	}

	@GetMapping("/{value}")
	public String listMensajesPrivados(@PathVariable("value") int receptor, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario emisor = usuarioService.findByUsername(username);
		Usuario r = usuarioService.findById(receptor);
		model.addAttribute("mensajesPrivados", mensajePrivadoService.findByUsersId(emisor.getId(), receptor));
		model.addAttribute("emisor", emisor);
		String authority = AuthController.highestLevel();
		model.addAttribute("authority", authority);
		model.addAttribute("receptor", r);
		List<Notificacion> notificaciones = new ArrayList<>();
		notificaciones.addAll(notificacionService.findByUserId(emisor.getId()));
		for(Notificacion n : notificaciones) {
			try {
				if (n.getMensajePrivado().getEmisor().equals(r)) {
					notificacionService.delete(n);
				}
			} catch (Exception e) {
				
			}
		}
		return MENSAJES_PRIVADOS_LISTING;
	}

	@GetMapping("{value}/new")
	public String editNewMensajesPrivados(@PathVariable("value") int receptor, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario emisor = usuarioService.findByUsername(username);
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
			Usuario emisor = usuarioService.findByUsername(username);
			model.addAttribute("mensajePrivado", new MensajePrivado());
			model.addAttribute("emisor", emisor);
			model.addAttribute("receptor", usuarioService.findById(receptor));
			return MENSAJES_PRIVADOS_FORM;
		} else {
			mensajePrivadoService.save(mensajePrivado);
			model.addAttribute("message", "El mensaje ha sido enviado con éxito.");
			return "redirect:/" +"mensajesPrivados/"+receptor;
		}
	}

	//@GetMapping("/{value}/delete")
	public String deleteMensajesPrivados(@PathVariable("value") int id, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		MensajePrivado mensajePrivado = mensajePrivadoService.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if (!mensajePrivado.getEmisor().equals(usuarioLoggeado) && !AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		mensajePrivadoService.delete(mensajePrivado);
		model.addAttribute("message", "El mensaje ha sido eliminado");
		return "redirect:/" +"mensajesPrivados/"+id;
	}

	//@GetMapping("/{value}/edit")
	public String editMensajePrivado(@PathVariable("value") int value, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario emisor = usuarioService.findByUsername(username);
		MensajePrivado mensajePrivado = mensajePrivadoService.findById(value);
		if (!mensajePrivado.getEmisor().equals(emisor) && !AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		model.addAttribute("mensajePrivado", mensajePrivado);
		model.addAttribute("emisor", mensajePrivado.getEmisor());
		model.addAttribute("receptor", mensajePrivado.getReceptor());
		return MENSAJES_PRIVADOS_FORM;
	}

	//@PostMapping("/{value}/edit")
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

			return "redirect:/" +"mensajesPrivados/"+mensajePrivado.getReceptor().getId();
		}
	}
}