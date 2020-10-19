package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    
	    String hello = "ALEATORIO US:  \n"
	    		+ "Guillermo López Rosado"
	    		+ "Isabel Arrans Vega"
	    		+ "Matthew Bwye Lera"
	    		+ "Pablo Colmenero Capote"
	    		+ "Francisco Javier Beltrán Rabadán"
	    		+ "Grupo G2-13";
	    List<Person> personas = new ArrayList<Person>();
	    Person p1 = new Person();
	    Person p2 = new Person();
	    Person p3 = new Person();
	    Person p4 = new Person();
	    Person p5 = new Person();
	    p1.setFirstName("Guillermo");
	    p1.setLastName("López Rosado");
	    
	    p2.setFirstName("Isabel");
	    p2.setLastName("Arrans Vega");
	    
	    p3.setFirstName("Matthew");
	    p3.setLastName("Bwye Lera");
	    
	    p4.setFirstName("Pablo");
	    p4.setLastName("Colmenero Capote");
	    
	    p5.setFirstName("Francisco Javier");
	    p5.setLastName("Beltrán Rabadán");
	    personas.add(p1);
	    personas.add(p2);
	    personas.add(p3);
	    personas.add(p4);
	    personas.add(p5);
	    model.put("personas", personas);
	    return "welcome";
	  }
}
