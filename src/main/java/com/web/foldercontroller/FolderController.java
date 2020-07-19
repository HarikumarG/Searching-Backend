package com.web.foldercontroller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
import com.web.foldersearch.FolderSearch;
import com.web.foldermodel.FolderJson;
import java.util.*;

@WebServlet("/foldersearch")
public class FolderController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		StringBuffer jb = new StringBuffer();
		String line = null;

		try {
			BufferedReader reader = request.getReader();
			while((line = reader.readLine()) != null) {
				jb.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error in reading request body "+e.getMessage());
		}

		JsonElement jsonElement = new JsonParser().parse(jb.toString());
		JsonObject jsonObject = jsonElement.getAsJsonObject();

		String folderpath = jsonObject.get("folderpath").getAsString();
		String pattern = jsonObject.get("pattern").getAsString();

		System.out.println(folderpath);
		System.out.println(pattern);

		String res[] = FolderSearch.setParameters(folderpath,pattern);

		String folder = "/home/harikumar_g/Documents/projects/Searching-Backend/src/main/java/com/web/foldersearch/Output/";
		
		ArrayList<FolderJson> jsonList = new ArrayList<FolderJson>();

		if(res.length > 0) {
			for(int i = 0; i < res.length; i++) {
				String filepath = folder+res[i];
				File file = new File(filepath);
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					FolderJson json = new FolderJson();
					String strline;
					String[] property = new String[10];
					int j = 0;
					while((strline = br.readLine()) != null) {
						property[j] = strline;
						j++;
					}
					json.setFilename(property[0]);
					json.setFilepath(property[1]);
					json.setFilesize(property[2]);
					json.setCreatedtime(property[3]);
					json.setModifiedtime(property[4]);
					jsonList.add(json);
				} catch(Exception e) {
					System.out.println("Error in reading meta-file "+e.getMessage());
				}
			}
		} else {
			System.out.println("No meta files created");
		}
		String jsonObj = new Gson().toJson(jsonList);
		response.getWriter().write(jsonObj);
	}
}