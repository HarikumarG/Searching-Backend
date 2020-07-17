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
import com.web.keywordservice.AnalyticsUtil;
import java.time.*;

@WebServlet("/keywordsearch")
public class KeywordController extends HttpServlet {

	public static HashMap<String,AnalyticsModel> analytics = new HashMap<String,AnalyticsModel>();

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

		if(analytics.containsKey(keyword)) {
			
			long searchcount = analytics.get(keyword).getSearchcount();
			searchcount++;
			analytics.get(keyword).setSearchcount(searchcount);

			analytics.get(keyword).setResultcount(keylines.size());


			analytics.get(keyword).setTimetaken(time);

			if(AnalyticsUtil.maxSearchCountVal <= analytics.get(keyword).getSearchcount()) {
				AnalyticsUtil.maxSearchCountVal = analytics.get(keyword).getSearchcount();
				AnalyticsUtil.maxSearchCountKey = keyword;
			}
			if(AnalyticsUtil.maxResultCountVal <= analytics.get(keyword).getResultcount()) {
				AnalyticsUtil.maxResultCountVal = analytics.get(keyword).getResultcount();
				AnalyticsUtil.maxResultCountKey = keyword;
			}
			if(AnalyticsUtil.maxTimeTakenVal <= analytics.get(keyword).getTimetaken()) {
				AnalyticsUtil.maxTimeTakenVal = analytics.get(keyword).getTimetaken();
				AnalyticsUtil.maxTimeTakenKey = keyword;
			}

		} else {
			AnalyticsModel obj = new AnalyticsModel();
			obj.setSearchcount(1);
			obj.setResultcount(keylines.size());
			obj.setTimetaken(time);
			analytics.put(keyword,obj);

			if(AnalyticsUtil.maxSearchCountKey == null && AnalyticsUtil.maxResultCountKey == null && AnalyticsUtil.maxTimeTakenKey == null) {
				AnalyticsUtil.maxSearchCountVal = 1;
				AnalyticsUtil.maxSearchCountKey = keyword;
				AnalyticsUtil.maxResultCountVal = keylines.size();	
				AnalyticsUtil.maxResultCountKey = keyword;
				AnalyticsUtil.maxTimeTakenVal = analytics.get(keyword).getTimetaken();
				AnalyticsUtil.maxTimeTakenKey = keyword;
			} else {

				if(AnalyticsUtil.maxSearchCountVal == 1) {
					AnalyticsUtil.maxSearchCountKey = keyword;
				}
				if(AnalyticsUtil.maxResultCountVal <= keylines.size()) {
					AnalyticsUtil.maxResultCountVal = keylines.size();
					AnalyticsUtil.maxResultCountKey = keyword;
				}
				if(AnalyticsUtil.maxTimeTakenVal <= analytics.get(keyword).getTimetaken()) {
					AnalyticsUtil.maxTimeTakenVal = analytics.get(keyword).getTimetaken();
					AnalyticsUtil.maxTimeTakenKey = keyword;
				}
			}
		}

		// System.out.println(AnalyticsUtil.maxSearchCountKey);
		// System.out.println(AnalyticsUtil.maxSearchCountVal);
		// System.out.println(AnalyticsUtil.maxResultCountKey);
		// System.out.println(AnalyticsUtil.maxResultCountVal);
		// System.out.println(analytics.get(keyword).getSearchcount());
		// System.out.println(analytics.get(keyword).getResultcount());
		String minsearchkey = null;
		long minsearchval = Integer.MAX_VALUE;
		String minresultkey = null;
		long minresultval = Integer.MAX_VALUE;
		String mintimetakenkey = null;
		long mintimetakenval = Integer.MAX_VALUE;

		for(Map.Entry<String,AnalyticsModel> data: analytics.entrySet()) {
			if(data.getValue().getSearchcount() < minsearchval) {
				minsearchkey = data.getKey();
				minsearchval = data.getValue().getSearchcount();
			}
			if(data.getValue().getResultcount() < minresultval) {
				minresultkey = data.getKey();
				minresultval = data.getValue().getResultcount();
			}
			if(data.getValue().getTimetaken() < mintimetakenval) {
				mintimetakenkey = data.getKey();
				mintimetakenval = data.getValue().getTimetaken();
			}
		}
		AnalyticsUtil.minSearchCountKey = minsearchkey;
		AnalyticsUtil.minSearchCountVal = minsearchval;
		AnalyticsUtil.minResultCountKey = minresultkey;
		AnalyticsUtil.minResultCountVal = minresultval;
		AnalyticsUtil.minTimeTakenKey = mintimetakenkey;
		AnalyticsUtil.minTimeTakenVal = mintimetakenval;

		KeywordSearch.destroyList();
	}

}