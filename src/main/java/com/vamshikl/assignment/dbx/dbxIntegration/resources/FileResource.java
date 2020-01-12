package com.vamshikl.assignment.dbx.dbxIntegration.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vamshikl.assignment.dbx.dbxIntegration.models.FolderModel;
import com.vamshikl.assignment.dbx.dbxIntegration.services.FileResourceService;
import com.vamshikl.assignment.dbx.dbxIntegration.services.Utils;

@RestController
public class FileResource {
	
	@Autowired
	private FileResourceService fileResourceService;
	
	@Autowired
	private Utils utils;
	
	private HttpSession httpSession;

	@GetMapping(value="/folder")
	public ResponseEntity<FolderModel> getFolder(@RequestParam("folder") String folderName, HttpServletRequest request) {
		
		if (!utils.isValidRequest(request)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		if (!utils.isAuthorized(request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		FolderModel folderModel = fileResourceService.getFolderModel(folderName);
		
		return ResponseEntity.ok(folderModel);
	}
	
	@GetMapping(value="/download")
	public ResponseEntity<Resource> download(@RequestParam("filePath") String filePath, HttpServletRequest request) {
		if (!utils.isValidRequest(request)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		if (!utils.isAuthorized(request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		Resource resource = fileResourceService.downloadFile(filePath);
		
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath.substring(filePath.lastIndexOf('/')+1))
                .body(resource);
	}
	
	@PostMapping(value="/upload")
	public ResponseEntity<String> upload(@RequestParam("folder") String folderPath, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
		if (!utils.isValidRequest(request)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		if (!utils.isAuthorized(request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		String pathToFile =  fileResourceService.uploadFile(folderPath, file);
		
		return ResponseEntity.ok("File Uploaded to " + pathToFile);
	}
	
	@PostMapping(value="/register")
	public ResponseEntity<String> registerToken(@RequestParam("token") String accessToken, HttpServletRequest request) {
		httpSession = request.getSession();
		httpSession.setAttribute("accessToken", accessToken);
		
		fileResourceService.initDbxClient(accessToken);
		
		return ResponseEntity.ok("Registered");
	}
	
}
