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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cursoudemy.springboot.app.model.entity.Client;
import com.cursoudemy.springboot.app.model.entity.Invoice;
import com.cursoudemy.springboot.app.model.entity.InvoiceItem;
import com.cursoudemy.springboot.app.model.entity.Product;
import com.cursoudemy.springboot.app.service.ClientService;
import com.cursoudemy.springboot.app.service.InvoiceService;
import com.cursoudemy.springboot.app.service.ProductService;

@Controller
@RequestMapping(value="/invoices")
@SessionAttributes("invoice")
public class InvoiceController {
	
	//Views
	private static final String NEW_INVOICE_FORM_VIEW = "invoice/newInvoice";
	private static final String INVOICE_DETAIL_VIEW = "invoice/invoiceDetail";
	
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
	
	@Autowired
	private InvoiceService invoiceService;
	
	//Methods
	/**
	 * Método que muestra el detalle de una factura
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model, RedirectAttributes redirect, Locale locale) {
		
		Invoice invoice = invoiceService.findById(id);

		if(null == invoice) {
			model.addAttribute(TITLE, messages.getMessage("client.list.title", null, locale));
			redirect.addFlashAttribute(ERROR, messages.getMessage("invoice.detail.errors.not.found", null, locale).concat(" " + id));
			return BASE_REDIRECT_TO_CLIENT;
		} 
		
		model.addAttribute(TITLE, messages.getMessage("invoice.detail.title", null, locale));
		model.addAttribute(INVOICE, invoice);
		return INVOICE_DETAIL_VIEW;
	}
	
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
	public String create(@ModelAttribute("invoice") Invoice invoice, @RequestParam(name="itemId[]", required=false) Long[] itemId, @RequestParam(name="amount[]", required=false) Integer[] amount, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status, Locale locale) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(result, INVOICE_DESCRIPTION, "forms.invoice.errors.description.empty");	

		if (result.hasErrors()) {
			model.addAttribute(TITLE, messages.getMessage("forms.invoice.add.title", null, locale));
			return NEW_INVOICE_FORM_VIEW;
		}
		
		if (null == itemId || itemId.length == 0) {
			model.addAttribute(TITLE, messages.getMessage("forms.invoice.add.title", null, locale));
			model.addAttribute(ERROR, messages.getMessage("client.detail.add.invoice.empty", null, locale));
			return NEW_INVOICE_FORM_VIEW;
		}
		
		for (int i = 0; i < itemId.length; i++) {
			Product product = productService.findById(itemId[i]);
			InvoiceItem item = new InvoiceItem();
			item.setProduct(product);
			item.setAmount(amount[i]);
			invoice.addItem(item);
		}
		
		invoiceService.create(invoice);
				
		status.setComplete();

		redirect.addFlashAttribute(SUCCESS, messages.getMessage("client.detail.add.invoice.success", null, locale));
		
		return BASE_REDIRECT_TO_CLIENT + invoice.getClient().getId();
	}
	
	/**
	 * Devuelve en formato JSON un producto de la BBDD que coincida con la búsqueda
	 */
	@RequestMapping(value="/load-products/{term}", produces={"application/json"}, method=RequestMethod.GET)
	public @ResponseBody List<Product> loadProducts(@PathVariable("term") String term) {
		return productService.findByName(term);
	}
}
