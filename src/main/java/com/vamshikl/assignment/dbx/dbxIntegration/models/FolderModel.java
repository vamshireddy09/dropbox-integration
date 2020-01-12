package com.vamshikl.assignment.dbx.dbxIntegration.models;

import java.util.ArrayList;
import java.util.List;

public class FolderModel {

	private String folderName;
	
	private List<FileModel> files = new ArrayList<>();
	
	private List<FolderModel> folders = new ArrayList<>();
	
	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public List<FileModel> getFiles() {
		return files;
	}

	public void setFiles(List<FileModel> files) {
		this.files = files;
	}

	public List<FolderModel> getFolders() {
		return folders;
	}

	public void setFolders(List<FolderModel> folders) {
		this.folders = folders;
	}
	
	public void setFolder(FolderModel folder) {
		this.folders.add(folder);
	}
	
	public void setFile(FileModel file) {
		this.files.add(file);
	}
	
}
