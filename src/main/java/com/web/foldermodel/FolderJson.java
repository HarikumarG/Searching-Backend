package com.web.foldermodel;

public class FolderJson {

	private String id;
	private String filename;
	private String filepath;
	private String filesize;
	private String createdtime;
	private String modifiedtime;

	public String getId() {
		return id;
	}
	public String getFilename() {
		return filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public String getFilesize() {
		return filesize;
	}
	public String getCreatedtime() {
		return createdtime;
	}
	public String getModifiedtime() {
		return modifiedtime;
	}

	public void setId(String id) {
		this.id = id;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}
	public void setModifiedtime(String modifiedtime) {
		this.modifiedtime = modifiedtime;
	}
}