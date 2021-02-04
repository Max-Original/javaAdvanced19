package hw.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hw.entity.User;
import hw.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
	
	@Override
	public boolean saveUser(User user) throws IOException {
		
		try {
			if (user != null) {
				userRepository.save(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public boolean deleteUser(Long id) {
		
		boolean status = false;
		try {
			if(id !=0) {
				userRepository.deleteById(id);
				
				
				return status;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}
