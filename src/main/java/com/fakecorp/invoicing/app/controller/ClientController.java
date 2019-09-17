package com.fakecorp.invoicing.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fakecorp.invoicing.app.model.entity.Client;
import com.fakecorp.invoicing.app.service.ClientService;
import com.fakecorp.invoicing.app.utils.forms.ClientFormValidator;
import com.fakecorp.invoicing.app.utils.pagination.Paginator;

/**
 * Controlador de la sección clientes
 *
 * @author David G. Ortiz
 */

@SessionAttributes("client")
@Controller
@RequestMapping("/clients")
public class ClientController extends BaseController {
	
	//Views
	private static final String CLIENTS_LIST_VIEW = "client/clientList";
	private static final String CLIENT_DETAIL_VIEW = "client/clientDetail";
	private static final String NEW_CLIENT_FORM_VIEW = "client/newClient";
	private static final String EDIT_CLIENT_FORM_VIEW = "client/editClient";
	
	//Redirections
	private static final String REDIRECT_TO_LIST = "redirect:/clients";
	
	//Attributes
	private static final String CLIENT = "client";
	private static final String CLIENTS = "clients";
	private static final int MAX_RESULTS_PER_PAGE = 6;
	
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
	public String list(@RequestParam(name="search", required=false) String term, @RequestParam(name="page", defaultValue="0") int page, Model model, RedirectAttributes redirect) {
		
		Page<Client> clients = null;
		
		if (null != term) {
			clients = clientService.getClientsByNameAndPage(term, page, MAX_RESULTS_PER_PAGE);
		} else {
			clients = clientService.getClientsByPage(page, MAX_RESULTS_PER_PAGE);
		}
		
		Paginator<Client> paginator = new Paginator<>("/clients", clients);
		
		if(!clients.hasContent()) {
			addTitle(redirect, "client.list.title");
			addErrorMessage(redirect, "client.list.errors.client.not.found");
			return REDIRECT_TO_LIST;
		}
		
		addTitle(model, "client.list.title");
		model.addAttribute(CLIENTS, clients);
		model.addAttribute("page", paginator);
		
		return CLIENTS_LIST_VIEW;
	}
	
	/**
	 * Método que muestra el detalle de un cliente
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, @RequestParam(name="page", defaultValue="0") int page, Model model, RedirectAttributes redirect) {
		
		if(isClient(id)) {
			model.addAttribute(CLIENT, clientService.findById(id));
			addTitle(model, "client.detail.title");
			return CLIENT_DETAIL_VIEW;
		} else {
			addErrorMessage(redirect, "client.list.errors.not.found");
			return REDIRECT_TO_LIST;
		}		
	}
	
	/**
	 * Método que muestra el formulario para añadir un nuevo cliente
	 */
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String create(Map<String, Object> model) {
		
		Client client = new Client();
		
		addTitle(model, "forms.client.add.title");
		model.put(CLIENT, client);
		
		return NEW_CLIENT_FORM_VIEW;
	}
	
	/**
	 * Método que procesa el formulario para añadir un nuevo cliente
	 */
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String create(@ModelAttribute("client") Client client, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status) {
		
		clientFormValidator.validate(client, result);
		
		if (result.hasErrors()) {
			addTitle(model, "forms.client.add.title");
			return NEW_CLIENT_FORM_VIEW;
		}
		
		clientService.create(client);
		status.setComplete();
		
		addSuccessMessage(redirect, "client.list.add.success");
		
		return REDIRECT_TO_LIST;
		
	}
	
	/**
	 * Método que muestra el formulario para editar un cliente
	 */
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes redirect) {
				
		if(isClient(id)) {
			model.put(CLIENT, clientService.findById(id));
			addTitle(model, "forms.client.edit.title");
			return EDIT_CLIENT_FORM_VIEW;
		} else {
			addErrorMessage(redirect, "client.list.errors.not.found");
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
			addTitle(model, "forms.client.edit.title");
			return EDIT_CLIENT_FORM_VIEW;
		}
		
		clientService.update(client);
		status.setComplete();
		
		addSuccessMessage(redirect, "client.list.edit.success");
		
		return REDIRECT_TO_LIST;
	}
	
	/**
	 * Método que elimina un cliente
	 */
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirect) {
		
		if(isClient(id)) {
			clientService.delete(id);
			addSuccessMessage(redirect, "client.list.delete.success");
		} else {
			addErrorMessage(redirect, "client.list.errors.not.found");
		}
		
		return REDIRECT_TO_LIST;
	}
	
	private boolean isClient(Long id) {
		return null != clientService.findById(id);
	}
}
