package hw;


import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hw.controller.UserController;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// Below line will create "uploads" folder at startup if not created.
		new File(UserController.uploadDirectory).mkdir();
		SpringApplication.run(Application.class, args);
	}
}
