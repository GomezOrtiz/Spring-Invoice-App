package com.cursoudemy.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cursoudemy.springboot.app.service.ClientService;

@Controller
@RequestMapping(value="/invoices")
public class InvoiceController {
	
	//Views
	private static final String NEW_INVOICE_FORM_VIEW = "invoice/newInvoice";
	
	//Redirections
	
	//Attributes
	private static final String TITLE = "title";
	
	//Messages
	@Autowired
	private MessageSource messages;
	
	//Beans
	@Autowired
	private ClientService clientService;
	
	//Methods
	/**
	 * Método que muestra el formulario para añadir una factura asociada a un cliente
	 */
	@RequestMapping(value="/{clientId}/new", method=RequestMethod.GET)
	public String create(@PathVariable("clientId") Long clientId) {
		
		return NEW_INVOICE_FORM_VIEW;
	}

}
