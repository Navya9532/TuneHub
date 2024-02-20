package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.LoginData;
import com.example.demo.entities.Song;
import com.example.demo.entities.Users;
import com.example.demo.services.SongService;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {
	@Autowired
	UsersService service;
	@Autowired
	SongService songService;
@PostMapping("/register")
public String addUsers(@ModelAttribute Users user,Model model)
{
	//checking if the user has already registered with same email or not
	boolean userStatus=service.emailExists(user.getEmail());
	if(userStatus==false)
	{
		//if the user is not registered then only add the user
		service.addUser(user);
		System.out.println("User added");
	}
	else
	{
		System.out.println("user already exists");
	}
	return "login";
}

//validating the email and password
	@PostMapping("/validate")
	//using the session the complete activity is maintained. 
	public String validate(@RequestParam("email") String email,@RequestParam("password") String password,HttpSession session,Model model)
	{
		if(service.validateUser(email, password)==true)
		{
			String role=service.getRole(email);
			//for the created session attribute we are setting the attribute as email
			session.setAttribute("email", email);
			if(role.equals("admin"))
			{
				return "adminHome";
			}
			else
			{
				Users user=service.getUser(email);
				boolean userStatus=user.isPremium();
				List<Song> songsList=songService.fetchAllSongs();
				model.addAttribute("songs",songsList);
				model.addAttribute("isPremium",userStatus);
				return "customerHome";
			}
		}
		else
		{
			model.addAttribute("loginError", true);
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session)
	{
		//for deleting the session object after logging out
		session.invalidate();
		return "login";
	}
	
	@GetMapping("/adminHome")
	public String adminHome()
	{
		return "adminHome";
	}
	
}
