package com.web.keywordcontroller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;

import com.web.keywordsearch.KeywordSearch;

@WebServlet("/keywordsearch")
public class KeywordController extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		StringBuffer jb = new StringBuffer();
		String line = null;

		try {

			BufferedReader reader = request.getReader();
			while((line = reader.readLine()) != null) {
				jb.append(line);
			}
		} catch(Exception e) {
			System.out.println("Error in reading request body"+e.getMessage());
		}

		JsonElement jsonElement = new JsonParser().parse(jb.toString());
		JsonObject jsonObject = jsonElement.getAsJsonObject();

		String folderpath = jsonObject.get("folderpath").getAsString();
		String filename = jsonObject.get("filename").getAsString();
		String keyword = jsonObject.get("keyword").getAsString();
		System.out.println(folderpath);
		System.out.println(filename);
		System.out.println(keyword);

		KeywordSearch.setParameters(folderpath,filename,keyword);

		String json = new Gson().toJson("success");
		response.getWriter().write(json);
	}

}