package com.fakecorp.invoicing.app.controller;

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

import com.fakecorp.invoicing.app.model.entity.Product;
import com.fakecorp.invoicing.app.service.ProductService;
import com.fakecorp.invoicing.app.utils.forms.ProductFormValidator;
import com.fakecorp.invoicing.app.utils.pagination.Paginator;

/**
 * Controlador de la sección productos
 *
 * @author David G. Ortiz
 */

@SessionAttributes("product")
@Controller
@RequestMapping("/products")
public class ProductController extends AbstractController {
		
	//Views
	private static final String PRODUCTS_LIST_VIEW = "product/productList";
	private static final String NEW_PRODUCT_FORM_VIEW = "product/newProduct";
	private static final String EDIT_PRODUCT_FORM_VIEW = "product/editProduct";
	
	//Redirections
	private static final String REDIRECT_TO_LIST = "redirect:/products";
	
	//Attributes
	private static final String TITLE = "title";
	private static final String PRODUCT = "product";
	private static final String PRODUCTS = "products";
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private static final int MAX_RESULTS_PER_PAGE = 6;
	
	//Messages
	@Autowired
	private MessageSource messages;
	
	//Beans
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductFormValidator productFormValidator;
	
	//Methods
	/**
	 * Método que muestra el listado de clientes (con buscador y paginación)
	 */
	@RequestMapping(value= {"/", "", "/list"}, method=RequestMethod.GET)
	public String list(@RequestParam(name="search", required=false) String term, @RequestParam(name="page", defaultValue="0") int page, Model model, RedirectAttributes redirect, Locale locale) {
		
		Page<Product> products = null;
		
		if (null != term) {
			products = productService.getProductsByNameAndPage(term, page, MAX_RESULTS_PER_PAGE);
		} else {
			products = productService.getProductsByPage(page, MAX_RESULTS_PER_PAGE);		
		}
		
		Paginator<Product> paginator = new Paginator<>("/products", products);
		
		if(!products.hasContent()) {
			redirect.addFlashAttribute(ERROR, messages.getMessage("product.list.errors.product.not.found", null, locale));
			return REDIRECT_TO_LIST;
		}
				
		model.addAttribute(TITLE, messages.getMessage("product.list.title", null, locale));
		model.addAttribute(PRODUCTS, products);
		model.addAttribute("page", paginator);
		
		return PRODUCTS_LIST_VIEW;
	}
	
	/**
	 * Método que muestra el formulario para añadir un nuevo producto
	 */
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String create(Map<String, Object> model, Locale locale) {
		
		Product product = new Product();
		
		model.put(TITLE, messages.getMessage("forms.product.add.title", null, locale));
		model.put(PRODUCT, product);
		
		return NEW_PRODUCT_FORM_VIEW;
	}
	
	/**
	 * Método que procesa el formulario para añadir un nuevo producto
	 */
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String create(@ModelAttribute("product") Product product, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status, Locale locale) {
		
		productFormValidator.validate(product, result);

		if (result.hasErrors()) {
			model.addAttribute(TITLE, messages.getMessage("forms.product.add.title", null, locale));
			return NEW_PRODUCT_FORM_VIEW;
		}
		
		productService.create(product);
		status.setComplete();

		redirect.addFlashAttribute(SUCCESS, messages.getMessage("product.list.add.success", null, locale));
		
		return REDIRECT_TO_LIST;
		
	}
	
	/**
	 * Método que muestra el formulario para editar un producto
	 */
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes redirect, Locale locale) {
				
		if(null != productService.findById(id)) {
			Product product = productService.findById(id);
			model.put(PRODUCT, product);
			model.put(TITLE, messages.getMessage("forms.product.edit.title", null, locale));
			return EDIT_PRODUCT_FORM_VIEW;
		} else {
			redirect.addFlashAttribute(ERROR, messages.getMessage("product.list.errors.not.found", null, locale));
			return REDIRECT_TO_LIST;
		}
	}
	
	/**
	 * Método que procesa el formulario para editar un producto
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String update(@ModelAttribute("product") Product product, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status, Locale locale) {
			
		productFormValidator.validate(product, result);

		if (result.hasErrors()) {	
			model.addAttribute(TITLE, messages.getMessage("forms.product.edit.title", null, locale));
			return EDIT_PRODUCT_FORM_VIEW;
		}
		
		productService.update(product);
		status.setComplete();
		
		redirect.addFlashAttribute(SUCCESS, messages.getMessage("product.list.edit.success", null, locale));
		
		return REDIRECT_TO_LIST;
	}
	
	/**
	 * Método que cambia el estado de discontinuidad de un producto
	 */
	@RequestMapping(value="/discontinue/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirect, Locale locale) {
		
		try {		
			productService.changeDiscontinued(id);
			redirect.addFlashAttribute(SUCCESS, messages.getMessage("product.list.delete.success", null, locale));
		} catch(Exception e) {
			redirect.addFlashAttribute(ERROR, messages.getMessage("product.list.errors.not.found", null, locale));
		}
		
		return REDIRECT_TO_LIST;
	}
	
}
