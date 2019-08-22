package com.cursoudemy.springboot.app.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cursoudemy.springboot.app.model.entity.Client;
import com.cursoudemy.springboot.app.model.entity.Invoice;
import com.cursoudemy.springboot.app.model.entity.Product;
import com.cursoudemy.springboot.app.service.ClientService;
import com.cursoudemy.springboot.app.service.ProductService;

@Controller
@RequestMapping(value="/invoices")
@SessionAttributes("invoice")
public class InvoiceController {
	
	//Views
	private static final String NEW_INVOICE_FORM_VIEW = "invoice/newInvoice";
	
	//Redirections
	private static final String BASE_REDIRECT_TO_CLIENT = "redirect:/clients/";
	
	//Attributes
	private static final String TITLE = "title";
	private static final String INVOICE = "invoice";
	private static final String INVOICE_DESCRIPTION = "description";
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	
	//Messages
	@Autowired
	private MessageSource messages;
	
	//Beans
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ProductService productService;
	
	//Methods
	/**
	 * Método que muestra el formulario para añadir una factura asociada a un cliente
	 */
	@RequestMapping(value="/{clientId}/new", method=RequestMethod.GET)
	public String create(@PathVariable("clientId") Long clientId, Map<String,Object> model, RedirectAttributes redirect, Locale locale) {
		
		Client client = clientService.findById(clientId);
		
		if (null == client) {
			redirect.addFlashAttribute(ERROR, "client.detail.errors.not.found");
			return BASE_REDIRECT_TO_CLIENT;
			
		} else {
			
			Invoice invoice = new Invoice();
			invoice.setClient(client);

			model.put(TITLE, messages.getMessage("forms.invoice.add.title", null, locale));
			model.put(INVOICE, invoice);
			
			return NEW_INVOICE_FORM_VIEW;
		}
	}
	
	/**
	 * Método que procesa el formulario para añadir una factura asociada a un cliente
	 */
	@RequestMapping(value="/{clientId}/new", method=RequestMethod.POST)
	public String create(@ModelAttribute("invoice") Invoice invoice, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status, Locale locale) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(result, INVOICE_DESCRIPTION, "forms.invoice.errors.description.empty");	

		if (result.hasErrors()) {
			model.addAttribute(TITLE, messages.getMessage("forms.invoice.add.title", null, locale));
			return NEW_INVOICE_FORM_VIEW;
		}
		
	// TODO: AQUÍ SE CREA LA FACTURA, PERO TAMBIÉN SE TENDRÁN QUE CREAR LOS ITEMS Y TENDREMOS QUE METERLE LA FACTURA AL CLIENTE
		
		status.setComplete();

		redirect.addFlashAttribute(SUCCESS, messages.getMessage("client.detail.add.invoice.success", null, locale));
		
		return BASE_REDIRECT_TO_CLIENT + invoice.getClient().getId();
	}
	
	/**
	 * Devuelve en formato JSON un producto de la BBDD que coincida con la búsqueda
	 */
	@RequestMapping(value="/load-products/{query}", produces={"application/json"}, method=RequestMethod.GET)
	public @ResponseBody List<Product> loadProducts(@PathVariable("query") String query) {
		return productService.findByName(query);
	}
}
