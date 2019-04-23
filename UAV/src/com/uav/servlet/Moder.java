package com.uav.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uav.dao.AdminDao;
import com.uav.dao.ModerDao;
import com.uav.dao.UserDao;
import com.uav.dao.impl.AdminDaoImpl;
import com.uav.dao.impl.ModerDaoImpl;
import com.uav.dao.impl.UserDaoImpl;
import com.uav.entity.Discuss;
import com.uav.entity.Post;
import com.uav.entity.Work;
import com.uav.util.BaseServlet;
import com.uav.util.LayuiCode;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class Moder
 */
@WebServlet("/Moder")
public class Moder  extends BaseServlet{
	public void querypost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String key=request.getParameter("key");
		ModerDao mddao=new ModerDaoImpl();
		int uid=(int) request.getSession().getAttribute("uid");
	    String section=mddao.myforum(uid);
		String forum=section;
		
		int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查
	
		ModerDao md=new ModerDaoImpl();
		List list =new ArrayList<Post>();
		list =md.querypost(forum, pages, limit);
		
		int count=list.size();
	    int tallpage;
		if(count%limit==0){
			tallpage=count/limit;
		}else{
			tallpage=count/limit+1;
		}
		LayuiCode.data(tallpage, list);
		
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		
		/*System.out.println("moderQuerypost:"+json);*/
		response.getWriter().print(json);
	}
	public void querydiscuss(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String key=request.getParameter("key");
		ModerDao mddao=new ModerDaoImpl();
		int uid=(int) request.getSession().getAttribute("uid");
	    String section=mddao.myforum(uid);
		String forum=section;
		int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查
	
		ModerDao md=new ModerDaoImpl();
		List list =new ArrayList<Post>();
		list =md.querydiscuss(forum, pages, limit);
		int count=list.size();
	    int tallpage;
		if(count%limit==0){
			tallpage=count/limit;
		}else{
			tallpage=count/limit+1;
		}
		LayuiCode.data(tallpage, list);
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		/*System.out.println("moderQuerydiscuss:"+json);*/
		response.getWriter().print(json);
	}
	public void kquerypost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String key=request.getParameter("key");
		ModerDao mddao=new ModerDaoImpl();
		int uid=(int) request.getSession().getAttribute("uid");
	    String section=mddao.myforum(uid);
		String forum=section;
		int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查
	
		ModerDao md=new ModerDaoImpl();
		List list =new ArrayList<Post>();
		list =md.kquerypost(key, forum, pages, limit);
		int count=list.size();
	    int tallpage;
		if(count%limit==0){
			tallpage=count/limit;
		}else{
			tallpage=count/limit+1;
		}
		LayuiCode.data(tallpage, list);
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		/*System.out.println("moderQuerydiscuss:"+json);*/
		response.getWriter().print(json);
	}
	public void unreadpost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		ModerDao mddao=new ModerDaoImpl();
		int uid=(int) request.getSession().getAttribute("uid");
	    String section=mddao.myforum(uid);
		String forum=section;
		int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查
	
		ModerDao md=new ModerDaoImpl();
		List list =new ArrayList<Post>();
		list=md.unreadpost(forum, pages, limit);
		int count=list.size();
	    int tallpage;
		if(count%limit==0){
			tallpage=count/limit;
		}else{
			tallpage=count/limit+1;
		}
		LayuiCode.data(tallpage, list);
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		
		response.getWriter().print(json);
	}
	public void judge(HttpServletRequest request, HttpServletResponse response){
		String status=request.getParameter("status");
		String pid=request.getParameter("pid");
		ModerDao md=new ModerDaoImpl();
		if(md.judge(status, pid)){
			System.out.println("操作成功");
		}
	}
	public void unreadvideo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查	
		ModerDao md=new ModerDaoImpl();
		List list =new ArrayList<Post>();
		list=md.unreadvideo(pages, limit);
		int count=list.size();
	    int tallpage;
		if(count%limit==0){
			tallpage=count/limit;
		}else{
			tallpage=count/limit+1;
		}
		LayuiCode.data(tallpage, list);
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		
		response.getWriter().print(json);
	}
	public void judvideo(HttpServletRequest request, HttpServletResponse response){
		String status=request.getParameter("status");
		String id=request.getParameter("id");
		ModerDao md=new ModerDaoImpl();
		if(md.judvideo(status, id)){
			System.out.println("操作成功");
	              }
		}
	public void report(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		ModerDao mddao=new ModerDaoImpl();
		int uid=(int) request.getSession().getAttribute("uid");
	    String section=mddao.myforum(uid);
		int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查	
		ModerDao md=new ModerDaoImpl();
		List list=new ArrayList<Discuss>();
		list=md.dealreport(section,pages, limit);
		int count=list.size();
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		
		response.getWriter().print(json);
	}
	public void tozero(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String did=request.getParameter("did");
		ModerDao md=new ModerDaoImpl();
		if(md.tozero(did)){
			response.getWriter().print("归零成功");
		}
	}
	public void totop(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String pid=request.getParameter("pid");
		String operate=request.getParameter("operate");
		ModerDao md=new ModerDaoImpl();
		if(md.totop(pid, operate)){
			response.getWriter().print("操作成功");
		}
	}
	public void addteach(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 
		  String subjects=request.getParameter("title");
		  String content=request.getParameter("content");
		  
		  Date date=new Date();
		  DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  int uid=(int) request.getSession().getAttribute("uid");
		  Post post =new Post();
		  
		  post.setSubjects(subjects);
		  post.setContent(content);
		  post.setDate(fmt.format(date));
		  post.setUid(uid);
		  
		  
		  //session获取视频路径
		  String teachpath=(String) request.getSession().getAttribute("workpath");
		  post.setPostimg(teachpath);
		  request.getSession().removeAttribute("workpath");
		  ModerDao md=new ModerDaoImpl();
		  
		  if(md.addteach(post)){
			    PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('发布成功')");	
				out.println("window.location.href = 'http://localhost:8090/UAV/';");
				out.println("</script>");
		  }else{
			    PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('发布失败')");	
				out.println("window.location.href = 'http://localhost:8090/UAV/';");
				out.println("</script>");
		  };
	}

}
