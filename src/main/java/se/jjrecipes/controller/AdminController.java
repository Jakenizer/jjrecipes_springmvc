package se.jjrecipes.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.jjrecipes.dao.UserDao;
import se.jjrecipes.dao.UserRoleDao;
import se.jjrecipes.data.UserData;
import se.jjrecipes.data.UserRoleData;
import se.jjrecipes.entity.User;
import se.jjrecipes.entity.UserRole;
import se.jjrecipes.form.UserForm;
import se.jjrecipes.service.AdminService;
import se.jjrecipes.util.GeneralUtil;

@Controller
public class AdminController {
	private static Logger logger = LoggerFactory.getLogger(AdminController.class); //slf4j logger
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private AdminService adminService;
	
	@ModelAttribute
	public void alwaysAdd(Model model) {
		model.addAttribute("isAdmin", GeneralUtil.isAdmin());
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView mv = new ModelAndView("/admin/admin");

	    if (!GeneralUtil.isAdmin()) {
	    	mv.setViewName("error");
        	mv.addObject("message", "Du saknar behörighet till denna sida.");
        	mv.addObject("returnpage", "list_and_search");
	    }
	    
		List<User> users = userDao.listUsers();
		mv.addObject("users", users);
		return mv;
 
	}
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ModelAndView createUser(@Valid @ModelAttribute UserForm form) {
	    if (!GeneralUtil.isAdmin()) {
	    	ModelAndView mv = new ModelAndView();
	    	mv.setViewName("error");
        	mv.addObject("message", "Du saknar behörighet till denna funktion.");
        	mv.addObject("returnpage", "/user/list_and_search");
        	return mv;
	    }
		
		User user = new User();
		user.setFirstname(form.getFirstname());
		user.setLastname(form.getLastname());
		user.setUsername(form.getUsername());
		user.setPassword(form.getPassword());
		user.setEnabled(form.getIsenabled());
		
		adminService.createUser(user, form.isAdmin());
		
		
		ModelAndView mv = new ModelAndView("redirect:admin");
		
		return mv;
		
	}
	
	/*
	@RequestMapping(value = "/modifyuser", method = RequestMethod.POST)
	public ModelAndView modifyUser() {
		
	}*/
	
	
	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	public ModelAndView deleteuser(@RequestParam(value = "userId", required = true) Long userId) {
		ModelAndView mv = new ModelAndView("redirect:admin");
		boolean success = adminService.deleteUser(userDao.get(userId));
		if (!success) {
        	mv.addObject("message", "Error while deleting user.");
        	mv.addObject("returnpage", "admin");
        	mv.addObject("exception", "");
            mv.setViewName("/user/error");
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
