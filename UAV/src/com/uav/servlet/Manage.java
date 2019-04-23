package com.uav.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uav.dao.AdminDao;
import com.uav.dao.ModerDao;
import com.uav.dao.impl.AdminDaoImpl;
import com.uav.dao.impl.ModerDaoImpl;
import com.uav.entity.Apply;
import com.uav.entity.Discuss;
import com.uav.entity.Evaluate;
import com.uav.entity.Post;
import com.uav.entity.Section;
import com.uav.entity.User;
import com.uav.util.BaseServlet;
import com.uav.util.DateJsonValueProcessor;
import com.uav.util.LayuiCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class Manage
 */
@WebServlet("/Manage")
public class Manage extends BaseServlet {
	public void querydiscuss(HttpServletRequest request, HttpServletResponse response) throws IOException{
		AdminDao admin=new AdminDaoImpl();
		List<Discuss> list =new ArrayList<Discuss>();
		list =admin.queryalldiscuss();
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
		System.out.println("list.toString:"+list.toString());
		
		
		
		JsonConfig jsonConfig = new JsonConfig();//处理日期转换JSON格式问题
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONArray json=JSONArray.fromObject(list,jsonConfig);//list转json
		
		System.out.println("Querydiscuss:"+json);
		String abc="{"+"\"code\":0"+",\"msg\":\"徐sir\""+",\"count\":6"+",\"data\":"+json.toString()+"}";
		System.out.println(abc);
		
		response.setContentType("application/json;charset=utf-8");
		/*response.getWriter().print(LayuiCode.data(10, json));*/
		response.getWriter().print(abc);
		/*LayuiCode.data(10, json).toString()*/
	}
	public void querypost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查
	
		AdminDao admin=new AdminDaoImpl();
		List list =new ArrayList<Post>();
		list =admin.querypost(pages, limit);
		
