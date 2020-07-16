package com.web.keywordcontroller;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
import com.web.keywordmodel.*;
import com.web.keywordservice.AnalyticsUtil;

@WebServlet("/analytics")
public class AnalyticsController extends HttpServlet {

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

		String keyword = jsonObject.get("keyword").getAsString();
		System.out.println(keyword);


		// System.out.println(AnalyticsUtil.maxSearchCountKey);
		// System.out.println(AnalyticsUtil.maxSearchCountVal);
		// System.out.println(AnalyticsUtil.maxResultCountKey);
		// System.out.println(AnalyticsUtil.maxResultCountVal);

		Map<String,ArrayList<AnalyticsJson>> jsonMap = new HashMap<>();

		ArrayList<AnalyticsJson> templist = new ArrayList<>();
		AnalyticsJson searchcountjson1 = new AnalyticsJson();
		
		searchcountjson1.setKeytype("max");
		searchcountjson1.setKeyname(AnalyticsUtil.maxSearchCountKey);
		searchcountjson1.setKeyval(AnalyticsUtil.maxSearchCountVal);
		templist.add(searchcountjson1);

		AnalyticsModel model = KeywordController.analytics.get(keyword);

		AnalyticsJson searchcountjson2 = new AnalyticsJson();
		searchcountjson2.setKeytype("current");
		searchcountjson2.setKeyname(keyword);
		searchcountjson2.setKeyval(model.getSearchcount());
		templist.add(searchcountjson2);

		jsonMap.put("searchcount",templist);
		
		ArrayList<AnalyticsJson> templist1 = new ArrayList<>();
		AnalyticsJson resultcountjson1 = new AnalyticsJson();

		resultcountjson1.setKeytype("max");
		resultcountjson1.setKeyname(AnalyticsUtil.maxResultCountKey);
		resultcountjson1.setKeyval(AnalyticsUtil.maxResultCountVal);
		templist1.add(resultcountjson1);

		AnalyticsJson resultcountjson2 = new AnalyticsJson();
		resultcountjson2.setKeytype("current");
		resultcountjson2.setKeyname(keyword);
		resultcountjson2.setKeyval(model.getResultcount());
		templist1.add(resultcountjson2);

		jsonMap.put("resultcount",templist1);

		String json = new Gson().toJson(jsonMap);
		response.getWriter().write(json);
	}
}