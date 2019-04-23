package com.uav.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

import org.apache.catalina.connector.Request;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.uav.dao.AdminDao;
import com.uav.dao.BaseDao;
import com.uav.dao.UserDao;
import com.uav.dao.impl.AdminDaoImpl;
import com.uav.dao.impl.BaseDaoImpl;
import com.uav.dao.impl.UserDaoImpl;
import com.uav.entity.Apply;
import com.uav.entity.Message;
import com.uav.entity.Post;
import com.uav.entity.Punch;
import com.uav.entity.User;
import com.uav.entity.Work;
import com.uav.util.BaseServlet;
import com.uav.util.LayuiCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")

public class UserServlet extends BaseServlet {
	  public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	    	String user=request.getParameter("username");
			String pass=request.getParameter("password");		
			System.out.println("用户名："+user+"密码："+pass);
			UserDao us=new UserDaoImpl();
			if(us.login(user, pass)){
				//设置需要保存的登陆信息
				BaseDao uinfo=new BaseDaoImpl();
				User userinfo=new User();
				userinfo=uinfo.setSession(user);
				/*HttpSession session=request.getSession();
				session.setAttribute("userinfo",userinfo );*///找到了以下代替方式
				request.getSession().setAttribute("userinfo", userinfo);
				request.getSession().setAttribute("uid", userinfo.getUid());
				Punch punch=new Punch();
				int uid=0;
				int sum=1;
				uid=(int) request.getSession().getAttribute("uid");
				punch=us.record(uid);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date=new Date();	
				/*System.out.println("时间差："+(date.getTime()-punch.getPunchtime().getTime()) / (24 * 60 * 60 * 1000));*/
		       request.setAttribute("count", punch.getCount());//打卡人数
				if((date.getTime()-punch.getPunchtime().getTime()) / (24 * 60 * 60 * 1000)<1 ){
		        	request.setAttribute("punch", "yes");
		        	System.out.println("今天打卡了");
		        	sum=punch.getSum();
		        }else {
		        	request.setAttribute("punch", "no");
		        	if(2>=(date.getTime()-punch.getPunchtime().getTime()) / (24 * 60 * 60 * 1000)){
		        		sum=punch.getSum()+1;	
		        		System.out.println("sum加一了");
		        	}
		        	System.out.println("今天没打卡");
		        }
		        request.setAttribute("sum", sum);
		        System.out.println("sum:"+sum);        
		        
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}else{
				response.setCharacterEncoding("UTF-8");
	            response.setHeader("Content-Type","text/html;charset=utf-8");
				PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('账号或密码错误!!')");	
				out.println("history.back();");
				out.println("</script>");
			}
			
	    }
	  public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String name=request.getParameter("username");
			String pass=request.getParameter("password");
			String code=request.getParameter("inputCode").toLowerCase();
			String vercode=(String) request.getSession().getAttribute("vacode");
			if(vercode.equals(code)){
				UserDao us=new UserDaoImpl();
				User user=new User();
				user.setUsername(name);
				user.setPassword(pass);
				if(us.register(user)){
					request.getRequestDispatcher("/login.html").forward(request, response);//跳转到登陆
				}else{
					response.setCharacterEncoding("UTF-8");
		            response.setHeader("Content-Type","text/html;charset=utf-8");
					PrintWriter out=response.getWriter();
					out.println("<html>");
					out.println("<head>");
					out.println("<meta charset='utf-8'>");
					out.println("<script>");		
					out.println("alert('注册失败，请再试一次吧!!')");	
					out.println("history.back();");
					out.println("</script>");
					out.println("</head>");
					out.println("<body>");	
					out.println("</body>");
					out.println("</html>");
				}
			}else{
				response.setCharacterEncoding("UTF-8");
	            response.setHeader("Content-Type","text/html;charset=utf-8");
				PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('验证码错误!!')");	
				out.println("history.back();");
				out.println("</script>");
				out.println("</head>");
				out.println("<body>");	
				out.println("</body>");
				out.println("</html>");
			}
			
	}
	 
	  public void outlogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  request.getSession().invalidate();
		  response.sendRedirect("index.jsp");
	  }
	  
	  
      public void upheadimg(HttpServletRequest request, HttpServletResponse response){
		  String savePath=this.getServletContext().getRealPath("/WEB-INF/images/headimg");
		  System.out.println(savePath);
		  String filename = "";
			 //消息提示
			 String message = "";
		  try{
			     //使用Apache文件上传组件处理文件上传步骤：
			     //1、创建一个DiskFileItemFactory工厂
			     DiskFileItemFactory factory = new DiskFileItemFactory();
			     //2、创建一个文件上传解析器
			     ServletFileUpload upload = new ServletFileUpload(factory);
			      //解决上传文件名的中文乱码
			     upload.setHeaderEncoding("UTF-8"); 
			     //3、判断提交上来的数据是否是上传表单的数据
			     if(!ServletFileUpload.isMultipartContent(request)){
			         //按照传统方式获取数据
			         return;
			     }
			     //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			     List<FileItem> list = upload.parseRequest(request);
			     for(FileItem item : list){
			         //如果fileitem中封装的是普通输入项的数据
			         if(item.isFormField()){
			             String name = item.getFieldName();
			             //解决普通输入项的数据的中文乱码问题
			             String value = item.getString("UTF-8");
			             //value = new String(value.getBytes("iso8859-1"),"UTF-8");
			             System.out.println(name + "=" + value);
			         }else{//如果fileitem中封装的是上传文件
			             //得到上传的文件名称，
			             /*String postfix = item.getName().substring(item.getName().lastIndexOf("."));
			             System.out.println(postfix);
			        	 filename=name+postfix;  徐sir
			             System.out.println("上传的文件名"+filename);*/
			        	  filename = item.getName();
				           System.out.println(filename);
				           
				           
				          /* 将头像名存储到数据库中*/
				           UserDao user=new UserDaoImpl();
				           int uid=0;
				           uid = ((User) request.getSession().getAttribute("userinfo")).getUid();
				           user.uphead(filename, uid);
				           
				           
				           
			             if(filename==null || filename.trim().equals("")){
			                 continue;
			             }
			             //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
			             //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
			             filename = filename.substring(filename.lastIndexOf("\\")+1);
			             //获取item中的上传文件的输入流
			             InputStream in = item.getInputStream();
			             
			             /*response.setContentType("image/*");//徐sir创建输出格式
	*/		             
			             //创建一个文件输出流
			             FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
			             //创建一个缓冲区
			             byte buffer[] = new byte[1024];
			             //判断输入流中的数据是否已经读完的标识
			             int len = 0;
			             //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
			             while((len=in.read(buffer))>0){
			                 //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
			                 out.write(buffer, 0, len);
			             }
			             //关闭输入流
			             in.close();
			             //关闭输出流
			             out.close();
			            
			             //删除处理文件上传时生成的临时文件
			             item.delete();
			             message = "文件上传成功！";
			             System.out.println(message);
			         }
			     }
			 }catch (Exception e) {
			     message= "文件上传失败！";
			     System.out.println(message);
			     e.printStackTrace();
			     
			 }
	
	  }
  
	  public void getheadimg(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		     /* 通过uid获取头像名*/
		  
			  UserDao user=new UserDaoImpl();
			   int uid=0;
			  uid= (int) request.getSession().getAttribute("uid");

			  String fileName=user.gethead(uid);
			  System.out.println("UserServlet："+fileName);
		  
		  
		  
		  
	    
	        //上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
	      /*  String fileSaveRootPath=this.getServletContext().getRealPath("/WEB-INF/images");*/
		 
	        String path=this.getServletContext().getRealPath("/WEB-INF/images/headimg/");
	        String pathname=path+"\\"+fileName;
	        System.out.println("UserServletpathname:"+pathname);
	        File file = new File(path);
	        //如果文件不存在
	        if(!file.exists()){
	            request.setAttribute("message", "您要下载的资源已被删除！！");
	            request.getRequestDispatcher("/index.jsp").forward(request, response);
	            return;
	        }
	        
	        //设置响应头，控制浏览器下载该文件
	        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
	      /* response.setContentType("image/*");
	        OutputStream toClient=response.getOutputStream();*/
	        //读取要下载的文件，保存到文件输入流
	        FileInputStream in = new FileInputStream(pathname);
	        //创建输出流
	        OutputStream out = response.getOutputStream();
	        //创建缓冲区
	        byte buffer[] = new byte[1024];
	        int len = 0;
	        //循环将输入流中的内容读取到缓冲区当中
	        while((len=in.read(buffer))>0){
	            //输出缓冲区的内容到浏览器，实现文件下载
	            out.write(buffer, 0, len);
	        }
	        //关闭文件输入流
	        in.close();
	        //关闭输出流
	        out.close();
		  
	  }
	  
	
	  
	  public void alterinfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		    String nick=request.getParameter("nick");
			String sex=request.getParameter("sex");
			String address=request.getParameter("address");
			String sign=request.getParameter("sign");
			int uid = 0;
			try {
				uid = ((User) request.getSession().getAttribute("userinfo")).getUid();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(uid==0){
				PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('更新错误，请重新登陆!!')");	
				out.println("history.back();");
				out.println("</script>");
				out.println("</head>");
				out.println("<body>");	
				out.println("</body>");
				out.println("</html>");
			}
			System.out.println("Alterinfo.java："+uid+nick+sex+address+sign);
			User user=new User();
			user.setUid(uid);
			user.setAddress(address);
			user.setSex(sex);
			user.setNick(nick);
			user.setSign(sign);
			
			UserDao aluser=new UserDaoImpl();
			if(aluser.alertinfo(user)){
				request.getSession().setAttribute("userinfo",user);
				PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('更新信息成功!!')");	
				out.println("history.back();");
				out.println("</script>");
				out.println("</head>");
				out.println("<body>");	
				out.println("</body>");
				out.println("</html>");
			}else{
				PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('请先登陆!!')");	
				out.println("history.back();");
				out.println("</script>");
				out.println("</head>");
				out.println("<body>");	
				out.println("</body>");
				out.println("</html>");
			}
	  }
	  
	  public void spunch(HttpServletRequest request, HttpServletResponse response){
		  int sum= Integer.parseInt(request.getParameter("sum"));
		  int uid=(int) request.getSession().getAttribute("uid");
		  UserDao us=new UserDaoImpl();
		  
		  us.punch(uid, sum);
		  System.out.println("打卡成功！"+uid+"连续打卡"+sum);
	  }
	  
	  public void dopost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		  String forum=request.getParameter("class");
		  String subjects=request.getParameter("title");
		  String content=request.getParameter("content");
		  Date date=new Date();
		  DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  int uid=(int) request.getSession().getAttribute("uid");
		  Post post =new Post();
		  post.setForum(forum);
		  post.setSubjects(subjects);
		  post.setContent(content);
		  post.setDate(fmt.format(date));
		  post.setUid(uid);
		  
		  //session获取post头像名
		  String postimg=(String) request.getSession().getAttribute("postimg");
		  post.setPostimg(postimg);
		  request.getSession().removeAttribute("postimg");
		  
		  UserDao us=new UserDaoImpl();
		  if(us.dopost(post)){
			    PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('发帖成功')");	
				out.println("window.location.href = 'http://localhost:8090/UAV/';");
				out.println("</script>");
		  }else{
			    PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('发帖失败')");	
				out.println("window.location.href = 'http://localhost:8090/UAV/';");
				out.println("</script>");
		  };
		  
	  }
	  /*ajax获取我的帖子模块数据*/
	  public void mypost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  int uid=(int) request.getSession().getAttribute("uid");
		  UserDao us=new UserDaoImpl();
		  List<Post> list=new ArrayList<Post>();
		  list=us.mypost(uid);
		  JSONArray json=JSONArray.fromObject(list);
		  response.setContentType("application/json;charset=utf-8");
		  System.out.println(json);
		  response.getWriter().print(json);
	  }
	  
	  /*获取用户帖子的详细信息*/
	  public void detailpost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		  String pid=request.getParameter("pid");
		  String where=request.getParameter("where");
		  UserDao us=new UserDaoImpl();
		  List list=new ArrayList();
		 
		  BaseDao bs =new BaseDaoImpl();
		  int count=bs.disscount(pid);
		  request.setAttribute("count", count);
		  
		  list=us.detailpost(pid);
		  JSONArray json=JSONArray.fromObject(list);
		  request.setAttribute("detailpost", list);
		 

		  if(where.equals("alter")){
			  request.getRequestDispatcher("WEB-INF/page/alterpost.jsp").forward(request, response);
		  }else if(where.equals("detail")){
		      request.getRequestDispatcher("WEB-INF/page/seepost.jsp").forward(request, response);}
	  }

	  public void querypost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		  String key=request.getParameter("key");
		  
		  if(key.equals("0")){
		
			  String keyword=request.getParameter("keyword");//获取前端传递的参数
			  List list =new ArrayList<Post>();//创建list集合用于存储搜索结果集
			  UserDao us=new UserDaoImpl();//创建数据访问层对象
			  list=us.querypost(keyword);//执行搜索帖子方法
			  JSONArray json=JSONArray.fromObject(list);//将搜索结果转换成JSON
			  request.setAttribute("post", json);//将搜索结果保存到request域
			  request.getRequestDispatcher("WEB-INF/page/google.jsp").forward(request, response);//请求转发到新页面
		  }
		  else{
			  String keyword=request.getParameter("keyword");
			  List<User> list =new ArrayList<User>();
			 UserDao us=new UserDaoImpl();
			 list=us.queryuser(keyword);
			 JSONArray json=JSONArray.fromObject(list);
			 request.setAttribute("user", json);
			 request.getRequestDispatcher("WEB-INF/page/google.jsp").forward(request, response);
			 
		  }
		 
		  
	  }
	  public void doposts(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		  /*
		   *获取前端传递的板块名、主题、内容等参数 
		   */
		  String forum=request.getParameter("class");//获取板块名
		  String subjects=request.getParameter("title");//获取主题
		  String content=request.getParameter("content");//获取内容
		  Date date=new Date();
		  DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  int uid=(int) request.getSession().getAttribute("uid");//获取当前发帖人id
		  Post post =new Post();//创建帖子实体类
		  /*
		   * 创建post实体类，将传递的参数封装到实体类
		   */
		  post.setForum(forum);
		  post.setSubjects(subjects);
		  post.setContent(content);
		  post.setDate(fmt.format(date));
		  post.setUid(uid);
		  
		  //session获取post路径名
		  String postimg=(String) request.getSession().getAttribute("pathname");
		  System.out.println(postimg);
		  System.out.println(request.getSession().getAttribute("pathname"));
		  post.setPostimg(postimg);
		  request.getSession().removeAttribute("pathname");
		  
		  /*
		   * 判断数据库执行结果
		   */
		  UserDao us=new UserDaoImpl();
		  if(us.dopost(post)){
			  JSONObject resObj = new JSONObject();
			  resObj.put("msg", "发帖成功！");
			  response.setContentType("application/json;charset=utf-8");
			  response.getWriter().write(resObj.toString());
			  request.getRequestDispatcher("/index.jsp").forward(request, response);
		  }else{
			  JSONObject resObj = new JSONObject();
			  resObj.put("msg", "发帖失败！");
			  response.setContentType("application/json;charset=utf-8");
			  response.getWriter().write(resObj.toString());
			  request.getRequestDispatcher("/fatie.html").forward(request, response);
		  };
		  
	  }
	  
	
	 
	  //增加评论
	  public void addpost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		  String discuss=request.getParameter("content");
		  int  pid=Integer.parseInt(request.getParameter("pid"));
		  Date date=new Date();
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  String time= format.format(date);
		  int uid=(int) (request.getSession().getAttribute("uid"));
		  UserDao us=new UserDaoImpl();
		  us.adddiscuss(uid, discuss, pid, time);
		  JSONObject resObj = new JSONObject();
		  resObj.put("msg", "发帖失败！");
		  response.setContentType("application/json;charset=utf-8");
		  response.getWriter().write(resObj.toString());
		  request.getRequestDispatcher("UserServlet?method=detailpost&pid="+pid+"&where=detail").forward(request, response);
		  
	  }
	  public void mydiscuss(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  UserDao us=new UserDaoImpl();
		  String pid=request.getParameter("pid");
	      List list=new ArrayList();
	      list=us.seediscuss(pid);
	      BaseDao bs=new BaseDaoImpl();
   
	      int count=bs.disscount(pid);
		   int pages=0;
		  if(count%4==0){
			  pages=count/4;
		  }else{
			  pages=count/4+1;
		  }
		  response.setContentType("application/json;charset=utf-8");
		  JSONObject json=JSONObject.fromObject(LayuiCode.data(count, list));
		  System.out.println(LayuiCode.data(count, list));
		  response.getWriter().print(json);
	  }
	  public void alterpost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		  String forum=request.getParameter("class");
		  String subjects=request.getParameter("title");
		  String content=request.getParameter("content");
		  String pid=request.getParameter("pid");
		  int uid=(int) request.getSession().getAttribute("uid");
		  Post post =new Post();
		  post.setForum(forum);
		  post.setSubjects(subjects);
		  post.setContent(content);
		  post.setUid(uid);
		  
		  //session获取post头像名
		  request.getSession().removeAttribute("postimg");
		  
		  UserDao us=new UserDaoImpl();
		  if(us.alterpost(post, pid)){
			  JSONObject resObj = new JSONObject();
			  resObj.put("msg", "修改帖子成功！");
			  response.setContentType("application/json;charset=utf-8");
			  response.getWriter().write(resObj.toString());
			  request.getRequestDispatcher("UserServlet?method=detailpost&pid="+pid+"&where=detail").forward(request, response);
		  }else{
			  JSONObject resObj = new JSONObject();
			  resObj.put("msg", "修改失败！");
			  response.setContentType("application/json;charset=utf-8");
			  response.getWriter().write(resObj.toString());
			  request.getRequestDispatcher("UserServlet?method=detailpost&pid="+pid+"&where=alter").forward(request, response);
		  };
	  }
	  //收藏表
	  public void collect(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uid=request.getParameter("uid");
		String pid=request.getParameter("pid");
		UserDao us=new UserDaoImpl();
		us.collect(pid, uid);
		response.setContentType("text/html");
		response.getWriter().print("收藏成功");
	  }
	  //查询收藏表
	  public void mycoll(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String uid=request.getParameter("uid");
		  UserDao us=new UserDaoImpl();
		  List<Post> list=new ArrayList<Post>();
		  list=us.mycoll(uid);
		  
		  JSONArray json=JSONArray.fromObject(list);
		  response.setContentType("application/json;charset=utf-8");
		  System.out.println(json);
		  response.getWriter().print(json);
	  }
	  //点赞
	  public void like(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String pid=request.getParameter("pid");
		  UserDao us=new UserDaoImpl();
		  us.like(pid);
		  String msg="点赞成功";
		  response.getWriter().print(msg);
		  
	  }
	  //查看其他用户的个人主页与发的帖子
	  public void otherinfo(HttpServletRequest request, HttpServletResponse response) throws IOException{  
		  String uid=request.getParameter("uid");
		  UserDao ud=new UserDaoImpl();
		  List<User> us=new ArrayList<User>();
		  us=ud.otherpage(uid);
		  JSONArray user=JSONArray.fromObject(us);
		  response.setContentType("application/json;charset=utf-8");
		  System.out.println(user);
		  response.getWriter().print(user);
		  
	  }
	  public void otherpost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String uid=request.getParameter("uid");
		  UserDao ud=new UserDaoImpl();
		  List<Post> ps=new ArrayList<Post>();
		  ps=ud.otherpost(uid);
		  
		  JSONArray post=JSONArray.fromObject(ps);
		  response.setContentType("application/json;charset=utf-8");  
		  System.out.println(post);
		  response.getWriter().print(post);
	  }
	  public void addfri(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String fid=request.getParameter("fid");
		  int uid=(int) request.getSession().getAttribute("uid");
		  System.out.println(uid);
		  UserDao us=new UserDaoImpl();
		  if(us.addfri(fid, uid)){
			  response.getWriter().print("添加成功");
		  }else{
			  response.getWriter().print("添加失败");
		  }
	  }
	  public void myfriend(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  int uid=(int) request.getSession().getAttribute("uid");
		  UserDao us=new UserDaoImpl();
		  List<User> list=new ArrayList<User>();
		  list= us.myfri(uid);
		  JSONArray json=JSONArray.fromObject(list);
		  response.setContentType("application/json;charset=utf-8");  
		  System.out.println(json);
		  response.getWriter().print(json);
		  
	  }
	  public void mess(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  int fromid=(int) request.getSession().getAttribute("uid");
		  int toid=Integer.parseInt(request.getParameter("toid"));
		  String content=request.getParameter("content");
		  Date date=new Date();
		  DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Message msg=new Message();
		  msg.setContent(content);
		  msg.setDate(fmt.format(date));
		  msg.setFromid(fromid);
		  msg.setToid(toid);
		  UserDao us=new UserDaoImpl();
		  if( us.mess(msg)){
			    response.getWriter().print("发送成功");

		  }else{
			  response.getWriter().print("发送失败");
		  };
	  }
	  public void newmess(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  int toid=(int) request.getSession().getAttribute("uid");
		  UserDao us=new UserDaoImpl();
		  List<Message> list=new ArrayList<Message>();
		  list=us.newmess(toid);
		  
		  //更新为已读
		  if(us.readed(toid)){
			  System.out.println("更新已读成功");
		  }
		  
		  JSONArray json=JSONArray.fromObject(list);
		  response.setContentType("application/json;charset=utf-8");
		  System.out.println(json);
		  response.getWriter().print(json);
	  }
	  public void myemail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		  int uid=(int)request.getSession().getAttribute("uid");
		  UserDao us=new UserDaoImpl();
		  String myemail=null;
		  myemail=us.myemail(uid);
		  request.setAttribute("myemail", myemail);
		  request.getRequestDispatcher("WEB-INF/page/safe.jsp").forward(request, response);
	  }
	  public void modif(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		  String mycode=request.getParameter("vercode");
		  String code=(String) request.getSession().getAttribute("vercode");
		  if(mycode.equals(code)){
			  System.out.println("邮箱验证通过");
			  request.getSession().removeAttribute("vercode");
			  request.getRequestDispatcher("WEB-INF/page/modif.html").forward(request, response);
		  }else{
				PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('验证码错误，请重新验证!!')");	
				out.println("history.back();");
				out.println("</script>");
				out.println("</head>");
				out.println("<body>");	
				out.println("</body>");
				out.println("</html>");
		  }
	  }
	  
	  public void modifypass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			String pass=request.getParameter("password");
			String code=request.getParameter("inputCode");
			String vercode=(String) request.getSession().getAttribute("vacode");
			int uid=(int) request.getSession().getAttribute("uid");
			if(code.equals(vercode)){
				UserDao us=new UserDaoImpl();
				us.modify(uid, pass);
				request.getRequestDispatcher("login.html").forward(request, response);
			}else{
				response.setCharacterEncoding("UTF-8");
	            response.setHeader("Content-Type","text/html;charset=utf-8");
				PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('抱歉出BUG了!!')");	
				out.println("history.back();");
				out.println("</script>");
				out.println("</head>");
				out.println("<body>");	
				out.println("</body>");
				out.println("</html>");
			}
	  }
	  public void bandmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		  String code=request.getParameter("vercode");
		  String mail=request.getParameter("email");
		  String vercode=(String) request.getSession().getAttribute("vercode");
		  if(code.equals(vercode)){
			  int uid=(int) request.getSession().getAttribute("uid");
			  UserDao us=new UserDaoImpl();
			  if(us.bandmail(uid, mail)){
				  request.getRequestDispatcher("WEB-INF/page/safe.jsp").forward(request, response);
			  }else{
				  System.out.println("未知BUG");
			  };
		  }else{
			    PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('验证码错误!!')");	
				out.println("history.back();");
				out.println("</script>");
				out.println("</head>");
				out.println("<body>");	
				out.println("</body>");
				out.println("</html>");
		  }
		  request.getSession().removeAttribute("vercode");
		 
	  }
	  public void cancelmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		  String code=request.getParameter("vercode");	  
		  String vercode=(String) request.getSession().getAttribute("vercode");		
		  if(code.equals(vercode)){
			  int uid=(int)request.getSession().getAttribute("uid");
			  UserDao us=new UserDaoImpl();
			  if(us.cancelmail(uid)){
				  
				  request.getRequestDispatcher("WEB-INF/page/safe.jsp").forward(request, response);
			  }else{
				  System.out.println("未知BUG");
			  }
		  }else{
			    PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('验证码错误!!')");	
				out.println("history.back();");
				out.println("</script>");
				out.println("</head>");
				out.println("<body>");	
				out.println("</body>");
				out.println("</html>");
		  }

		  request.getSession().removeAttribute("vercode");
	  }
	  public void delpost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String pid=request.getParameter("pid");
		  AdminDao ad=new AdminDaoImpl();
		  if(ad.delpost(pid)){
			  PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('删除成功!!')");	
				out.println("history.back();");
				out.println("</script>");
		  }
	  }
	  public void delcoll(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String pid=request.getParameter("pid");
		  UserDao us=new UserDaoImpl();
		  int uid=(int) request.getSession().getAttribute("uid");
		  if(us.cancelcoll(pid, uid)){
			  PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('取消收藏成功!!')");	
				out.println("history.back();");
				out.println("</script>");
		  }  
	  }
	  public void delfri(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String fid=request.getParameter("fid");
		  int uid=(int)request.getSession().getAttribute("uid");
		  UserDao us=new UserDaoImpl();
		  if( us.delfri(fid, uid)){
			  response.getWriter().print("好友删除成功");
		  }else{
			  response.getWriter().print("出了BUG不让删");
		  }
	  }
	  public void apply(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String forum=request.getParameter("forum");
		  String name=request.getParameter("title");
		  String reason=request.getParameter("content");
		  int uid=(int)request.getSession().getAttribute("uid");
		  Date mydate=new Date();
		  DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String date=fmt.format(mydate);
		  Apply ap=new Apply();
		  ap.setDate(date);
		  ap.setName(name);
		  ap.setReason(reason);
		  ap.setUid(uid);
		  ap.setForum(forum);
		  BaseDao bs=new BaseDaoImpl();
		  bs.apply(ap);
		  PrintWriter out=response.getWriter();
			out.println("<meta charset='utf-8'>");
			out.println("<script>");		
			out.println("alert('申请成功!!请静候佳音')");	
			out.println("window.location.href = 'http://localhost:8090/UAV/';");
			out.println("</script>");
	  }
	 
	public void logins(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		    String user=request.getParameter("username");
			String pass=request.getParameter("password");	
			UserDao us=new UserDaoImpl();
			String uauth=us.logins(user, pass);
			if(uauth.equals("false")){
			    PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('用户名或密码错误')");	
				out.println("window.location.href = 'http://localhost:8090/UAV/';");
				out.println("</script>");
	     	}else if(uauth.equals("none")){
				  PrintWriter out=response.getWriter();
					out.println("<meta charset='utf-8'>");
					out.println("<script>");		
					out.println("alert('用户账号已被封')");	
					out.println("window.location.href = 'http://localhost:8090/UAV/';");
					out.println("</script>");
				 
			}else {
				BaseDao uinfo=new BaseDaoImpl();
				User userinfo=new User();
				userinfo=uinfo.setSession(user);
				request.getSession().setAttribute("auth",uauth);
				request.getSession().setAttribute("userinfo", userinfo);
				request.getSession().setAttribute("uid", userinfo.getUid());
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}	
	  }
	public void upwork(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int uid=(int)request.getSession().getAttribute("uid");
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String workpath=(String) request.getSession().getAttribute("workpath");
		request.getSession().removeAttribute("workpath");
		Date date=new Date();
		DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate=fmt.format(date);
		Work wk=new Work();
		wk.setContent(content);
		wk.setDate(mydate);
		wk.setTitle(title);
		wk.setWorkpath(workpath);
		wk.setUid(uid);
		UserDao us=new UserDaoImpl();
		if(us.upwork(wk)){
			response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type","text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset='utf-8'>");
			out.println("<script>");		
			out.println("alert('作品上传成功!!')");	
			out.println("window.location.href = 'http://localhost:8090/UAV/index.jsp';");
			out.println("</script>");
		};
	}
	public void seework(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id=request.getParameter("id");
		List list=new ArrayList<Work>();
		UserDao us=new UserDaoImpl();
		list=us.seework(id);
		request.setAttribute("detailwork", list);
		request.getRequestDispatcher("WEB-INF/page/seework.jsp").forward(request, response);
	}
	public void report(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String did=request.getParameter("did");
		UserDao us=new UserDaoImpl();
		if(us.report(did)){
			response.getWriter().print("举报成功");
		}
	}
	public void seeteach(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int tid=Integer.parseInt(request.getParameter("tid"));
		UserDao us=new UserDaoImpl();
		Post ps=new Post();
		ps=us.seeteach(tid);
		request.setAttribute("detteach", ps);
		request.getRequestDispatcher("WEB-INF/page/seeteach.jsp").forward(request, response);
	}
	public void findpass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String username=request.getParameter("username");
		UserDao us=new UserDaoImpl();
		User user=new User();
		user=us.findpass(username);
		request.getSession().setAttribute("uid", user.getUid());
		request.setAttribute("user", user);
		request.getRequestDispatcher("/findsecs.jsp").forward(request, response);
	}
}
