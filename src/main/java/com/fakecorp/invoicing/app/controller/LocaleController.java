package com.fakecorp.invoicing.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controlador para redirigir a la página anterior tras cambiar de idioma
 *
 * @author David G. Ortiz
 */

@Controller
public class LocaleController {
	
	@RequestMapping(value="/locale", method=RequestMethod.GET)
	public String redirectLocale(HttpServletRequest request) {
		
		String lastUrl = request.getHeader("referer");
		
		return "redirect:".concat(lastUrl);
	}
}
