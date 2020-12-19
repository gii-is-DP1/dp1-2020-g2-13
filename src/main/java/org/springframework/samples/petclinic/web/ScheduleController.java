package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.samples.petclinic.model.Notificacion;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.NotificacionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@RestController

public class ScheduleController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	NotificacionService notificacionService;
	
	public static void main(String[] args) {
        SpringApplication.run(ScheduleController.class, args);
    }
	
	@GetMapping("/cogerNotificaciones")
	public int cogerNotificaciones() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuario = usuarioService.findByUsername(username);
		int notificaciones = notificacionService.findByUserId(usuario.getId()).size();
		return notificaciones;
	}
}
