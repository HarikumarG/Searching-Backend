package com.web.keywordcontroller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
import java.util.*;
import com.web.keywordsearch.KeywordSearch;
import com.web.keywordmodel.AnalyticsModel;
import com.web.keyworddao.KeywordDao;
import java.time.*;

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
			System.out.println("Error in reading request body "+e.getMessage());
		}

		JsonElement jsonElement = new JsonParser().parse(jb.toString());
		JsonObject jsonObject = jsonElement.getAsJsonObject();

		String folderpath = jsonObject.get("folderpath").getAsString();
		String filename = jsonObject.get("filename").getAsString();
		String keyword = jsonObject.get("keyword").getAsString();
		System.out.println(folderpath);
		System.out.println(filename);
		System.out.println(keyword);

		Instant start = Instant.now();
		ArrayList<String> keylines = KeywordSearch.setParameters(folderpath,filename,keyword);
		Instant stop = Instant.now();
		long time = Duration.between(start,stop).toNanos();

		Map<String,ArrayList<String>> jsonMap = new HashMap<>();

		for(int i = 0; i < keylines.size(); i++) {
			System.out.println(keylines.get(i));
		}

		jsonMap.put(keyword,keylines);

		String json = new Gson().toJson(jsonMap);
		response.getWriter().write(json);

		KeywordDao dao = new KeywordDao();
		boolean isUpdated = dao.updateValue(keyword,1,keylines.size(),time);
		if(isUpdated) { 
			KeywordSearch.destroyList(); 
		} else { 
			System.out.println("DB update is not successful");
		}
	}

}