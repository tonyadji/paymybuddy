/**
 * 
 */
package com.paymybuddy.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.paymybuddy.controllers.interfaces.ActiveMenuController;
import com.paymybuddy.entities.Transaction;
import com.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.exceptions.InsufficientBalanceException;
import com.paymybuddy.services.AccountService;
import com.paymybuddy.services.ProfileService;
import com.paymybuddy.services.TransactionService;
import com.paymybuddy.ui.ProfilUI;
import com.paymybuddy.ui.form.TransferToAccountForm;
import com.paymybuddy.ui.form.TransferToBankAccountForm;
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
	
	private final TransactionService transactionService;
	
	public UserTransferPageController(ProfileService profilService, AccountService accountService, TransactionService transactionService) {
		this.profilService = profilService;
		this.accountService = accountService;
		this.transactionService = transactionService;
	}
	
	@GetMapping("/my/transfer")
	public ModelAndView getUserTransferPage(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "5")int size) {
		log.debug("[GET] /my/transfer");
		final ModelAndView mav = super.getRequest();
		if(page <= 0) {
			page = 1;
		}
		page --;
		
		if(size <= 0) {
			size = 10;
		}
		final Page<Transaction> paginatedTransactions = getMyPaginatedTransactions(page, size);
		mav.addObject(ModelUtils.MODEL_MY_PAGINATED_TRANSACTIONS, paginatedTransactions);
		int totalPages = paginatedTransactions.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            mav.addObject("pageNumbers", pageNumbers);
        }
        mav.addObject("currentPage", page+1);
		return mav;
	}

	@PostMapping("/my/transfer/to-contact")
	public ModelAndView handleTranferToContact(@Valid @ModelAttribute(ModelUtils.MODEL_ACCOUNT_TRANSFER_FORM) TransferToAccountForm form,
			BindingResult bindingResult) {
		
		log.debug("[POST] /my/transfer/to-contact");
		
		if(bindingResult.hasErrors()) {
			return getUserTransferPage(0,10);
		}
		try {
			accountService.transfer(form.getReceiverUsername(),form.getAmount());
		}catch (AccountNotFoundException e) {
			bindingResult.rejectValue("receiverUsername", "", e.getMessage());
			return getUserTransferPage(0,10);
		}catch (InsufficientBalanceException | IllegalArgumentException e) {
			bindingResult.rejectValue("amount", "", e.getMessage());
			return getUserTransferPage(0,10);
		}		
		
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/my/transfer");
		return new ModelAndView(redirectView);
	}
	
	@PostMapping("/my/transfer/to-bank-account")
	public ModelAndView handleTransferToBankAccount(@Valid @ModelAttribute(ModelUtils.MODEL_BANK_ACCOUNT_TRANSFER_FORM) TransferToBankAccountForm form,
			BindingResult bindingResult) {
		
		log.debug("[POST] /my/transfer/to-bank-account");
		
		if(bindingResult.hasErrors()) {
			return getUserTransferPage(0,10);
		}
		
		try {
			accountService.bankTransfer(form.getBankAccount(),form.getAmount());
		}catch (AccountNotFoundException e) {
			bindingResult.rejectValue("receiverUsername", "", e.getMessage());
			return getUserTransferPage(0,10);
		}catch (InsufficientBalanceException | IllegalArgumentException e) {
			bindingResult.rejectValue("amount", "", e.getMessage());
			return getUserTransferPage(0,10);
		}
		
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/my/transfer");
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
	public TransferToAccountForm transferToAccountForm() {
		return new TransferToAccountForm();
	}
	
	@ModelAttribute(ModelUtils.MODEL_BANK_ACCOUNT_TRANSFER_FORM)
	public TransferToBankAccountForm transferToBankAccountForm() {
		return new TransferToBankAccountForm();
	}
	
	@ModelAttribute(ModelUtils.MODEL_MY_PROFILE)
	public ProfilUI myProfile() {
		return profilService.getProfileInformations();
	}
	
	@ModelAttribute(ModelUtils.MODEL_MY_TRANSACTIONS)
	public List<Transaction> getMyTransactions(){
		return transactionService.getTransactionHistory();
	}
	
	public Page<Transaction> getMyPaginatedTransactions(int page, int size){
		return transactionService.getPaginatedTransactionHistory(PageRequest.of(page, size));
	}
		
	
}
