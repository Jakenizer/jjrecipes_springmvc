package se.jjrecipes.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.jjrecipes.data.UserData;
import se.jjrecipes.data.UserRoleData;
import se.jjrecipes.entity.User;
import se.jjrecipes.entity.UserRole;
import se.jjrecipes.form.UserForm;
import se.jjrecipes.util.GeneralUtil;

@Controller
public class AdminController {
	private static Logger logger = LoggerFactory.getLogger(AdminController.class); //slf4j logger
	
	
	@ModelAttribute
	public void alwaysAdd(Model model) {
		model.addAttribute("isAdmin", GeneralUtil.isAdmin());
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView mv = new ModelAndView("admin/admin");

	    if (!GeneralUtil.isAdmin()) {
	    	mv.setViewName("error");
        	mv.addObject("message", "Du saknar behörighet till denna sida.");
        	mv.addObject("returnpage", "list_and_search");
	    }
	    
		List<User> users = UserData.listUsers();
		mv.addObject("users", users);
		return mv;
 
	}
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ModelAndView createUser(@Valid @ModelAttribute UserForm form) {
	    if (!GeneralUtil.isAdmin()) {
	    	ModelAndView mv = new ModelAndView();
	    	mv.setViewName("error");
        	mv.addObject("message", "Du saknar behörighet till denna funktion.");
        	mv.addObject("returnpage", "list_and_search");
        	return mv;
	    }
		
		User u1 = new User();
		u1.setFirstname(form.getFirstname());
		u1.setLastname(form.getLastname());
		u1.setUsername(form.getUsername());
		u1.setPassword(form.getPassword());
		u1.setEnabled(form.getIsenabled());
		UserData.addUser(u1);
		
		UserRole ur = new UserRole();
		ur.setRole("ROLE_USER");
		ur.setUsername(form.getUsername());
		UserRoleData.addUserRole(ur);
		
		if (form.isAdmin()) {
			UserRole ur2 = new UserRole();
			ur2.setRole("ROLE_ADMINISTRATOR");
			ur2.setUsername(form.getUsername());
			UserRoleData.addUserRole(ur2);
		}
		
		ModelAndView mv = new ModelAndView("redirect:admin");
		
		return mv;
		
	}
	
	/*
	@RequestMapping(value = "/modifyuser", method = RequestMethod.POST)
	public ModelAndView modifyUser() {
		
	}*/
	
	
	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	public ModelAndView deleteuser(@RequestParam(value = "userId", required = true) Long userId) {
		ModelAndView mv = new ModelAndView("admin/admin");
		boolean success = UserData.deleteById(User.class, userId);
		if (!success) {
        	mv.addObject("message", "Error while deleting user.");
        	mv.addObject("returnpage", "admin");
        	mv.addObject("exception", "");
            mv.setViewName("error");
		}
    	return mv;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView allUsers() {
		List<User> users = UserData.listUsers();
		ModelAndView mv = new ModelAndView("admin/admin");
		mv.addObject("users", users);
		
		return mv;
	}

	
}
