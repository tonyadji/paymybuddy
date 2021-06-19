/**
 * 
 */
package com.paymybuddy.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.paymybuddy.entities.PMBUser;
import com.paymybuddy.exceptions.UserAlreadyExistsException;
import com.paymybuddy.services.PMBUserService;
import com.paymybuddy.ui.form.RegisterForm;
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
public class RegisterController extends AbstractController {
	
	private final PMBUserService userService;
	
	public RegisterController(PMBUserService userService) {
		this.userService = userService;
	}

	@GetMapping("/register")
	public ModelAndView getRegisterPage() {
		
		log.debug("[GET] /register");
		if(SecurityUtils.isAuthenticated()) {
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("/home");
			return new ModelAndView(redirectView);
		}
		return super.getRequest();
	}

	@PostMapping("/register")
	public ModelAndView handleRegistration(@Valid @ModelAttribute(ModelUtils.MODEL_REGISTER_FORM) RegisterForm form,
			BindingResult bindingResult) {
		
		log.debug("[POST] /register");
		
		if(bindingResult.hasErrors()) {
			return super.getRequest();
		}
		
		try {
			final PMBUser user = userService.createUser(form);
			SecurityUtils.updateSecurityContext(user);
		}catch (UserAlreadyExistsException e) {
			bindingResult.rejectValue("username", "", e.getMessage());
			return super.getRequest();
		}
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/home");
		return new ModelAndView(redirectView);
	}

	@Override
	public String getView() {
		return ViewUtils.VIEW_REGISTER;
	}

	@Override
	public String getTitle() {
		return "Login";
	}

	@ModelAttribute(ModelUtils.MODEL_REGISTER_FORM)
	public RegisterForm registerForm() {
		return new RegisterForm();
	}
}
