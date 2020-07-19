package com.web.service;

import java.io.*;

public class CheckPath {

	public boolean checkpath(String folderpath,String filename) {

		String finalpath = "";
		if(filename.equals("")) {
			finalpath = folderpath;
		} else {
			finalpath = folderpath+filename;	
		}
		File f = new File(finalpath);

		if(f.exists()) {
			return true;
		}
		return false;
	}
}