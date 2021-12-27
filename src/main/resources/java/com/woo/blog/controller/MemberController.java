package com.woo.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class MemberController {
	@RequestMapping("/form.do")
	public String form() {

		return "uploadForm";
	}
}
