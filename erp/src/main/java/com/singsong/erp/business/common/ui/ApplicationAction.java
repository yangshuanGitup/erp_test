package com.singsong.erp.business.common.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationAction {
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("username", "系统管理员");
		return "/index";
	}

	@RequestMapping("/login")
	public String loginAction(HttpServletRequest request, Model model) {
		return "/fra/login";
	}

	@RequestMapping("/error")
	public String error(HttpServletRequest request, HttpServletResponse response) {
		return "/error";
	}

	@RequestMapping("/noPermiss")
	public String noPermiss(HttpServletRequest request, HttpServletResponse response) {
		return "/noPermiss";
	}
}
