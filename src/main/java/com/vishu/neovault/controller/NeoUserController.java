package com.vishu.neovault.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vishu.neovault.model.NeoUserModel;
import com.vishu.neovault.model.UtlityModel;
import com.vishu.neovault.service.NeoUserService;
import com.vishu.neovault.utils.Constants;

@Controller
public class NeoUserController {
	
	@Autowired
	public NeoUserService neoUserService;
		
	@GetMapping("/")
	public String userLoginpage(Model model) {
		model.addAttribute("neoUserModel",new NeoUserModel());
		return "auth/neo-user-login";
	}
	
	@PostMapping("/login")
	public String doLogin(@ModelAttribute NeoUserModel neoUserModel,Model model) {

        boolean isValid = neoUserService.validateUser(
        		neoUserModel.getUsername(),
        		neoUserModel.getPassword());
        if (isValid) {
            return "dashboard";
        }
        model.addAttribute("error", "Invalid username or password");
        return "auth/neo-user-login";
	}
	
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
	    model.addAttribute("neoUserModel", new NeoUserModel());
	    model.addAttribute("title", "Register User");
	    return "auth/neo-user-register";
	}
	
	@PostMapping("/register")
	public String registerUser(NeoUserModel neoUserModel,Model model) {
		UtlityModel result = neoUserService.addUser(neoUserModel);
		if (result.getStatus() == Constants.SUCCESS_STATUS) {
			List<NeoUserModel> users = neoUserService.getAllUsers();
			model.addAttribute("users",users);
			model.addAttribute("success",result.getError());
			return "neo-user-login";
	    }
		 model.addAttribute("neoUserModel", neoUserModel);
		 model.addAttribute("error", result.getError());
		return "auth/neo-user-register";
		
	}
	
	
}