		int count=admin.getValue("post");
	    int tallpage;
		if(count%limit==0){
			tallpage=count/limit;
		}else{
			tallpage=count/limit+1;
		}
		LayuiCode.data(tallpage, list);
		
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		
		System.out.println("Querydiscuss:"+json);
		response.getWriter().print(json);

		
	}
	public void queryuser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查

		AdminDao admin=new AdminDaoImpl();
		List<User> list =new ArrayList<User>();
		list =admin.queryuser(pages, limit);
		int count=admin.getValue("user");
	    int tallpage;
		if(count%limit==0){
			tallpage=count/limit;
		}else{
			tallpage=count/limit+1;
		}
		LayuiCode.data(tallpage, list);
		
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		
		System.out.println("Querydiscuss:"+json);
		response.getWriter().print(json);
	}
	public void deluser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uid=request.getParameter("uid");
		AdminDao ad=new AdminDaoImpl();
		
		if(ad.deluser(uid)){
			System.out.println("删除成功");
			response.getWriter().println("删除成功");
		}
	}	
	public void upduser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String nick=request.getParameter("nick");
		String sex=request.getParameter("sex");
		String address=request.getParameter("address");
		String sign=request.getParameter("sign");
		String uauth=request.getParameter("uauth");
		int  fraction=Integer.parseInt(request.getParameter("fraction"));
		int uid=Integer.parseInt(request.getParameter("uid"));
		User us=new User();
		us.setAddress(address);
		us.setFraction(fraction);
		us.setPassword(password);
		us.setNick(nick);
		us.setSex(sex);
		us.setSign(sign);
		us.setUauth(uauth);
		us.setUsername(username);
		us.setUid(uid);
		AdminDao ad=new AdminDaoImpl();
		System.out.println("11111111111"+us.getAddress());
		
		if(ad.upduser(us)){
			System.out.println("更新成功");
			response.getWriter().println("更新成功");
		};		
	}
	public void insuser(HttpServletRequest request, HttpServletResponse response){
		String username=request.getParameter("username");
		String password=request.getParameter("password");
	    User user=new User();
	    user.setUsername(username);
	    user.setPassword(password);
		AdminDao ad=new AdminDaoImpl();
		ad.insuser(user);
	}
	public void adduser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uname=request.getParameter("username");
		String pass=request.getParameter("password");
		System.out.println(uname);
		AdminDao ad=new AdminDaoImpl();
		if(ad.adduser(uname, pass)){
			 PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('添加成功!!')");	
				out.println("history.back();");
				out.println("</script>");
		}else{
			 PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('添加成功!!')");	
				out.println("history.back();");
				out.println("</script>");
		}
	}
    public void delpost(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String pid=request.getParameter("pid");
    	AdminDao ad=new AdminDaoImpl();
    	if(ad.delpost(pid)){
			 PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('删除帖子成功!!')");	
				out.println("history.back();");
				out.println("</script>");
    	}
    }
    public void updpost(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String subjects=request.getParameter("subjects");
    	String content=request.getParameter("content");
    	String date=request.getParameter("date");
    	int uid=Integer.parseInt(request.getParameter("uid"));
    	String forum=request.getParameter("forum");
    	int like=Integer.parseInt(request.getParameter("like"));
    	int grade=Integer.parseInt(request.getParameter("grade"));
    	String imgname=request.getParameter("postimg");	
    	int pid=Integer.parseInt(request.getParameter("pid"));
    	Post ps=new Post();
    	ps.setContent(content);
    	ps.setDate(date);
    	ps.setForum(forum);
    	ps.setGrade(grade);
    	ps.setLike(like);
    	ps.setPid(pid);
    	ps.setUid(uid);
    	ps.setSubjects(subjects);
    	ps.setPostimg(imgname);
    	AdminDao ad=new AdminDaoImpl();
    	if(ad.updpost(ps)){
			 PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('帖子更新成功!!')");	
				out.println("history.back();");
				out.println("</script>");
    	};
    }
    public void deldiscuss(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String did=request.getParameter("did");
    	AdminDao ad=new AdminDaoImpl();
    	if(ad.deldiscuss(did)){
			 PrintWriter out=response.getWriter();
		
				out.println("评论删除成功");	

    	}
    }
    public void upddiscuss(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  int did=Integer.parseInt(request.getParameter("did"));
		  int uid=Integer.parseInt(request.getParameter("uid"));
		  String discuss=request.getParameter("discuss");
		  int likenum=Integer.parseInt(request.getParameter("likenum"));
		  int pid=Integer.parseInt(request.getParameter("pid"));
		  String time=request.getParameter("time");
		  Discuss ds=new Discuss();
		  ds.setDid(did);
		  ds.setDiscuss(discuss);
		  ds.setLikenum(likenum);
		  ds.setPid(pid);
		  ds.setTime(time);
		  ds.setUid(uid);
		  AdminDao ad=new AdminDaoImpl();
		  if(ad.upddiscuss(ds)){
	    	    PrintWriter out=response.getWriter();
				out.println("评论更新成功!!");	
		  }
    }
    public void ukeyquery(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	  String key=request.getParameter("key");
    	  int page=Integer.parseInt(request.getParameter("page"));
  		int limit=Integer.parseInt(request.getParameter("limit"));
  		int pages=(page-1)*limit;//从第几个开始查

  		AdminDao admin=new AdminDaoImpl();
  		List<User> list =new ArrayList<User>();
  		list =admin.ukeyquery(key, pages, limit);
  		int count=list.size();
  		System.out.println("通过list.size获取查询的个数："+count);
  	    int tallpage;
  		if(count%limit==0){
  			tallpage=count/limit;
  		}else{
  			tallpage=count/limit+1;
  		}
  		LayuiCode.data(tallpage, list);
  		
  		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
  		
  		System.out.println("Querydiscuss:"+json);
  		response.getWriter().print(json);
    }
    public void pkeyquery(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String key=request.getParameter("key");
    	int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查

		AdminDao admin=new AdminDaoImpl();
		List list =new ArrayList<Post>();
		list =admin.pkeyquery(key, pages, limit);
		int count=list.size();
		System.out.println("通过size搜索的记录数"+count);
	    int tallpage;
		if(count%limit==0){
			tallpage=count/limit;
		}else{
			tallpage=count/limit+1;
		}
		LayuiCode.data(tallpage, list);
		
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		System.out.println("Querydiscuss:"+json);
		response.getWriter().print(json);
    }
    public void dealapply(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	List list=new ArrayList<Apply>();
    	AdminDao ad=new AdminDaoImpl();
    	int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查
    	list=ad.dealapply(pages,limit);
    	int count=list.size();
		System.out.println("通过size搜索的记录数"+count);
	    int tallpage;
		if(count%limit==0){
			tallpage=count/limit;
		}else{
			tallpage=count/limit+1;
		}
		LayuiCode.data(tallpage, list);
		
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		System.out.println("Querydiscuss:"+json);
		response.getWriter().print(json);
    }
    public void getsection(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	AdminDao ad=new AdminDaoImpl();
    	List list=new ArrayList<Section>();
    	int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查
    	list=ad.getsection(pages,limit);
    	int count=list.size();
		System.out.println("通过size搜索的记录数"+count);
	    int tallpage;
		if(count%limit==0){
			tallpage=count/limit;
		}else{
			tallpage=count/limit+1;
		}
		LayuiCode.data(tallpage, list);
		
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		System.out.println("Querydiscuss:"+json);
		response.getWriter().print(json);  	
    }
    public void addsection(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String vendor= new String(request.getParameter("vendor").getBytes("iso-8859-1"), "utf-8");//将传递过来的参数编码格式转换成UTF-8
    	String section= new String(request.getParameter("section").getBytes("iso-8859-1"), "utf-8");//将传递过来的参数编码格式转换成UTF-8
    	AdminDao ad=new AdminDaoImpl();//创建数据防问层对象
    	/*
    	 * 执行添加板块方法，添加成功返回添加成功提示
    	 */
    	if(ad.addsec(vendor, section)){
			 PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('板块添加成功!!')");	
				out.println("history.back();");
				out.println("</script>");
    	}
    }
    public void editsection(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String vendor=request.getParameter("vendor");
    	String section=request.getParameter("section");
    	String uid=request.getParameter("uid");
    	String id=request.getParameter("id");
    	AdminDao ad=new AdminDaoImpl();
    	if(ad.editsec(vendor, section, uid,id)){
    		response.getWriter().println("修改板块成功");
    	}
    }
    public void delsection(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String section=request.getParameter("section");
    	String uid=request.getParameter("uid");
    	AdminDao ad=new AdminDaoImpl();
    	if(ad.delsec(uid, section)){
    		response.getWriter().println("删除板块成功");
    	}
    }
    public void getevaluate(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查
		AdminDao admin=new AdminDaoImpl();
		List list =new ArrayList<Evaluate>();
		list=admin.getevaluate(pages, limit);
		int count=list.size();		
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));	
		System.out.println("Querydiscuss:"+json);
		response.getWriter().print(json);
    	
    }
    public void uauth(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String uid=request.getParameter("uid");
    	String auth=request.getParameter("auth");
    	AdminDao ad=new AdminDaoImpl();
    	if(ad.uauth(uid, auth)){
    		response.getWriter().print("修改权成功");    		
    	}
    }
    public void dealpost(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	
    	String status=request.getParameter("status");
    	List list=new ArrayList<Apply>();
    	AdminDao ad=new AdminDaoImpl();
    	int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查
    	list=ad.dealpost(status, pages,limit);
    	int count=list.size();
		System.out.println("通过size搜索的记录数"+count);
		
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		System.out.println("Querydiscuss:"+json);
		response.getWriter().print(json);
    	
    }
    public void dealreport(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int page=Integer.parseInt(request.getParameter("page"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		int pages=(page-1)*limit;//从第几个开始查	
		AdminDao ad=new AdminDaoImpl();
		List list=new ArrayList<Discuss>();
		list=ad.dealreport(pages, limit);
				
		int count=list.size();
		JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		System.out.println("moderQueryreport:"+json);
		response.getWriter().print(json);
    }
    
    
    
    
    
}
