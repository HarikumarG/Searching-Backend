package com.web.keyworddao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.web.keywordmodel.AnalyticsModel;
import com.web.keywordservice.AnalyticsUtil;

public class KeywordDao {

	Connection conn;
	public KeywordDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/keywordsearch";
			Connection con = (Connection) DriverManager.getConnection(url,"root","");
			this.conn = con;
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean updateValue(String keyword,long searchcount,long resultcount,long timetaken) {
		Statement stmt;
		boolean result = false;
		try {
			stmt = (Statement) conn.createStatement();
			String url = "select * from keyword where word='"+keyword+"'";
			ResultSet rs = stmt.executeQuery(url);
			if(rs.next() != false) {
				long search = Integer.parseInt(rs.getString(2));
				search++;
				String urls = "update keyword set searchcount="+search+",resultcount="+resultcount+",timetaken="+timetaken+" where word='"+keyword+"'";
				int rowNum = stmt.executeUpdate(urls);
				if(rowNum > 0) {
					result = true;
				}
			} else {
				String urls = "insert into keyword(word,searchcount,resultcount,timetaken)values('"+keyword+"',"+searchcount+","+resultcount+","+timetaken+")";
				int rowNum = stmt.executeUpdate(urls);
				if(rowNum > 0) {
					result = true;
				}
			}
			conn.close();
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public AnalyticsModel getAnalyticsData(String keyword) {
		AnalyticsModel model = new AnalyticsModel();
		Statement stmt;
		try {

			stmt = (Statement) conn.createStatement();
			String url1 = "select * from keyword where word='"+keyword+"'";
			ResultSet rs1 = stmt.executeQuery(url1);
			if(rs1.next() != false) {
				model.setSearchcount(Integer.parseInt(rs1.getString(2)));
				model.setResultcount(Integer.parseInt(rs1.getString(3)));
				model.setTimetaken(Integer.parseInt(rs1.getString(4)));
			}


			String url2 = "select * from keyword order by searchcount desc limit 1";
			ResultSet rs2 = stmt.executeQuery(url2);
			if(rs2.next() != false) {
				AnalyticsUtil.maxSearchCountKey = rs2.getString(1);
				AnalyticsUtil.maxSearchCountVal = Integer.parseInt(rs2.getString(2));
			}
			String url3 = "select * from keyword order by searchcount asc limit 1";
			ResultSet rs3 = stmt.executeQuery(url3);
			if(rs3.next() != false) {
				AnalyticsUtil.minSearchCountKey = rs3.getString(1);
				AnalyticsUtil.minSearchCountVal = Integer.parseInt(rs3.getString(2));
			}


			String url4 = "select * from keyword order by resultcount desc limit 1";
			ResultSet rs4 = stmt.executeQuery(url4);
			if(rs4.next() != false) {
				AnalyticsUtil.maxResultCountKey = rs4.getString(1);
				AnalyticsUtil.maxResultCountVal = Integer.parseInt(rs4.getString(3));
			}
			String url5 = "select * from keyword order by resultcount asc limit 1";
			ResultSet rs5 = stmt.executeQuery(url5);
			if(rs5.next() != false) {
				AnalyticsUtil.minResultCountKey = rs5.getString(1);
				AnalyticsUtil.minResultCountVal = Integer.parseInt(rs5.getString(3));
			}


			String url6 = "select * from keyword order by timetaken desc limit 1";
			ResultSet rs6 = stmt.executeQuery(url6);
			if(rs6.next() != false) {
				AnalyticsUtil.maxTimeTakenKey = rs6.getString(1);
				AnalyticsUtil.maxTimeTakenVal = Integer.parseInt(rs6.getString(4));
			}
			String url7 = "select * from keyword order by timetaken asc limit 1";
			ResultSet rs7 = stmt.executeQuery(url7);
			if(rs7.next() != false) {
				AnalyticsUtil.minTimeTakenKey = rs7.getString(1);
				AnalyticsUtil.minTimeTakenVal = Integer.parseInt(rs7.getString(4));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return model;
	}
}