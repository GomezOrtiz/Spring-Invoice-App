package com.cursoudemy.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador de la página de inicio
 *
 * @author David G. Ortiz
 */

@Controller
public class HomeController {
	
	/**
	 * Método que redirige las rutas más comunes del index al listado de clientes
	 */
	@RequestMapping(value= {"", "/", "/index", "/home", "/app"})
	public String home() {
		return "redirect:/clients";
	}

}
