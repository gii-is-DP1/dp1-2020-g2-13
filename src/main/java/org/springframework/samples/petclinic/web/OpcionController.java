package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.samples.petclinic.model.TipoTest;
import org.springframework.samples.petclinic.service.OpcionService;
import org.springframework.samples.petclinic.service.PreguntaService;
import org.springframework.samples.petclinic.service.TipoTestService;
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
	
	@InitBinder("opcion")
	public void initOpcionBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new OpcionValidator());
	}
	 
	@GetMapping("/{id_examen}/{id_pregunta}/new")
	public String editNewOpcion(@PathVariable("id_examen") int id_examen, @PathVariable("id_pregunta") int id_pregunta, ModelMap model) {
		model.addAttribute("opcion",new Opcion());
		Pregunta pregunta = preguntaService.findById(id_pregunta);
		model.addAttribute("pregunta", pregunta);
		model.addAttribute("id_examen", id_examen);
		return OPCION_FORM;
	}
	
	@PostMapping("/{id_examen}/{id_pregunta}/new")
	public String saveNewOpcion(@PathVariable("id_examen") int id_examen, @PathVariable("id_pregunta") int id_pregunta,@Valid Opcion opcion, BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			Pregunta pregunta = preguntaService.findById(id_pregunta);
			model.addAttribute("pregunta", pregunta);
			model.addAttribute("id_examen", id_examen);
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
	
	@GetMapping("/{id_examen}/{id_pregunta}/{id}/delete")
	public String deleteOpcion(@PathVariable("id") int id, @PathVariable("id_examen") int id_examen, @PathVariable("id_pregunta") int id_pregunta, ModelMap model) {
		Pregunta pregunta = preguntaService.findById(id_pregunta);
		TipoTest tipoTest = pregunta.getTipoTest();
		List<Opcion> opciones = tipoTest.getOpciones();
		Opcion opcion = opcionService.findById(id);
		opciones.remove(opcion);
		tipoTest.setOpciones(opciones);
		tipoTestService.save(tipoTest);
		opcionService.delete(opcion);
		TipoTest tipoTest2 = tipoTestService.findById(tipoTest.getId());
		if(tipoTest2.getOpciones().size()==0) {
			pregunta.setTipoTest(null);
			preguntaService.save(pregunta);
			tipoTestService.delete(tipoTest2);
		}
		model.addAttribute("message", "The option was deleted successfully!");
		return examenController.examenDetails(id_examen, model);
	}

}
