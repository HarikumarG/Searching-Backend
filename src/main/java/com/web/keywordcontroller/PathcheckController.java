package com.web.keywordcontroller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
import com.web.keywordservice.CheckPath;

@WebServlet("/checkpath")
public class PathcheckController extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		StringBuffer jb = new StringBuffer();
		String line = null;

		try {
			BufferedReader reader = request.getReader();
			while((line = reader.readLine()) != null) {
				jb.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error in reading request body"+e.getMessage());
		}

		JsonElement jsonElement = new JsonParser().parse(jb.toString());
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		String folderpath = jsonObject.get("folderpath").getAsString();
		String filename = jsonObject.get("filename").getAsString();
		
		System.out.println(folderpath);
		System.out.println(filename);

		CheckPath check = new CheckPath();

		boolean status = check.checkpath(folderpath,filename);
		if(status) {
			String json = new Gson().toJson("SUCCESS");
			response.getWriter().write(json);
		} else {
			String json = new Gson().toJson("FAILURE");
			response.getWriter().write(json);
		}
	}
}
