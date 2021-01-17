package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.HiloService;
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
		model.addAttribute("usuarios", usuarios);
		return HILOS_FORM;
	}

//	@GetMapping("/{id}")
//	public String viewHilo(@PathVariable("id") int id, ModelMap model) {
//		return auxViewHilo(id, model);
//	}

	@PostMapping("/{id}/edit")
	public String editHilo(@PathVariable("id") int id, @Valid Hilo modifiedHilo, BindingResult binding,
			ModelMap model) {
		Hilo hilo = hiloService.findById(id);
		if (binding.hasErrors()) {
			return HILOS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedHilo, hilo, "id");
			hiloService.save(hilo);
			model.addAttribute("message", "Thread updated succesfully!");
			return listHilos(model);
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
		model.addAttribute("message","The thread was deleted successfully!");
		return listHilos(model);
	}
	 
	@GetMapping("/new")
	public String editNewHilo(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		model.addAttribute("hilo",new Hilo());
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
			hilo.setUsuario(usuarioLoggeado); 
			hiloService.save(hilo);
			model.addAttribute("message", "The thread was created successfully!");			
			return listHilos(model);
		}
	}
//
//	@PostMapping("/{value}")
//	public String saveNewComentario(@Valid Comentario comentario, BindingResult binding,ModelMap model) {
//		int id = comentario.getHilo().getId();
//		if(binding.hasErrors()) {
//			model.addAttribute("message", "El comentario no es válido.");
//		}else {
//			comentarioService.save(comentario);
//			model.addAttribute("message", "El comentario se ha publicado.");
////			try {
////				comentarioService.save(comentario);
////				model.addAttribute("message", "El comentario se ha publicado.");
////			}
////			catch (ImpossibleComentarioException ex){
////				model.addAttribute("message", "El comentario no es válido.");
////			}
//		}
//		return auxViewHilo(id, model);
//	}
}
