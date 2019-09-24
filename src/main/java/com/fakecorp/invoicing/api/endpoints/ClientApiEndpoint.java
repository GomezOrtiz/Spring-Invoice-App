package com.fakecorp.invoicing.api.endpoints;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fakecorp.invoicing.app.controller.BaseController;
import com.fakecorp.invoicing.app.model.entity.Client;
import com.fakecorp.invoicing.app.service.ClientService;
import com.fakecorp.invoicing.app.utils.forms.ClientFormValidator;

/**
 * Endpoints de clientes de la API
 *
 * @author David G. Ortiz
 */

@RestController
@RequestMapping("/api/clients")
public class ClientApiEndpoint extends BaseController {
		
	//Beans
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientFormValidator clientFormValidator;
	
	//Endpoints
	/**
	 * Endpoint que devuelve el listado de clientes
	 */
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public List<Client> list(@RequestParam(name="search", required=false) String term, HttpServletResponse response) {
		
		List<Client> clients = null;
		
		try {
			clients = clientService.findAll();
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
		if(null == clients || clients.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}
		
		return clients;
	}
	
	/**
	 * Endpoint que devuelve un cliente por su ID
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Client detail(@PathVariable("id") Long id, HttpServletResponse response) {
		
		Client client = null;
		
		if(!isClient(id)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} 			
		
		try {
			client = clientService.findById(id);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
		return client;
	}
	
	/**
	 * Endpoint para añadir un nuevo cliente
	 */
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String create(@RequestBody Client client, HttpServletResponse response) {
				
		if(null == client) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return "No se ha enviado ningún cliente";
		}
		
		try {
			clientService.create(client);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
				
		return "El cliente se ha creado con éxito";
	}
//	
//	/**
//	 * Método que muestra el formulario para editar un cliente
//	 */
//	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
//	public String update(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes redirect) {
//				
//		if(isClient(id)) {
//			model.put(CLIENT, clientService.findById(id));
//			addTitle(model, "forms.client.edit.title");
//			return EDIT_CLIENT_FORM_VIEW;
//		} else {
//			addErrorMessage(redirect, "client.list.errors.not.found");
//			return REDIRECT_TO_LIST;
//		}
//	}
//	
//	/**
//	 * Método que procesa el formulario para editar un cliente
//	 */
//	@RequestMapping(value="/edit", method=RequestMethod.POST)
//	public String update(@ModelAttribute("client") Client client, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status) {
//			
//		clientFormValidator.validate(client, result);
//
//		if (result.hasErrors()) {	
//			addTitle(model, "forms.client.edit.title");
//			return EDIT_CLIENT_FORM_VIEW;
//		}
//		
//		clientService.update(client);
//		status.setComplete();
//		
//		addSuccessMessage(redirect, "client.list.edit.success", client.getFullName());
//		
//		return REDIRECT_TO_LIST;
//	}
//	
	/**
	 * Endpoint que elimina un cliente
	 */
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, HttpServletResponse response) {
		
		if(!isClient(id)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return "No existe ningún cliente con esa ID";
		} else {
			try {
				clientService.delete(id);
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return "No hemos podido eliminar el cliente";
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return "El cliente se ha eliminado correctamente";
		}

	}
	
	private boolean isClient(Long id) {
		return null != clientService.findById(id);
	}
}
