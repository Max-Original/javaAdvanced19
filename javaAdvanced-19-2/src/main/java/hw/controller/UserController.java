package hw.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import hw.entity.User;
import hw.service.UserService;

@Controller
@RequestMapping("/image-upload")
public class UserController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);
	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

	@Autowired
	private UserService userService;

	@GetMapping(value = { "/", "/index", "/home", "/default" } )
	public String homePage() {
		return "index";
	}

	@GetMapping("/sign-up")
	public String showSignupPage() {
		return "sign-up";
	}

	@PostMapping("/saveUser")
	public @ResponseBody ResponseEntity<?> createUser(@Valid User user, @RequestParam("name") String name,
			@RequestParam String lastName, @RequestParam Integer age, @RequestParam("file") MultipartFile file) {

		try {
			HttpHeaders headers = new HttpHeaders();
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			String UserName = name;
			String UserLastName = lastName;
			Integer UserAge = age;

			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			String fileType = file.getContentType();
			long size = file.getSize();
			String fileSize = String.valueOf(size);

			log.info("Name" + UserName);
			log.info("LastName" + UserLastName);
			log.info("Age" + UserAge);

			log.info("FileName: " + file.getOriginalFilename());
			log.info("FileType: " + file.getContentType());
			log.info("FileSize: " + file.getSize());

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			stream.write(file.getBytes());
			stream.close();

			user.setName(name);
			user.setLastName(lastName);
			user.setAge(age);
			user.setFileName(fileName);
			user.setFilePath(filePath);
			user.setFileType(fileType);
			user.setFileSize(fileSize);

			boolean status = userService.saveUser(user);
			if (status) {
				log.info("User Created");
				headers.add("User Saved With Image - ", fileName);
				return new ResponseEntity<>("User Saved With File - " + fileName, headers, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception:" + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/users")
	public ModelAndView showAllUsers() {
		log.info("Showing Users list with File");
		ModelAndView mav = new ModelAndView("users");
		List<User> usersList = userService.getAllUsers();
		mav.addObject("usersList", usersList);
		return mav;
	}

	@GetMapping("/removeFile/{id}/{deleteFileName}")
	public ModelAndView removeFileHandler(@PathVariable("id") Long id,
			@PathVariable("deletedFileName") String deletedFileName) {
		ModelAndView mav = new ModelAndView("redirect:/image-upload/users");
		String path = null;
		File file = null;
		try {
			path = uploadDirectory + "/" + deletedFileName;
			file = new File(path);
			if (file.exists()) {
				log.info("File Exists: ID" + id + " File " + deletedFileName);
				log.info("Deleteing User with File");
				boolean status = userService.deleteUser(id);
				log.info("Status: " + status);
				if (status) {
					log.info("User Deleted with file Status: " + status);
					List<User> userList = userService.getAllUsers();
					mav.addObject("userList", userList);
					return mav;
				} else {
					log.info("Oops! Something Went Wrong Status: " + status);
					List<User> userList = userService.getAllUsers();
					mav.addObject("userList", userList);
					return mav;
				}
			} else {
				log.info("Oops! File Not Found: " + deletedFileName);
				throw new Exception("Oops! File Not Found: " + deletedFileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return mav;
	}
}
