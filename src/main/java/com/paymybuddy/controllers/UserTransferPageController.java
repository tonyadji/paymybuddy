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
import com.paymybuddy.exceptions.InsufficientBalanceException;
import com.paymybuddy.services.AccountService;
import com.paymybuddy.services.ProfileService;
import com.paymybuddy.ui.ProfilUI;
import com.paymybuddy.ui.form.AccountTransferForm;
import com.paymybuddy.utils.ModelUtils;
import com.paymybuddy.utils.ViewUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tonys
 *
 */
@Controller
@Slf4j
public class UserTransferPageController extends AbstractController implements ActiveMenuController{
	
	private final ProfileService profilService;
	
	private final AccountService accountService;
	
	public UserTransferPageController(ProfileService profilService, AccountService accountService) {
		this.profilService = profilService;
		this.accountService = accountService;
	}
	
	@GetMapping("/transfer")
	public ModelAndView getUserProfilePage() {
		log.debug("[GET] /transfer");
		return super.getRequest();
	}

	@PostMapping("/transfer")
	public ModelAndView handleRegistration(@Valid @ModelAttribute(ModelUtils.MODEL_ACCOUNT_TRANSFER_FORM) AccountTransferForm form,
			BindingResult bindingResult) {
		
		log.debug("[POST] /transfer");
		
		if(bindingResult.hasErrors()) {
			return super.getRequest();
		}
		try {
			accountService.transfer(form.getReceiverUsername(),form.getAmount());
		}catch (AccountNotFoundException e) {
			bindingResult.rejectValue("receiverUsername", "", e.getMessage());
			return super.getRequest();
		}catch (InsufficientBalanceException | IllegalArgumentException e) {
			bindingResult.rejectValue("amount", "", e.getMessage());
			return super.getRequest();
		}		
		
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/transfer");
		return new ModelAndView(redirectView);
	}
	
	@Override
	public String getView() {
		return ViewUtils.VIEW_USER_TRANSFER_PAGE;
	}

	@Override
	public String getTitle() {
		return "page_transfer.title";
	}

	@ModelAttribute(ModelUtils.MODEL_ACTIVE_MENU)
	@Override
	public String getActiveMenu() {
		return "transfer";
	}

	@ModelAttribute(ModelUtils.MODEL_ACCOUNT_TRANSFER_FORM)
	public AccountTransferForm accountTransferForm() {
		return new AccountTransferForm();
	}
	
	@ModelAttribute(ModelUtils.MODEL_MY_PROFILE)
	public ProfilUI myProfile() {
		return profilService.getProfileInformations();
	}
	
}
