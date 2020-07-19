package com.web.foldersearch;

public class FolderSearch {

	static {
		System.loadLibrary("folder");
	}

	private native String[] startSearch(String path,String pat);

	public static String[] setParameters(String path,String pat) {
		String[] res = new FolderSearch().startSearch(path,pat);
		return res;
	}
}