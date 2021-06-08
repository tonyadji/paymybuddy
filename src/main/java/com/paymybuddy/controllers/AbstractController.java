/**
 * 
 */
package com.paymybuddy.controllers;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.utils.ModelUtils;
import com.paymybuddy.utils.ViewUtils;

/**
 * @author tonys
 *
 */
public abstract class AbstractController {

	public ModelAndView getRequest() {
		return new ModelAndView(ViewUtils.GENERIC_TEMPLATE);
	}
	
	@ModelAttribute(ModelUtils.MODEL_VIEW)
	public String view() {
		return getView();
	}
	
	@ModelAttribute(ModelUtils.MODEL_TITLE)
	public String title() {
		return getTitle();
	}
	
	
	
	public abstract String getView();
	
	public abstract String getTitle();
}
