package com.fakecorp.invoicing.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LocaleController {
	
	@RequestMapping(value="/locale", method=RequestMethod.GET)
	public String redirectLocale(HttpServletRequest request) {
		
		String lastUrl = request.getHeader("referer");
		
		return "redirect:".concat(lastUrl);
	}
}
