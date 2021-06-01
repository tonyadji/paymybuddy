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
public class UserHomePageController extends AbstractController {
	
	@GetMapping("/home")
	public ModelAndView getUserHomePage() {
		return super.getRequest();
	}

	@Override
	public String getView() {
		return ViewUtils.VIEW_USER_HOME_PAGE;
	}

	@Override
	public String getTitle() {
		return "Home";
	}

}
