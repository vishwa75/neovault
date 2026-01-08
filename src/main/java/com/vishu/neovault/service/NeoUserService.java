package com.vishu.neovault.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishu.neovault.model.NeoUserModel;
import com.vishu.neovault.model.UtlityModel;
import com.vishu.neovault.repository.NeoUserRepository;

@Service
public class NeoUserService {
	
	
	@Autowired
    private NeoUserRepository neoUserRepository;
	
	  public List<NeoUserModel> getAllUsers() {
	        return neoUserRepository.getAllUsers();
	    }
	  
	  public UtlityModel addUser(NeoUserModel neoUserModel) {
		    return neoUserRepository.InsertRecord(neoUserModel);
		}

	  public boolean validateUser(String username, String password) {

	        NeoUserModel user = neoUserRepository.findByUsername(username);

	        if (user == null) {
	            return false;
	        }

	        // TEMP (plain text comparison)
	        return user.getPassword().equals(password);
	    }
	
}
