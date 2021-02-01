package hw.controler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import hw.domain.FileMultipart;
import hw.dto.MultipartUploadResponse;
import hw.service.FileMultipartService;

@RestController
public class FileMultipartController {

	@Autowired
	private FileMultipartService fileMultipartService;

	@RequestMapping(path = {"/uploadFile"}, method = RequestMethod.POST)
	public MultipartUploadResponse uploadFiel(@RequestParam("file") MultipartFile file, @RequestParam("user") String name, @RequestParam ("email") String email)  throws IOException {

		FileMultipart fileMultipart = fileMultipartService.storeFile(file, name, email);

		String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileMultipart.getId()).toUriString();
		
		
		
		
		return new MultipartUploadResponse(fileMultipart.getFileName(), fileDownloadUrl, file.getContentType(),
				file.getSize());
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws FileNotFoundException {
		FileMultipart fileMultipart = fileMultipartService.getFile(fileId);

		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileMultipart.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileMultipart.getFileName() + "\"")
                .body(new ByteArrayResource(fileMultipart.getData()));
	}
	

}
