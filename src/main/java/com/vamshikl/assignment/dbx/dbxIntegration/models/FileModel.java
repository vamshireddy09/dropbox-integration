package com.vamshikl.assignment.dbx.dbxIntegration.models;

public class FileModel {
	
	private String fileName;
	
	private String filePath;

	public FileModel(String name, String path) {
		this.fileName = name;
		this.filePath = path;
	}
	
	public FileModel() {
		
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
