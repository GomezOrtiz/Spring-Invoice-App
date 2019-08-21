package com.cursoudemy.springboot.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.cursoudemy.springboot.app.model.Client;
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
	private static final String CLIENTS_LIST_VIEW = "clients/list";
	private static final String NEW_CLIENT_FORM_VIEW = "clients/new";
	private static final String EDIT_CLIENT_FORM_VIEW = "clients/edit";
	
	//Redirections
	private static final String REDIRECT_TO_LIST = "redirect:/clients";
	
	//Attributes
	private static final String TITLE = "title";
	private static final String CLIENT = "client";
	private static final String CLIENTS = "clients";
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private static final int MAX_RESULTS_PER_PAGE = 6;
	
	//Messages
	@Value("${clients.list.title}")
	private String listTitle;
	@Value("${clients.add.title}")
	private String addTitle;
	@Value("${clients.edit.title}")
	private String editTitle;
	@Value("${clients.error.not.found}")
	private String notFound;
	@Value("${clients.add.success}")
	private String addSuccess;
	@Value("${clients.edit.success}")
	private String editSuccess;
	@Value("${clients.delete.success}")
	private String deleteSuccess;
	
	//Beans
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientFormValidator clientFormValidator;
	
	//Methods
	/**
	 * Método que muestra el listado de clientes (con paginación)
	 */
	@RequestMapping(value= {"/", "", "/list"}, method=RequestMethod.GET)
	public String listClients(@RequestParam(name="page", defaultValue="0") int page, Model model) {
				
		Page<Client> clients = clientService.getClientsByPage(page, MAX_RESULTS_PER_PAGE);
		Paginator<Client> paginator = new Paginator<>("/clients", clients);
				
		model.addAttribute(TITLE, listTitle);
		model.addAttribute(CLIENTS, clients);
		model.addAttribute("page", paginator);
		
		return CLIENTS_LIST_VIEW;
	}
	
	/**
	 * Método que muestra el formulario para añadir un nuevo cliente
	 */
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String createForm(Map<String, Object> model) {
		
		Client client = new Client();
		
		model.put(TITLE, addTitle);
		model.put(CLIENT, client);
		
		return NEW_CLIENT_FORM_VIEW;
	}
	
	/**
	 * Método que procesa el formulario para añadir un nuevo cliente
	 */
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String create(@ModelAttribute("client") Client client, BindingResult result, Model model, RedirectAttributes redirect) {
		
		clientFormValidator.validate(client, result);

		if (result.hasErrors()) {
			model.addAttribute(TITLE, addTitle);
			return NEW_CLIENT_FORM_VIEW;
		}
		
		clientService.create(client);
		
		redirect.addFlashAttribute(SUCCESS, addSuccess);
		
		return REDIRECT_TO_LIST;
		
	}
	
	/**
	 * Método que muestra el formulario para editar un cliente
	 */
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes redirect) {
				
		if(null != clientService.findById(id)) {
			Client client = clientService.findById(id);
			model.put(CLIENT, client);
			model.put(TITLE, editTitle);
			return EDIT_CLIENT_FORM_VIEW;
		} else {
			redirect.addFlashAttribute(ERROR, notFound);
			return REDIRECT_TO_LIST;
		}
	}
	
	/**
	 * Método que procesa el formulario para editar un cliente
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String update(@ModelAttribute("client") Client client, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status) {
			
		clientFormValidator.validate(client, result);

		if (result.hasErrors()) {	
			model.addAttribute(TITLE, editTitle);
			return EDIT_CLIENT_FORM_VIEW;
		}
		
		clientService.update(client);
		status.setComplete();
		
		redirect.addFlashAttribute(SUCCESS, editSuccess);
		
		return REDIRECT_TO_LIST;
	}
	
	/**
	 * Método que elimina un cliente
	 */
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirect) {
		
		if(null != clientService.findById(id)) {
			clientService.delete(id);
			redirect.addFlashAttribute(SUCCESS, deleteSuccess);
		} else {
			redirect.addFlashAttribute(ERROR, notFound);
		}
		
		return REDIRECT_TO_LIST;
	}
	
}
