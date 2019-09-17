package com.fakecorp.invoicing.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import com.fakecorp.invoicing.app.model.entity.Client;
import com.fakecorp.invoicing.app.model.entity.Invoice;
import com.fakecorp.invoicing.app.model.entity.Product;
import com.fakecorp.invoicing.app.service.ClientService;
import com.fakecorp.invoicing.app.service.InvoiceService;
import com.fakecorp.invoicing.app.service.ProductService;

/**
 * Controlador de la sección facturas
 *
 * @author David G. Ortiz
 */

@Controller
@RequestMapping(value="/invoices")
@SessionAttributes("invoice")
public class InvoiceController extends BaseController {
	
	//Views
	private static final String NEW_INVOICE_FORM_VIEW = "invoice/newInvoice";
	private static final String NEW_INVOICE_HOME_FORM_VIEW = "home/newInvoice";
	private static final String INVOICE_DETAIL_VIEW = "invoice/invoiceDetail";
	
	//Redirections
	private static final String BASE_REDIRECT_TO_CLIENT = "redirect:/clients/";
	private static final String REDIRECT_NEW_INVOICE_HOME = "redirect:/invoices/new";
	
	//Attributes
	private static final String INVOICE = "invoice";
	private static final String INVOICE_CLIENT_NAME = "client.name";
	private static final String INVOICE_DESCRIPTION = "description";
	
	//Beans
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	//Methods
	/**
	 * Método que muestra el detalle de una factura
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		
		if(isInvoice(id)) {
			addTitle(model, "invoice.detail.title");
			model.addAttribute(INVOICE, invoiceService.findById(id));
			return INVOICE_DETAIL_VIEW;
		} else {
			addErrorMessage(redirect, "invoice.detail.errors.not.found".concat(" " + id));
			return BASE_REDIRECT_TO_CLIENT;
		}		
	}
	
	/**
	 * Método que muestra el formulario para añadir una factura
	 */
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String create(Map<String,Object> model) {
						
			addTitle(model, "forms.invoice.add.title");
			model.put(INVOICE, new Invoice());
			
			return NEW_INVOICE_HOME_FORM_VIEW;
	}
	
	/**
	 * Método que muestra el formulario para añadir una factura asociada a un cliente
	 */
	@RequestMapping(value="/{clientId}/new", method=RequestMethod.GET)
	public String create(@PathVariable("clientId") Long clientId, Map<String,Object> model, RedirectAttributes redirect) {
		
		if(isClient(clientId)) {
			addTitle(model, "forms.invoice.add.title");
			model.put(INVOICE, new Invoice(clientService.findById(clientId)));
			return NEW_INVOICE_FORM_VIEW;
		} else {
			addErrorMessage(redirect, "client.detail.errors.not.found");
			return BASE_REDIRECT_TO_CLIENT;
		}
	}
	
	/**
	 * Método que procesa el formulario para añadir una factura
	 */
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String create(@ModelAttribute("invoice") Invoice invoice, @RequestParam(name="itemId[]", required=false) Long[] itemId, @RequestParam(name="amount[]", required=false) Integer[] amount, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status) {
				
		if(StringUtils.isEmptyOrWhitespace((String) result.getFieldValue(INVOICE_CLIENT_NAME))) {
			addErrorMessage(redirect, "forms.invoice.add.errors.name.empty");
			return REDIRECT_NEW_INVOICE_HOME;
		}
		
		if(StringUtils.isEmptyOrWhitespace((String) result.getFieldValue(INVOICE_DESCRIPTION))) {
			addErrorMessage(redirect, "forms.invoice.add.errors.description.empty");
			return REDIRECT_NEW_INVOICE_HOME;
		}

		if (null == itemId || itemId.length == 0) {
			addErrorMessage(redirect, "client.detail.add.invoice.empty");
			return REDIRECT_NEW_INVOICE_HOME;
		}
		
		invoiceService.createWithItems(invoice, itemId, amount);
				
		status.setComplete();
		
		addSuccessMessage(redirect, "client.detail.add.invoice.success");
		
		return BASE_REDIRECT_TO_CLIENT + invoice.getClient().getId();
	}
	
	/**
	 * Método para eliminar una factura
	 */
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirect) {
		
		Invoice invoice = invoiceService.findById(id);
		Client client = invoice.getClient();
		
		if (isInvoice(id) && isClient(client.getId())) {
			invoiceService.delete(id);
			addSuccessMessage(redirect, "invoice.delete.success");
			return BASE_REDIRECT_TO_CLIENT + client.getId();
		} else {
			addErrorMessage(redirect, "invoice.delete.error.not.found");
			return BASE_REDIRECT_TO_CLIENT;
		}
				
	}
	
	/**
	 * Devuelve en formato JSON cualquier producto no descatalogado de la BBDD que incluya el término de búsqueda
	 */
	@RequestMapping(value="/load-products/{term}", produces={"application/json"}, method=RequestMethod.GET)
	public @ResponseBody List<Product> loadProducts(@PathVariable("term") String term) {
		return productService.findByName(term);
	}
	
	/**
	 * Devuelve en formato JSON cualquier cliente de la BBDD que incluya el término de búsqueda
	 */
	@RequestMapping(value="/load-clients/{term}", produces={"application/json"}, method=RequestMethod.GET)
	public @ResponseBody List<Client> loadClients(@PathVariable("term") String term) {	
		
		List<Client> clients = clientService.findByName(term);
		
		for (Client client : clients) {
			client.setInvoices(new ArrayList<Invoice>());
		}
		
		return clients;
	}
	
	private boolean isClient(Long id) {
		return null != clientService.findById(id);
	}
	
	private boolean isInvoice(Long id) {
		return null != invoiceService.findById(id);
	}
}
