package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Video;
import org.springframework.samples.petclinic.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/videos")
public class VideoController {
	
	public static final String VIDEOS_LISTING = "videos/VideosListing";
	public static final String VIDEOS_FORM = "videos/CreateOrUpdateVideoForm";
	
	@Autowired
	VideoService videoService;
	
	@GetMapping
	public String listVideos(ModelMap model) {
		model.addAttribute("videos", videoService.findAll());
		return VIDEOS_LISTING;
	}	
	
	@GetMapping("/{id}/delete")
	public String deleteVideo(@PathVariable("id") int id,ModelMap model) {
		Video video=videoService.findById(id);
			videoService.delete(video);
			model.addAttribute("message","The video was deleted successfully!");
			return listVideos(model);
	}
	
	@GetMapping("/{id}/edit")
	public String editPdf(@PathVariable("id") int id, ModelMap model) {
		Video video = videoService.findById(id);
			model.addAttribute("video", video);
			return VIDEOS_FORM;
	}
	
	@PostMapping("/{id}/edit")
	public String editVideo(@PathVariable("id") int id, @Valid Video modifiedVideo, BindingResult binding,
			ModelMap model) {
		Video video = videoService.findById(id);
		if (binding.hasErrors()) {
			return VIDEOS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedVideo, video, "id");
			videoService.save(video);
			model.addAttribute("message", "Thread updated succesfully!");
			return listVideos(model);
		}
	}
	
	@GetMapping("/new")
	public String editNewVideo(ModelMap model) {
		model.addAttribute("video",new Video());
		return VIDEOS_FORM;
	}
	
	@PostMapping("/new")
	public String saveNewVideo(@Valid Video video, BindingResult binding,ModelMap model) {
		if(binding.hasErrors()) {			
			return VIDEOS_FORM;
		}else {
			videoService.save(video);
			model.addAttribute("message", "The video was uploaded successfully!");			
			return listVideos(model);
		}
	}
}
