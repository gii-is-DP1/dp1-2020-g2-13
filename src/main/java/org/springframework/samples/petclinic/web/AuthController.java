package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Notificacion;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.HiloService;
import org.springframework.samples.petclinic.service.NotificacionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
public class AuthController {
	
	@Autowired
	AuthoritiesService authoritiesService;
	
	public static boolean isAuthenticated() {
		boolean b = true;
		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority a : auth) {
			if (a.getAuthority().equals("ROLE_ANONYMOUS")) {
				b = false;
				break;
			}
		}
		return b;
	}

//	public static boolean isAuthenticated(Usuario usuario) {
//		boolean b = true;
//		Iterable<Authorities> auth = usuario.getUser().getAuthorities();
//		for (Authorities a : auth) {
//			if (a.getAuthority().equals("ROLE_ANONYMOUS")) {
//				b = false;
//				break;
//			}
//		}
//		return b;
//	}

	public static boolean hasPaid() {
		boolean b = false;
		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority a : auth) {
			if (a.getAuthority().equals("pagado")) {
				b = true;
				break;
			}
		}
		if (!b) {
			return isAdmin();
		}
		return b;
	}

//	public static boolean hasPaid(Usuario usuario) {
//		boolean b = false;
//		Iterable<Authorities> auth = usuario.getUser().getAuthorities();
//		for (Authorities a : auth) {
//			if (a.getAuthority().equals("pagado")) {
//				b = true;
//				break;
//			}
//		}
//		if (!b) {
//			return isAdmin();
//		}
//		return b;
//	}

	public static boolean isAdmin() {
		boolean b = false;
		Collection<? extends GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority a : auth) {
			if (a.getAuthority().equals("admin")) {
				b = true;
				break;
			}
		}
		return b;
	}

//	public static boolean isAdmin(Usuario usuario) {
//		boolean b = false;
//		Iterable<Authorities> auth = usuario.getUser().getAuthorities();
//		for (Authorities a : auth) {
//			if (a.getAuthority().equals("admin")) {
//				b = true;
//				break;
//			}
//		}
//		return b;
//	}
	
	public static String highestLevel() {
		if (isAdmin()) {
			return "admin";
		}
		if (hasPaid()) {
			return "pagado";
		}
		if (isAuthenticated()) {
			return "registrado";
		}
		return "invitado";
	}
	
	public String highestLevel(Usuario usuario) {
//		if (isAdmin(usuario)) {
//			return "admin";
//		}
//		if (hasPaid(usuario)) {
//			return "pagado";
//		}
//		if (isAuthenticated(usuario)) {
//			return "registrado";
//		}
		Collection<Authorities> auth = authoritiesService.findByUsername(usuario.getUser().getUsername());
		String s = "invitado";
		for (Authorities a : auth) {
			if (a.getAuthority().equals("admin")) {
				return "admin";
			}
			if (a.getAuthority().equals("pagado")) {
				s = "pagado";
			}
			if (a.getAuthority().equals("registrado") && s.equals("invitado")) {
				s = "registrado";
			}
		}
		return s;
	}
}
