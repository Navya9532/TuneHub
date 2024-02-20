package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Users;
import com.example.demo.repository.UsersRepository;
@Service
public class UsersServiceImplementation implements UsersService 
{
	@Autowired
	UsersRepository repo;
	@Override
	public String addUser(Users user) 
	{
		repo.save(user);
		return "user successfully added";
	}
	@Override
	public boolean emailExists(String email) {
		if(repo.findByEmail(email)==null)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	//getting email and password from db for validation
//	@Override
//	public boolean validateUser(String email, String password) {
//		Users user=repo.findByEmail(email);
//		String db_pass=user.getPassword();
//		if(password.equals(db_pass))
//		{
//			return true;
//		}
//		else
//		{
//			return false;	
//		}
//	}
	
	public boolean validateUser(String email, String password) {
	    Users user = repo.findByEmail(email);
	    if (user != null) {
	        String db_pass = user.getPassword();
	        return password.equals(db_pass);
	    } else {
	        // User not found in the database
	        return false;
	    }
	}
	@Override
	public String getRole(String email) {
		Users user=repo.findByEmail(email);
		return user.getRole();
	}
	@Override
	public Users getUser(String email) {
		return repo.findByEmail(email);
	}
	@Override
	public void updateUser(Users user) {
		repo.save(user);
	}

}
