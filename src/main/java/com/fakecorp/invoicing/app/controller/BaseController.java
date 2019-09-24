package com.fakecorp.invoicing.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fakecorp.invoicing.app.model.entity.User;
import com.fakecorp.invoicing.app.utils.auth.AuthUtils;

/**
 * Controlador abstracto del que heredan todos los demás controladores
 *
 * @author David G. Ortiz
 */

@Controller
public class BaseController {
	
	//Attributes
	private static final String TITLE = "title";
	private static final String ERROR = "error";
	private static final String SUCCESS = "success";
	private static final String WARNING = "warning";

	//Messages
	@Autowired
	protected MessageSource messages;
	
	//Beans
	@Autowired
	private AuthUtils authUtils;
	
	//Base Attributes
	@ModelAttribute("loggedInUser")
	public User getAuthenticatedUser() {
		return authUtils.getLoggedInUser();
	}
	
	//Base Methods
	protected void addMessage(Model model, String attributeName, String messageProperty, Object... args) {
		model.addAttribute(attributeName, messages.getMessage(messageProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addMessage(Map<String,Object> model, String attributeName, String messageProperty, Object... args) {
		model.put(attributeName, messages.getMessage(messageProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addMessage(RedirectAttributes redirect, String attributeName, String messageProperty, Object... args) {
		redirect.addFlashAttribute(attributeName, messages.getMessage(messageProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addTitle(Model model, String titleProperty, Object... args) {
		model.addAttribute(TITLE, messages.getMessage(titleProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addTitle(Map<String,Object> model, String titleProperty, Object... args) {
		model.put(TITLE, messages.getMessage(titleProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addTitle(RedirectAttributes redirect, String titleProperty, Object... args) {
		redirect.addFlashAttribute(TITLE, messages.getMessage(titleProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addErrorMessage(Model model, String errorProperty, Object... args) {
		model.addAttribute(ERROR, messages.getMessage(errorProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addErrorMessage(Map<String,Object> model, String errorProperty, Object... args) {
		model.put(ERROR, messages.getMessage(errorProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addErrorMessage(RedirectAttributes redirect, String errorProperty, Object... args) {
		redirect.addFlashAttribute(ERROR, messages.getMessage(errorProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addSuccessMessage(Model model, String errorProperty, Object... args) {
		model.addAttribute(SUCCESS, messages.getMessage(errorProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addSuccessMessage(Map<String,Object> model, String errorProperty, Object... args) {
		model.put(SUCCESS, messages.getMessage(errorProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addSuccessMessage(RedirectAttributes redirect, String errorProperty, Object... args) {
		redirect.addFlashAttribute(SUCCESS, messages.getMessage(errorProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addWarningMessage(Model model, String warningProperty, Object... args) {
		model.addAttribute(WARNING, messages.getMessage(warningProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addWarningMessage(Map<String,Object> model, String warningProperty, Object... args) {
		model.put(WARNING, messages.getMessage(warningProperty, args, null, LocaleContextHolder.getLocale()));
	}
	
	protected void addWarningMessage(RedirectAttributes redirect, String warningProperty, Object... args) {
		redirect.addFlashAttribute(WARNING, messages.getMessage(warningProperty, args, null, LocaleContextHolder.getLocale()));
	}
}
