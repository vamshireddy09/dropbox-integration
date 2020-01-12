package com.vamshikl.assignment.dbx.dbxIntegration.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.vamshikl.assignment.dbx.dbxIntegration.models.FileModel;
import com.vamshikl.assignment.dbx.dbxIntegration.models.FolderModel;

@Service
public class FileResourceService {

	private DbxClientV2 dbxClient;
	
	/**
	 * Initializing the DbxClient with the Access Token.
	 * @param token
	 */
	public void initDbxClient(String token) {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("").build();
		this.dbxClient = new DbxClientV2(config, token);
	}
	
	/**
	 * Gets all the folders and files inside a given folder in dropbox
	 * @param folderName
	 * @return
	 */
	public FolderModel getFolderModel(String folderName) {
		FolderModel folderModel = new FolderModel();
		String folderPath = "/" + folderName;
		
		folderModel.setFolderName(folderPath.substring(folderPath.lastIndexOf('/')+1));
		
		ListFolderResult folderResult = null;
		try {
			folderResult = this.dbxClient.files().listFolder(folderPath);
		} catch (DbxException e) {
			e.printStackTrace();
		}
		
		folderResult.getEntries().stream().forEach(entry -> {
			if (entry instanceof FolderMetadata) {
				folderModel.setFolder(getFolderModel(entry.getPathDisplay()));
			} else if (entry instanceof FileMetadata) {
				folderModel.setFile(new FileModel(entry.getName(), entry.getPathDisplay()));;
			}
		});
		
		return folderModel;
	}
	
	/**
	 * downloads a file present at given path in dropbox
	 * e.g. path = /test/innerTest/testFile.txt
	 * @param path
	 * @return
	 */
	public Resource downloadFile(String path) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			this.dbxClient.files().downloadBuilder("/" + path).download(byteArrayOutputStream);
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Resource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
		return resource;
	}
	
	/**
	 * uploads a file to given path in dropbox
	 * e.g. path = /test/innerTest and file attribute contains the actual file data
	 * @param path
	 * @param file
	 * @return
	 */
	public String uploadFile(String path, MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			this.dbxClient.files().upload("/" + path + "/" + fileName).uploadAndFinish(file.getInputStream());
		} catch (DbxException | IOException e) {
			e.printStackTrace();
		}
		
		return "/" + path + "/" + fileName;
	}
}
