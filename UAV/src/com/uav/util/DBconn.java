package com.uav.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBconn {
	static String url="jdbc:mysql://127.0.0.1:3306/uav?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&userssl=false";
	static String username="root";
	static String password="123456";
	static Connection conn=null;
	static ResultSet rs=null;
	
	public static void init(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(url,username,password);
			System.out.println("数据连接成功");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("驱动初始失败");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库连接失败");
			e.printStackTrace();
		}
	}
	public static int addupdel(String sql){
		int i=0;
		
			try {
				PreparedStatement ps=conn.prepareStatement(sql);
				i=ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("数据库增删改异常");
			}return i;
		
	}
	public static ResultSet selectSql(String sql){
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			rs=ps.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据可查询异常");
		}
		return rs;
	}
	public static void closeConn(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库关闭异常");
		}
	}


}
