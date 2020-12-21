package org.springframework.samples.petclinic.web;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Video;
import org.springframework.samples.petclinic.service.UsuarioService;
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
	public static final String VIDEOS_VISUALIZE = "videos/VideoVisualize";

	@Autowired
	VideoService videoService;
	
	@Autowired
	UsuarioService usuarioService;

	@GetMapping
	public String listVideos(ModelMap model) {
		model.addAttribute("videos", videoService.findAll());
		model.addAttribute("usuarios", usuarioService.findAll());

		return VIDEOS_LISTING;
	}

	@GetMapping("/{id}/delete")
	public String deleteVideo(@PathVariable("id") int id, ModelMap model) {
		Video video = videoService.findById(id);
		videoService.delete(video);
		model.addAttribute("message", "The video was deleted successfully!");
		return listVideos(model);
	}

	@GetMapping("/{id}/visualize")
	public String visualizeVideo(@PathVariable("id") int id, ModelMap model) {

		Video video = videoService.findById(id);
		String enlace = video.getLink();

		String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(enlace);
		String youtubeId = null;
		if (matcher.find()) {
			youtubeId = matcher.group();
		}
		String embedLink = "https://www.youtube.com/embed/" + youtubeId + "?autohide=0";
		model.addAttribute("embedLink", embedLink);
		model.addAttribute("video", video);
		return VIDEOS_VISUALIZE;
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
		model.addAttribute("video", new Video());
		return VIDEOS_FORM;
	}

	@PostMapping("/new")
	public String saveNewVideo(@Valid Video video, BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			return VIDEOS_FORM;
		} else {
			videoService.save(video);
			model.addAttribute("message", "The video was uploaded successfully!");
			return listVideos(model);
		}
	}
}
