package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Examen;
import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.samples.petclinic.model.TipoTest;
import org.springframework.samples.petclinic.service.ExamenService;
import org.springframework.samples.petclinic.service.OpcionService;
import org.springframework.samples.petclinic.service.PreguntaService;
import org.springframework.samples.petclinic.service.TipoTestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/opciones")
public class OpcionController {

	public static final String OPCION_FORM = "opciones/createOrUpdateOpcionesForm";
	
	@Autowired
	OpcionService opcionService;
	
	@Autowired
	PreguntaService preguntaService;
	
	@Autowired
	TipoTestService tipoTestService;
	
	@Autowired
	ExamenController examenController;
	
	@GetMapping("/{id_examen}/{id_pregunta}/new")
	public String editNewOpcion(@PathVariable("id_examen") int id_examen, @PathVariable("id_pregunta") int id_pregunta, ModelMap model) {
		model.addAttribute("opcion",new Opcion());
		Pregunta pregunta = preguntaService.findById(id_pregunta);
		model.addAttribute("pregunta", pregunta);
		model.addAttribute("id_examen", id_examen);
		return OPCION_FORM;
	}
	
	@PostMapping("/{id_examen}/{id_pregunta}/new")
	public String saveNewOpcion(@PathVariable("id_examen") int id_examen, @PathVariable("id_pregunta") int id_pregunta, Opcion opcion, BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			return OPCION_FORM;
		} else {
			Pregunta pregunta = preguntaService.findById(id_pregunta);
			TipoTest tipoTest = pregunta.getTipoTest();
			List<Opcion> opciones = new ArrayList<Opcion>();
			if(tipoTest==null) {
				tipoTest = new TipoTest();
			}else {
				opciones = tipoTest.getOpciones();
			}
			opciones.add(opcion);
			opcionService.save(opcion);
			tipoTest.setOpciones(opciones);
			tipoTestService.save(tipoTest);
			pregunta.setTipoTest(tipoTest);
			preguntaService.save(pregunta);
			
			model.addAttribute("message", "The question was created successfully!");
			return examenController.examenDetails(id_examen, model);
		}
	}

}
