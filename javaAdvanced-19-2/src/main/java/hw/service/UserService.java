package hw.service;

import java.io.IOException;
import java.util.List;

import hw.entity.User;

public interface UserService {

	public boolean saveUser(User user) throws IOException;
	
	public List<User> getAllUsers();
	
	public boolean deleteFile(Long id, String file);
	
}
