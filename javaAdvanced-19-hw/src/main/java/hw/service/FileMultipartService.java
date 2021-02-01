package hw.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import hw.domain.FileMultipart;
import hw.domain.User;
import hw.repository.FileMultipartRepository;

@Service
public class FileMultipartService {

	@Autowired
	private FileMultipartRepository multipartRepository;

	
	public FileMultipart storeFile(MultipartFile file, String name, String email) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		FileMultipart multipart = null;
		User user = null;
		
		if(!fileName.contains("..")) {
			multipart = new FileMultipart(fileName, file.getContentType(), file.getBytes());
			user = new User(name, email);
		}
		
		
		return multipartRepository.save(multipart);
		// UserRepo.save(user) ?
	}

public FileMultipart getFile(String fielId) throws FileNotFoundException {
	return multipartRepository.findById(fielId).orElseThrow(() ->new FileNotFoundException("field not found"));
}

}
