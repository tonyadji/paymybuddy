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

import com.paymybuddy.controllers.interfaces.ActiveMenuController;
import com.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.services.AccountService;
import com.paymybuddy.services.ProfileService;
import com.paymybuddy.ui.ProfilUI;
import com.paymybuddy.ui.form.AccountDepositForm;
import com.paymybuddy.utils.ModelUtils;
import com.paymybuddy.utils.ViewUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tonys
 *
 */
@Controller
@Slf4j
public class UserProfilePageController extends AbstractController implements ActiveMenuController{
	
	private final ProfileService profilService;
	
	private final AccountService accountService;
	
	public UserProfilePageController(ProfileService profilService, AccountService accountService) {
		this.profilService = profilService;
		this.accountService = accountService;
	}
	
	@GetMapping("/profile")
	public ModelAndView getUserProfilePage() {
		log.debug("[GET] /profile");
		return super.getRequest();
	}

	@PostMapping("/profile/account-deposit")
	public ModelAndView handleDeposit(@Valid @ModelAttribute(ModelUtils.MODEL_ACCOUNT_DEPOSIT_FORM) AccountDepositForm form,
			BindingResult bindingResult) {
		
		log.debug("[POST] /profile");
		
		if(bindingResult.hasErrors()) {
			return super.getRequest();
		}
		try {
			accountService.deposit(form.getAmount());
		}catch (AccountNotFoundException | IllegalArgumentException e) {
			bindingResult.rejectValue("amount", "", e.getMessage());
			return super.getRequest();
		}		
		
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/profile");
		return new ModelAndView(redirectView);
	}
	
	@Override
	public String getView() {
		return ViewUtils.VIEW_USER_PROFILE_PAGE;
	}

	@Override
	public String getTitle() {
		return "page_profile.title";
	}

	@ModelAttribute(ModelUtils.MODEL_ACTIVE_MENU)
	@Override
	public String getActiveMenu() {
		return "profile";
	}

	@ModelAttribute(ModelUtils.MODEL_ACCOUNT_DEPOSIT_FORM)
	public AccountDepositForm accountDepositForm() {
		return new AccountDepositForm();
	}
	
	@ModelAttribute(ModelUtils.MODEL_MY_PROFILE)
	public ProfilUI myProfile() {
		return profilService.getProfileInformations();
	}
	
}
