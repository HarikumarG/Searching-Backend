package com.web.keywordsearch;

import java.io.FileWriter;
import java.io.File;
import java.util.*;

public class KeywordSearch {

	static {
		try {
			System.loadLibrary("hello");
		} catch (UnsatisfiedLinkError e) {
     		System.err.println("Native code library failed to load.\n" + e);
    	}
	}

	private native void startKeySearch(String path,String filename,String keyword);

	private static FileWriter fwrite;

	private static ArrayList<String> keywordlines = new ArrayList<String>();

	private void filewrite(String text) {
		try {
			keywordlines.add(text);
			fwrite.write(text);
			fwrite.write("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<String> setParameters(String path,String file,String key) {
		try {
			File outputfile = new File("/home/harikumar_g/Documents/projects/Searching-Backend/src/main/java/com/web/keywordsearch/Output.txt");
			FileWriter fw = new FileWriter(outputfile,true);
			fwrite = fw;
			new KeywordSearch().startKeySearch(path,file,key);
			fwrite.close();
		} catch(Exception e) {
			System.out.println("Error in opening Output.txt file");
		}
		return keywordlines;
	}

	public static void destroyList() {
		keywordlines.clear();
	}
}