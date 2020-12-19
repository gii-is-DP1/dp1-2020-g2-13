package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ExamenService;
import org.springframework.samples.petclinic.service.HiloService;
import org.springframework.samples.petclinic.service.NotificacionService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.scheduling.annotation.Scheduled;
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
@RequestMapping("/usuarios")
public class UsuarioController {
	public static final String USUARIOS_LISTING = "usuarios/UsuariosListing";

	public static final String USUARIOS_FORM = "usuarios/createOrUpdateUsuariosForm";

	public static final String PERFIL = "usuarios/perfil";

	@Autowired
	UsuarioService usuarioService;
	@Autowired
	HiloService hiloService;

	@Autowired
	ExamenService examenService;
	
	@Autowired
	NotificacionService notificacionService;

	@InitBinder("usuario")
	public void initUsuarioBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new UsuarioValidator());
	}

	@GetMapping
	public String listUsuarios(ModelMap model) {
		model.addAttribute("usuarios", usuarioService.findAll());
		return USUARIOS_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String editPdf(@PathVariable("id") int id, ModelMap model) {
		Usuario usuario = usuarioService.findById(id);
		model.addAttribute("usuario", usuario);
		return USUARIOS_FORM;

	}

	@PostMapping("/{id}/edit")
	public String editUsuario(@PathVariable("id") int id, @Valid Usuario modifiedUsuario, BindingResult binding,
			ModelMap model) {
		Usuario usuario = usuarioService.findById(id);
		if (binding.hasErrors()) {
			return USUARIOS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedUsuario, usuario, "id");
			usuarioService.save(usuario);
			model.addAttribute("message", "User updated succesfully!");
			return listUsuarios(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteUsuario(@PathVariable("id") int id, ModelMap model) {
		Usuario usuario = usuarioService.findById(id);

		usuarioService.delete(usuario);
		model.addAttribute("message", "The user was deleted successfully!");
		return listUsuarios(model);

	}

	@GetMapping("/new")
	public String editNewUsuario(ModelMap model) {
		model.put("usuario", new Usuario());
		return USUARIOS_FORM;
	}

	@PostMapping("/new")
	public String saveNewUsuario(@Valid Usuario usuario, BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			return USUARIOS_FORM;
		} else {
			usuarioService.save(usuario);
			model.addAttribute("message", "The user was created successfully!");
			return listUsuarios(model);
		}
	}

	@GetMapping(value = "{usuarioId}/hilos")
	public String getPetClinicHistory(@PathVariable("usuarioId") int usuarioId, ModelMap model) {
		model.addAttribute("usuario", usuarioService.findById(usuarioId));
		model.addAttribute("hilos", hiloService.findByUsuarioId(usuarioId));
		return "usuarios/UsuarioHilos";
	}

	@GetMapping(value = "{usuarioId}/examenes")
	public String getExamenAuthor(@PathVariable("usuarioId") int usuarioId, ModelMap model) {
		model.addAttribute("usuario", usuarioService.findById(usuarioId));
		model.addAttribute("examenes", examenService.findByUsuarioId(usuarioId));
		return "usuarios/UsuarioHilos";
	}

	@GetMapping("/{id}/perfil")
	public String perfil(@PathVariable("id") int id, ModelMap model) {
		Usuario usuario = usuarioService.findById(id);

		model.addAttribute("usuario", usuario);
		return PERFIL;

	}
	

	

}
