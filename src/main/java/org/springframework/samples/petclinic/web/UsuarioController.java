package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ExamenService;
import org.springframework.samples.petclinic.service.HiloService;
import org.springframework.samples.petclinic.service.NotificacionService;
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
@RequestMapping("/usuarios")
public class UsuarioController {
	public static final String USUARIOS_LISTING = "usuarios/UsuariosListing";

	public static final String USUARIOS_FORM = "usuarios/createOrUpdateUsuariosForm";
	
	public static final String MEJORAR_CUENTA = "usuarios/mejorarCuenta";
	
	public static final String LOGIN = "login";

	public static final String PERFIL = "usuarios/perfil";

	public static final String ERROR = "";

	@Autowired
	UsuarioService usuarioService;
	@Autowired
	HiloService hiloService;

	@Autowired
	ExamenService examenService;
	
	@Autowired
	NotificacionService notificacionService;
	
	@Autowired
	AuthoritiesService authoritiesService;
	
	@Autowired
	AuthController authController;

	@InitBinder("usuario")
	public void initUsuarioBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new UsuarioValidator());
	}

	@GetMapping
	public String listUsuarios(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		model.addAttribute("usuarios", usuarioService.findAll());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuario = usuarioService.findByUsername(username);
		model.addAttribute("usuario", usuario);
		String authority = AuthController.highestLevel();
		model.addAttribute("authority", authority);
		return USUARIOS_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String editPdf(@PathVariable("id") int id, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if (!usuarioLoggeado.getId().equals(id) && !AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
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
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if (!usuarioLoggeado.getId().equals(id) && !AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
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
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		model.addAttribute("usuario", usuarioService.findById(usuarioId));
		model.addAttribute("hilos", hiloService.findByUsuarioId(usuarioId));
		return "usuarios/UsuarioHilos";
	}

	@GetMapping(value = "{usuarioId}/examenes")
	public String getExamenAuthor(@PathVariable("usuarioId") int usuarioId, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		model.addAttribute("usuario", usuarioService.findById(usuarioId));
		model.addAttribute("examenes", examenService.findByUsuarioId(usuarioId));
		return "usuarios/UsuarioHilos";
	}

	@GetMapping("/{id}/perfil")
	public String perfil(@PathVariable("id") int id, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		Usuario usuario = usuarioService.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if (!usuarioLoggeado.getId().equals(id)) {
			if (!AuthController.hasPaid()) {
				return "redirect:/" + MEJORAR_CUENTA;
			}
		}
		String authority = AuthController.highestLevel();
		String visitingUserAuthority = authController.highestLevel(usuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("usuarioLoggeado", usuarioLoggeado);
		model.addAttribute("authority", authority);
		model.addAttribute("visitingUserAuthority", visitingUserAuthority);
		return PERFIL;
	}

	@GetMapping("/mejorarCuenta")
	public String upgradeAccountView(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		return MEJORAR_CUENTA;
	}

	@GetMapping("/mejorarCuenta/confirma")
	public String upgradeAccount(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuario = usuarioService.findByUsername(username);
		usuarioService.upgradeAccount(usuario);
		model.addAttribute("message", "¡Has mejorado tu cuenta!");
		return listUsuarios(model);
	}

	@GetMapping("/empeorarCuenta")
	public String downgradeAccount(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		if (AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuario = usuarioService.findByUsername(username);
		usuarioService.downgradeAccount(usuario);
		model.addAttribute("message", "¡Has empeorado tu cuenta!");
		return listUsuarios(model);
	}

	@GetMapping("/{value}/hacerAdministrador")
	public String hacerAdministrador(@PathVariable("value") int value, ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		Usuario usuario = usuarioService.findById(value);
		usuarioService.hacerAdministrador(usuario);
		model.addAttribute("message", "¡El usuario ahora es administrador!");
		return listUsuarios(model);
	}

	@GetMapping("/quitarAdministrador")
	public String quitarAdministrador(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		int i = 0;
		Iterable<Authorities> authorities = authoritiesService.findAll();
		for (Authorities a : authorities) {
			if (a.getAuthority().equals("admin")) {
				i++;
			}
		}
		if (i > 1) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Usuario usuario = usuarioService.findByUsername(username);
			usuarioService.quitarAdministrador(usuario);
			model.addAttribute("message", "¡Ahora no eres administrador!");
		}
		else {
			model.addAttribute("message", "No se ha podido completar, no quedarían administradores.");
		}
		return listUsuarios(model);
	}
}
