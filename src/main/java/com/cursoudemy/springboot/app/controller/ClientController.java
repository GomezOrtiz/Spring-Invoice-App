package com.cursoudemy.springboot.app.controller;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cursoudemy.springboot.app.model.entity.Client;
import com.cursoudemy.springboot.app.service.ClientService;
import com.cursoudemy.springboot.app.utils.forms.ClientFormValidator;
import com.cursoudemy.springboot.app.utils.pagination.Paginator;

/**
 * Controlador de la sección clientes
 *
 * @author David G. Ortiz
 */

@SessionAttributes("client")
@Controller
@RequestMapping("/clients")
public class ClientController {
	
	//Views
	private static final String CLIENTS_LIST_VIEW = "client/clientList";
	private static final String CLIENT_DETAIL_VIEW = "client/clientDetail";
	private static final String NEW_CLIENT_FORM_VIEW = "client/newClient";
	private static final String EDIT_CLIENT_FORM_VIEW = "client/editClient";
	
	//Redirections
	private static final String REDIRECT_TO_LIST = "redirect:/clients";
	
	//Attributes
	private static final String TITLE = "title";
	private static final String CLIENT = "client";
	private static final String CLIENTS = "clients";
//	private static final String INVOICES = "invoices";
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private static final int MAX_RESULTS_PER_PAGE = 6;
	
	//Messages
	@Autowired
	private MessageSource messages;
	
	//Beans
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientFormValidator clientFormValidator;
	
	//Methods
	/**
	 * Método que muestra el listado de clientes (con buscador y paginación)
	 */
	@RequestMapping(value= {"/", "", "/list"}, method=RequestMethod.GET)
	public String list(@RequestParam(name="search", required=false) String term, @RequestParam(name="page", defaultValue="0") int page, Model model, RedirectAttributes redirect, Locale locale) {
		
		Page<Client> clients = null;
		
		if (null != term) {
			clients = clientService.getClientsByNameAndPage(term, page, MAX_RESULTS_PER_PAGE);
		} else {
			clients = clientService.getClientsByPage(page, MAX_RESULTS_PER_PAGE);
		}
		
		Paginator<Client> paginator = new Paginator<>("/clients", clients);
		
		if(!clients.hasContent()) {
			redirect.addFlashAttribute(TITLE, messages.getMessage("client.list.title", null, locale));
			redirect.addFlashAttribute(ERROR, messages.getMessage("client.list.errors.client.not.found", null, locale));
			return REDIRECT_TO_LIST;
		}
						
		model.addAttribute(TITLE, messages.getMessage("client.list.title", null, locale));
		model.addAttribute(CLIENTS, clients);
		model.addAttribute("page", paginator);
		
		return CLIENTS_LIST_VIEW;
	}
	
	/**
	 * Método que muestra el detalle de un cliente
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, @RequestParam(name="page", defaultValue="0") int page, Model model, RedirectAttributes redirect, Locale locale) {
		
		if(null != clientService.findById(id)) {
			Client client = clientService.findById(id);
			
//			Page<Invoice> invoices = invoiceService.getInvoicesByClientAndPage(page, MAX_RESULTS_PER_PAGE);
//			Paginator<Invoice> paginator = new Paginator<>("/clients/{id}", invoices);
			
			model.addAttribute(CLIENT, client);
//			model.addAttribute(INVOICES, invoices);
			model.addAttribute(TITLE, messages.getMessage("client.detail.title", null, locale));
			return CLIENT_DETAIL_VIEW;
		} else {
			redirect.addFlashAttribute(ERROR, messages.getMessage("client.list.errors.not.found", null, locale));
			return REDIRECT_TO_LIST;
		}		
	}
	
	/**
	 * Método que muestra el formulario para añadir un nuevo cliente
	 */
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String create(Map<String, Object> model, Locale locale) {
		
		Client client = new Client();
		
		model.put(TITLE, messages.getMessage("forms.client.add.title", null, locale));
		model.put(CLIENT, client);
		
		return NEW_CLIENT_FORM_VIEW;
	}
	
	/**
	 * Método que procesa el formulario para añadir un nuevo cliente
	 */
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String create(@ModelAttribute("client") Client client, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status, Locale locale) {
		
		clientFormValidator.validate(client, result);

		if (result.hasErrors()) {
			model.addAttribute(TITLE, messages.getMessage("forms.client.add.title", null, locale));
			return NEW_CLIENT_FORM_VIEW;
		}
		
		clientService.create(client);
		status.setComplete();

		redirect.addFlashAttribute(SUCCESS, messages.getMessage("client.list.add.success", null, locale));
		
		return REDIRECT_TO_LIST;
		
	}
	
	/**
	 * Método que muestra el formulario para editar un cliente
	 */
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes redirect, Locale locale) {
				
		if(null != clientService.findById(id)) {
			Client client = clientService.findById(id);
			model.put(CLIENT, client);
			model.put(TITLE, messages.getMessage("forms.client.edit.title", null, locale));
			return EDIT_CLIENT_FORM_VIEW;
		} else {
			redirect.addFlashAttribute(ERROR, messages.getMessage("client.list.errors.not.found", null, locale));
			return REDIRECT_TO_LIST;
		}
	}
	
	/**
	 * Método que procesa el formulario para editar un cliente
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String update(@ModelAttribute("client") Client client, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status, Locale locale) {
			
		clientFormValidator.validate(client, result);

		if (result.hasErrors()) {	
			model.addAttribute(TITLE, messages.getMessage("forms.client.edit.title", null, locale));
			return EDIT_CLIENT_FORM_VIEW;
		}
		
		clientService.update(client);
		status.setComplete();
		
		redirect.addFlashAttribute(SUCCESS, messages.getMessage("client.list.edit.success", null, locale));
		
		return REDIRECT_TO_LIST;
	}
	
	/**
	 * Método que elimina un cliente
	 */
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirect, Locale locale) {
		
		if(null != clientService.findById(id)) {
			clientService.delete(id);
			redirect.addFlashAttribute(SUCCESS, messages.getMessage("client.list.delete.success", null, locale));
		} else {
			redirect.addFlashAttribute(ERROR, messages.getMessage("client.list.errors.not.found", null, locale));
		}
		
		return REDIRECT_TO_LIST;
	}
	
}
