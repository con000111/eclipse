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
			System.out.println("�������ӳɹ�");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("������ʼʧ��");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("���ݿ�����ʧ��");
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
				System.out.println("���ݿ���ɾ���쳣");
			}return i;
		
	}
	public static ResultSet selectSql(String sql){
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			rs=ps.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("���ݿɲ�ѯ�쳣");
		}
		return rs;
	}
	public static void closeConn(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("���ݿ�ر��쳣");
		}
	}


}
