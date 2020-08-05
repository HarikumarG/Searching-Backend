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
import com.web.keyworddao.KeywordDao;

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

		KeywordDao dao = new KeywordDao();
		AnalyticsModel model = dao.getAnalyticsData(keyword);

		Map<String,ArrayList<AnalyticsJson>> jsonMap = new HashMap<>();

		ArrayList<AnalyticsJson> templist = new ArrayList<>();
		AnalyticsJson searchcountjson1 = new AnalyticsJson();
		
		searchcountjson1.setKeytype("max");
		searchcountjson1.setKeyname(AnalyticsUtil.maxSearchCountKey);
		searchcountjson1.setKeyval(AnalyticsUtil.maxSearchCountVal);
		templist.add(searchcountjson1);

		AnalyticsJson searchcountjson2 = new AnalyticsJson();
		searchcountjson2.setKeytype("current");
		searchcountjson2.setKeyname(keyword);
		searchcountjson2.setKeyval(model.getSearchcount());
		templist.add(searchcountjson2);

		AnalyticsJson searchcountjson3 = new AnalyticsJson();
		searchcountjson3.setKeytype("min");
		searchcountjson3.setKeyname(AnalyticsUtil.minSearchCountKey);
		searchcountjson3.setKeyval(AnalyticsUtil.minSearchCountVal);
		templist.add(searchcountjson3);

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

		AnalyticsJson resultcountjson3 = new AnalyticsJson();
		resultcountjson3.setKeytype("min");
		resultcountjson3.setKeyname(AnalyticsUtil.minResultCountKey);
		resultcountjson3.setKeyval(AnalyticsUtil.minResultCountVal);
		templist1.add(resultcountjson3);

		jsonMap.put("resultcount",templist1);

		ArrayList<AnalyticsJson> templist2 = new ArrayList<>();
		AnalyticsJson timetakenjson1 = new AnalyticsJson();

		timetakenjson1.setKeytype("max");
		timetakenjson1.setKeyname(AnalyticsUtil.maxTimeTakenKey);
		timetakenjson1.setKeyval(AnalyticsUtil.maxTimeTakenVal);
		templist2.add(timetakenjson1);

		AnalyticsJson timetakenjson2 = new AnalyticsJson();
		timetakenjson2.setKeytype("current");
		timetakenjson2.setKeyname(keyword);
		timetakenjson2.setKeyval(model.getTimetaken());
		templist2.add(timetakenjson2);

		AnalyticsJson timetakenjson3 = new AnalyticsJson();
		timetakenjson3.setKeytype("min");
		timetakenjson3.setKeyname(AnalyticsUtil.minTimeTakenKey);
		timetakenjson3.setKeyval(AnalyticsUtil.minTimeTakenVal);
		templist2.add(timetakenjson3);

		jsonMap.put("timetaken",templist2);

		String json = new Gson().toJson(jsonMap);
		response.getWriter().write(json);
	}
}