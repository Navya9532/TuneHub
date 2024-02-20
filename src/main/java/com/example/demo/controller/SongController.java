package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Song;
import com.example.demo.services.SongService;

@Controller
public class SongController 
{
 @Autowired
 SongService service;
 @PostMapping("/addSong")
 public String addSong(@ModelAttribute Song song,Model model)
 {
	 //checking whether the song exists in the database or not using song name 
	 boolean songStatus=service.songExists(song.getName());
	 if(songStatus==false)
	 {
		 service.addSong(song);
		 System.out.println("Song has been added successfully");
	 }
	 else
	 {
		 System.out.println("song already exists");
	 }
	 return "adminHome"; 
 }
 //To view all the added songs
 @GetMapping("/viewSongs")
 //model should be imported from model.ui package
 public String viewSongs(Model model)
 {
	List<Song> songsList=service.fetchAllSongs();
	model.addAttribute("songs",songsList);
	 return "displaySongs"; 
 }
 
 @GetMapping("/playSongs")
 public String playSongs(Model model)
 {
	 boolean premiumUser=true;
	 if(premiumUser==true)
	 {
		 List<Song> songsList=service.fetchAllSongs();
		 model.addAttribute("songs",songsList);
	     return "displaySongs"; 
	 }
	 else
	 {
		 return "makePayment";
	 }
 }
 
}
