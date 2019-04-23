package com.uav.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uav.dao.BaseDao;
import com.uav.dao.UserDao;
import com.uav.dao.impl.BaseDaoImpl;
import com.uav.dao.impl.UserDaoImpl;
import com.uav.entity.Post;
import com.uav.entity.Punch;
import com.uav.entity.User;
import com.uav.entity.Work;
import com.uav.util.BaseServlet;
import com.uav.util.LayuiCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Preloads
 */
@WebServlet("/Preloads")
public class Preloads extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	public void getTopf(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String forum=new String(request.getParameter("forum").getBytes("ISO-8859-1"),"UTF-8");
		String grade="1";
		BaseDao bs=new BaseDaoImpl();
		List<Post> list=new ArrayList<Post>();
		list=bs.toppost(forum, grade);
		int count=list.size();
		 response.setContentType("application/json;charset=utf-8");
		  JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		/*  System.out.println(JSONArray.fromObject(list));*/
		  System.out.println(json);
		  response.getWriter().print(json);
		
	}
	public void postcount(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		BaseDao bs=new BaseDaoImpl();
		int count=bs.getcount();
		response.getWriter().println(count);
		
	}
    
	//按条件查询post
	public void timepost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String page=request.getParameter("page");
		int limit=6;
		int pages=(Integer.parseInt(page)-1)*limit;
		List list =new ArrayList<Post>();
		BaseDao bs=new BaseDaoImpl();
		list=bs.timedesc(pages,limit);
		int count=list.size();
		 response.setContentType("application/json;charset=utf-8");
		  JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		/*  System.out.println(JSONArray.fromObject(list));*/
		  System.out.println(json);
		  response.getWriter().print(json);
	}
	public void likepost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String page=request.getParameter("page");
		int limit=6;
		int pages=(Integer.parseInt(page)-1)*limit;
		List list =new ArrayList<Post>();
		BaseDao bs=new BaseDaoImpl();
		list=bs.likedesc(pages,limit);
		int count=list.size();
		 response.setContentType("application/json;charset=utf-8");
		  JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		/*  System.out.println(JSONArray.fromObject(list));*/
		  System.out.println(json);
		  response.getWriter().print(json);
	}
	public void getsection(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String forum=request.getParameter("forum");
		
		List list=new ArrayList<String>();
		BaseDao bs=new BaseDaoImpl();
		list=bs.getsection(forum);
		response.setContentType("application/json;charset=utf-8");
		  JSONArray json=JSONArray.fromObject(list);
		  response.getWriter().print(json);
	}
	public void getrank(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 //大神排行榜
		  BaseDao bs=new BaseDaoImpl();
		  List<User> list=new ArrayList<User>();
		  list=bs.ranking();
	      response.setContentType("application/json;charset=utf-8");
	      JSONArray json=JSONArray.fromObject(list);
		  response.getWriter().print(json);
	}
	public void getwork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List list=new ArrayList<Work>();
		 BaseDao bs=new BaseDaoImpl();
		 list=bs.getwork();
		 int count=list.size();
	      response.setContentType("application/json;charset=utf-8");
	      JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		  response.getWriter().print(json);
	}
	  //数据流预加载
	  public void flow(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String page=request.getParameter("page");
		  int i=Integer.parseInt(page);
		  int length=4;
		  int first=(i-1)*4;
		  BaseDao bs=new BaseDaoImpl();
		  List<Post> list=new ArrayList<Post>();
		  list=bs.post(first, length);
		  String table="post";
		  String clas="%";
		  int count= bs.getvalue(table,clas);
		   int pages=0;
		  if(count%4==0){
			  pages=count/4;
		  }else{
			  pages=count/4+1;
		  }
		  
		  
		  /*JSONArray json=JSONArray.fromObject(list);*/
		  response.setContentType("application/json;charset=utf-8");
		  JSONObject json=JSONObject.fromObject(LayuiCode.data(pages, list));
		  /*System.out.println(JSONObject.fromObject(list));*/
		  System.out.println(json);
		  response.getWriter().print(json);
		  
	  }
	  
	  public void forumpost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String page=request.getParameter("page");
		  String forum=new String(request.getParameter("forum").getBytes("ISO-8859-1"),"UTF-8");
		  List<Post> list=new ArrayList<Post>();
		  BaseDao bs=new BaseDaoImpl();
		  int i=Integer.parseInt(page);
		  int first=(i-1)*6;
		  list=bs.forpost(forum, first);
		  String table="post";
		  int count= bs.getvalue(table,forum);
		  int pages=0;
		  if(count%6==0){
			  pages=count/6;
		  }else{
			  pages=count/6+1;
		  }
		  /*JSONArray json=JSONArray.fromObject(list);*/
		  response.setContentType("application/json;charset=utf-8");
		  JSONObject json=JSONObject.fromObject(LayuiCode.data(pages, list));
		  /*System.out.println(JSONObject.fromObject(list));*/
		  System.out.println(json);
		  response.getWriter().print(json);
	  }
	  public void postbyhot(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String page=request.getParameter("page");
		  String forum=new String(request.getParameter("forum").getBytes("ISO-8859-1"),"UTF-8");
		  List<Post> list=new ArrayList<Post>();
		  BaseDao bs=new BaseDaoImpl();
		  int i=Integer.parseInt(page);
		  int first=(i-1)*6;
		  list=bs.postbyhot(forum, first);
		  String table="post";
		  int count= bs.getvalue(table,forum);
		  int pages=0;
		  if(count%6==0){
			  pages=count/6;
		  }else{
			  pages=count/6+1;
		  }
		  /*JSONArray json=JSONArray.fromObject(list);*/
		  response.setContentType("application/json;charset=utf-8");
		  JSONObject json=JSONObject.fromObject(LayuiCode.data(pages, list));
		  /*System.out.println(JSONObject.fromObject(list));*/
		  System.out.println(json);
		  response.getWriter().print(json);
	  }
      public void teach(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	  List list =new ArrayList<Post>();
    	  BaseDao bs=new BaseDaoImpl();
    	  list=bs.teach();
    	  request.setAttribute("teach", list);
    	 
    	  request.getRequestDispatcher("WEB-INF/page/teach.jsp").forward(request, response);
      }
	

}
