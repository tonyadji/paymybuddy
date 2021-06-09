/**
 * 
 */
package com.paymybuddy.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.paymybuddy.controllers.interfaces.ActiveMenuController;
import com.paymybuddy.entities.UserContact;
import com.paymybuddy.exceptions.ContactAlreadyExistsException;
import com.paymybuddy.exceptions.UserNotFoundException;
import com.paymybuddy.services.UserContactService;
import com.paymybuddy.ui.form.ContactForm;
import com.paymybuddy.utils.ModelUtils;
import com.paymybuddy.utils.SecurityUtils;
import com.paymybuddy.utils.ViewUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tonys
 *
 */
@Controller
@Slf4j
public class UserContactPageController extends AbstractController implements ActiveMenuController{
	
	private final UserContactService userContactService;
	
	public UserContactPageController(UserContactService userContactService) {
		this.userContactService = userContactService;
	}
	
	@GetMapping("/contact")
	public ModelAndView getUserContactPage() {
		return super.getRequest();
	}

	@PostMapping("/contact")
	public ModelAndView handleRegistration(@Valid @ModelAttribute(ModelUtils.MODEL_CONTACT_FORM) ContactForm form,
			BindingResult bindingResult) {
		
		log.debug("[POST] /contact");
		
		if(bindingResult.hasErrors()) {
			return super.getRequest();
		}
		
		try {
			userContactService.createUserContact(SecurityUtils.getAuthUserName(), form.getUsername());
		}catch (UserNotFoundException | ContactAlreadyExistsException e) {
			bindingResult.rejectValue("username", "", e.getMessage());
			return super.getRequest();
		}
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/contact");
		return new ModelAndView(redirectView);
	}
	
	@Override
	public String getView() {
		return ViewUtils.VIEW_USER_CONTACT_PAGE;
	}

	@Override
	public String getTitle() {
		return "page_contact.title";
	}

	@ModelAttribute(ModelUtils.MODEL_ACTIVE_MENU)
	@Override
	public String getActiveMenu() {
		return "contact";
	}

	@ModelAttribute(ModelUtils.MODEL_CONTACT_FORM)
	public ContactForm contactForm() {
		return new ContactForm();
	}
	
	@ModelAttribute(ModelUtils.MODEL_MY_CONTACTS)
	public List<UserContact> myContacts() {
		return userContactService.findByOwner(SecurityUtils.getAuthUserName());
	}
}
