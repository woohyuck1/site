package com.woo.blog.controller;


import javax.activation.CommandMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.woo.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	


	
	//컨트롤러에서 세션을 어떻게 찾는지?
	@GetMapping({"","/"})
	public String index(Model model,@PageableDefault(size = 2,sort="id",direction = Sort.Direction.DESC)Pageable pageable) { 
		model.addAttribute("boards",boardService.list(pageable));
		return "index";
	}
	@GetMapping("/board/{id}")
	public String findByid(@PathVariable int id, Model model) {
	model.addAttribute("board",boardService.textread(id));
		
	return "board/detall";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.textread(id));
		return "board/updateForm";
	}
	
	
	//user권한이필요c
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
