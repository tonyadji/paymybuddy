/**
 * 
 */
package com.paymybuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.paymybuddy.utils.SecurityUtils;
import com.paymybuddy.utils.ViewUtils;

/**
 * @author tonys
 *
 */
@Controller
public class IndexController extends AbstractController {
	
	@GetMapping("/")
	public ModelAndView getLoginPage() {
		if(SecurityUtils.isAuthenticated()) {
			RedirectView rv = new RedirectView("/my/home");
			return new ModelAndView(rv);
		}
		return super.getRequest();
	}

	@Override
	public String getView() {
		return ViewUtils.VIEW_LOGIN;
	}

	@Override
	public String getTitle() {
		return "Login";
	}

}
