/**
 * 
 */
package com.paymybuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.utils.ViewUtils;

/**
 * @author tonys
 *
 */
@Controller
public class LoginController extends AbstractController {
	
	@GetMapping("/login")
	public ModelAndView getLoginPage() {
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
