package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController 
{
//on clicking login button login page opens
 @GetMapping("/login")
 public String Login()
 {
	 return "login";
 }
//on clicking register button registration page opens
 @GetMapping("/registration")
 public String registration()
 {
	 return "registration";
 }
 //to add the new song by admin
 @GetMapping("/newSong")
 public String newSong()
 {
	 return "newSong";
 }
 @GetMapping("/about")
 public String about()
 {
	 return "about";
 }
}
