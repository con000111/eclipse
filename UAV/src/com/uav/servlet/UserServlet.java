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
			System.out.println("�û�����"+user+"���룺"+pass);
			UserDao us=new UserDaoImpl();
			if(us.login(user, pass)){
				//������Ҫ����ĵ�½��Ϣ
				BaseDao uinfo=new BaseDaoImpl();
				User userinfo=new User();
				userinfo=uinfo.setSession(user);
				/*HttpSession session=request.getSession();
				session.setAttribute("userinfo",userinfo );*///�ҵ������´��淽ʽ
				request.getSession().setAttribute("userinfo", userinfo);
				request.getSession().setAttribute("uid", userinfo.getUid());
				Punch punch=new Punch();
				int uid=0;
				int sum=1;
				uid=(int) request.getSession().getAttribute("uid");
				punch=us.record(uid);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date=new Date();	
				/*System.out.println("ʱ��"+(date.getTime()-punch.getPunchtime().getTime()) / (24 * 60 * 60 * 1000));*/
		       request.setAttribute("count", punch.getCount());//������
				if((date.getTime()-punch.getPunchtime().getTime()) / (24 * 60 * 60 * 1000)<1 ){
		        	request.setAttribute("punch", "yes");
		        	System.out.println("�������");
		        	sum=punch.getSum();
		        }else {
		        	request.setAttribute("punch", "no");
		        	if(2>=(date.getTime()-punch.getPunchtime().getTime()) / (24 * 60 * 60 * 1000)){
		        		sum=punch.getSum()+1;	
		        		System.out.println("sum��һ��");
		        	}
		        	System.out.println("����û��");
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
				out.println("alert('�˺Ż��������!!')");	
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
					request.getRequestDispatcher("/login.html").forward(request, response);//��ת����½
				}else{
					response.setCharacterEncoding("UTF-8");
		            response.setHeader("Content-Type","text/html;charset=utf-8");
					PrintWriter out=response.getWriter();
					out.println("<html>");
					out.println("<head>");
					out.println("<meta charset='utf-8'>");
					out.println("<script>");		
					out.println("alert('ע��ʧ�ܣ�������һ�ΰ�!!')");	
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
				out.println("alert('��֤�����!!')");	
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
			 //��Ϣ��ʾ
			 String message = "";
		  try{
			     //ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
			     //1������һ��DiskFileItemFactory����
			     DiskFileItemFactory factory = new DiskFileItemFactory();
			     //2������һ���ļ��ϴ�������
			     ServletFileUpload upload = new ServletFileUpload(factory);
			      //����ϴ��ļ�������������
			     upload.setHeaderEncoding("UTF-8"); 
			     //3���ж��ύ�����������Ƿ����ϴ���������
			     if(!ServletFileUpload.isMultipartContent(request)){
			         //���մ�ͳ��ʽ��ȡ����
			         return;
			     }
			     //4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
			     List<FileItem> list = upload.parseRequest(request);
			     for(FileItem item : list){
			         //���fileitem�з�װ������ͨ�����������
			         if(item.isFormField()){
			             String name = item.getFieldName();
			             //�����ͨ����������ݵ�������������
			             String value = item.getString("UTF-8");
			             //value = new String(value.getBytes("iso8859-1"),"UTF-8");
			             System.out.println(name + "=" + value);
			         }else{//���fileitem�з�װ�����ϴ��ļ�
			             //�õ��ϴ����ļ����ƣ�
			             /*String postfix = item.getName().substring(item.getName().lastIndexOf("."));
			             System.out.println(postfix);
			        	 filename=name+postfix;  ��sir
			             System.out.println("�ϴ����ļ���"+filename);*/
			        	  filename = item.getName();
				           System.out.println(filename);
				           
				           
				          /* ��ͷ�����洢�����ݿ���*/
				           UserDao user=new UserDaoImpl();
				           int uid=0;
				           uid = ((User) request.getSession().getAttribute("userinfo")).getUid();
				           user.uphead(filename, uid);
				           
				           
				           
			             if(filename==null || filename.trim().equals("")){
			                 continue;
			             }
			             //ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
			             //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
			             filename = filename.substring(filename.lastIndexOf("\\")+1);
			             //��ȡitem�е��ϴ��ļ���������
			             InputStream in = item.getInputStream();
			             
			             /*response.setContentType("image/*");//��sir���������ʽ
	*/		             
			             //����һ���ļ������
			             FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
			             //����һ��������
			             byte buffer[] = new byte[1024];
			             //�ж��������е������Ƿ��Ѿ�����ı�ʶ
			             int len = 0;
			             //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
			             while((len=in.read(buffer))>0){
			                 //ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
			                 out.write(buffer, 0, len);
			             }
			             //�ر�������
			             in.close();
			             //�ر������
			             out.close();
			            
			             //ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
			             item.delete();
			             message = "�ļ��ϴ��ɹ���";
			             System.out.println(message);
			         }
			     }
			 }catch (Exception e) {
			     message= "�ļ��ϴ�ʧ�ܣ�";
			     System.out.println(message);
			     e.printStackTrace();
			     
			 }
	
	  }
  
	  public void getheadimg(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		     /* ͨ��uid��ȡͷ����*/
		  
			  UserDao user=new UserDaoImpl();
			   int uid=0;
			  uid= (int) request.getSession().getAttribute("uid");

			  String fileName=user.gethead(uid);
			  System.out.println("UserServlet��"+fileName);
		  
		  
		  
		  
	    
	        //�ϴ����ļ����Ǳ�����/WEB-INF/uploadĿ¼�µ���Ŀ¼����
	      /*  String fileSaveRootPath=this.getServletContext().getRealPath("/WEB-INF/images");*/
		 
	        String path=this.getServletContext().getRealPath("/WEB-INF/images/headimg/");
	        String pathname=path+"\\"+fileName;
	        System.out.println("UserServletpathname:"+pathname);
	        File file = new File(path);
	        //����ļ�������
	        if(!file.exists()){
	            request.setAttribute("message", "��Ҫ���ص���Դ�ѱ�ɾ������");
	            request.getRequestDispatcher("/index.jsp").forward(request, response);
	            return;
	        }
	        
	        //������Ӧͷ��������������ظ��ļ�
	        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
	      /* response.setContentType("image/*");
	        OutputStream toClient=response.getOutputStream();*/
	        //��ȡҪ���ص��ļ������浽�ļ�������
	        FileInputStream in = new FileInputStream(pathname);
	        //���������
	        OutputStream out = response.getOutputStream();
	        //����������
	        byte buffer[] = new byte[1024];
	        int len = 0;
	        //ѭ�����������е����ݶ�ȡ������������
	        while((len=in.read(buffer))>0){
	            //��������������ݵ��������ʵ���ļ�����
	            out.write(buffer, 0, len);
	        }
	        //�ر��ļ�������
	        in.close();
	        //�ر������
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
				out.println("alert('���´��������µ�½!!')");	
				out.println("history.back();");
				out.println("</script>");
				out.println("</head>");
				out.println("<body>");	
				out.println("</body>");
				out.println("</html>");
			}
			System.out.println("Alterinfo.java��"+uid+nick+sex+address+sign);
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
				out.println("alert('������Ϣ�ɹ�!!')");	
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
				out.println("alert('���ȵ�½!!')");	
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
		  System.out.println("�򿨳ɹ���"+uid+"������"+sum);
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
		  
		  //session��ȡpostͷ����
		  String postimg=(String) request.getSession().getAttribute("postimg");
		  post.setPostimg(postimg);
		  request.getSession().removeAttribute("postimg");
		  
		  UserDao us=new UserDaoImpl();
		  if(us.dopost(post)){
			    PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('�����ɹ�')");	
				out.println("window.location.href = 'http://localhost:8090/UAV/';");
				out.println("</script>");
		  }else{
			    PrintWriter out=response.getWriter();
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('����ʧ��')");	
				out.println("window.location.href = 'http://localhost:8090/UAV/';");
				out.println("</script>");
		  };
		  
	  }
	  /*ajax��ȡ�ҵ�����ģ������*/
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
	  
	  /*��ȡ�û����ӵ���ϸ��Ϣ*/
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
		
			  String keyword=request.getParameter("keyword");//��ȡǰ�˴��ݵĲ���
			  List list =new ArrayList<Post>();//����list�������ڴ洢���������
			  UserDao us=new UserDaoImpl();//�������ݷ��ʲ����
			  list=us.querypost(keyword);//ִ���������ӷ���
			  JSONArray json=JSONArray.fromObject(list);//���������ת����JSON
			  request.setAttribute("post", json);//������������浽request��
			  request.getRequestDispatcher("WEB-INF/page/google.jsp").forward(request, response);//����ת������ҳ��
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
		   *��ȡǰ�˴��ݵİ���������⡢���ݵȲ��� 
		   */
		  String forum=request.getParameter("class");//��ȡ�����
		  String subjects=request.getParameter("title");//��ȡ����
		  String content=request.getParameter("content");//��ȡ����
		  Date date=new Date();
		  DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  int uid=(int) request.getSession().getAttribute("uid");//��ȡ��ǰ������id
		  Post post =new Post();//��������ʵ����
		  /*
		   * ����postʵ���࣬�����ݵĲ�����װ��ʵ����
		   */
		  post.setForum(forum);
		  post.setSubjects(subjects);
		  post.setContent(content);
		  post.setDate(fmt.format(date));
		  post.setUid(uid);
		  
		  //session��ȡpost·����
		  String postimg=(String) request.getSession().getAttribute("pathname");
		  System.out.println(postimg);
		  System.out.println(request.getSession().getAttribute("pathname"));
		  post.setPostimg(postimg);
		  request.getSession().removeAttribute("pathname");
		  
		  /*
		   * �ж����ݿ�ִ�н��
		   */
		  UserDao us=new UserDaoImpl();
		  if(us.dopost(post)){
			  JSONObject resObj = new JSONObject();
			  resObj.put("msg", "�����ɹ���");
			  response.setContentType("application/json;charset=utf-8");
			  response.getWriter().write(resObj.toString());
			  request.getRequestDispatcher("/index.jsp").forward(request, response);
		  }else{
			  JSONObject resObj = new JSONObject();
			  resObj.put("msg", "����ʧ�ܣ�");
			  response.setContentType("application/json;charset=utf-8");
			  response.getWriter().write(resObj.toString());
			  request.getRequestDispatcher("/fatie.html").forward(request, response);
		  };
		  
	  }
	  
	
	 
	  //��������
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
		  resObj.put("msg", "����ʧ�ܣ�");
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
		  
		  //session��ȡpostͷ����
		  request.getSession().removeAttribute("postimg");
		  
		  UserDao us=new UserDaoImpl();
		  if(us.alterpost(post, pid)){
			  JSONObject resObj = new JSONObject();
			  resObj.put("msg", "�޸����ӳɹ���");
			  response.setContentType("application/json;charset=utf-8");
			  response.getWriter().write(resObj.toString());
			  request.getRequestDispatcher("UserServlet?method=detailpost&pid="+pid+"&where=detail").forward(request, response);
		  }else{
			  JSONObject resObj = new JSONObject();
			  resObj.put("msg", "�޸�ʧ�ܣ�");
			  response.setContentType("application/json;charset=utf-8");
			  response.getWriter().write(resObj.toString());
			  request.getRequestDispatcher("UserServlet?method=detailpost&pid="+pid+"&where=alter").forward(request, response);
		  };
	  }
	  //�ղر�
	  public void collect(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uid=request.getParameter("uid");
		String pid=request.getParameter("pid");
		UserDao us=new UserDaoImpl();
		us.collect(pid, uid);
		response.setContentType("text/html");
		response.getWriter().print("�ղسɹ�");
	  }
	  //��ѯ�ղر�
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
	  //����
	  public void like(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String pid=request.getParameter("pid");
		  UserDao us=new UserDaoImpl();
		  us.like(pid);
		  String msg="���޳ɹ�";
		  response.getWriter().print(msg);
		  
	  }
	  //�鿴�����û��ĸ�����ҳ�뷢������
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
			  response.getWriter().print("��ӳɹ�");
		  }else{
			  response.getWriter().print("���ʧ��");
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
			    response.getWriter().print("���ͳɹ�");

		  }else{
			  response.getWriter().print("����ʧ��");
		  };
	  }
	  public void newmess(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  int toid=(int) request.getSession().getAttribute("uid");
		  UserDao us=new UserDaoImpl();
		  List<Message> list=new ArrayList<Message>();
		  list=us.newmess(toid);
		  
		  //����Ϊ�Ѷ�
		  if(us.readed(toid)){
			  System.out.println("�����Ѷ��ɹ�");
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
			  System.out.println("������֤ͨ��");
			  request.getSession().removeAttribute("vercode");
			  request.getRequestDispatcher("WEB-INF/page/modif.html").forward(request, response);
		  }else{
				PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('��֤�������������֤!!')");	
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
				out.println("alert('��Ǹ��BUG��!!')");	
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
				  System.out.println("δ֪BUG");
			  };
		  }else{
			    PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('��֤�����!!')");	
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
				  System.out.println("δ֪BUG");
			  }
		  }else{
			    PrintWriter out=response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='utf-8'>");
				out.println("<script>");		
				out.println("alert('��֤�����!!')");	
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
				out.println("alert('ɾ���ɹ�!!')");	
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
				out.println("alert('ȡ���ղسɹ�!!')");	
				out.println("history.back();");
				out.println("</script>");
		  }  
	  }
	  public void delfri(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String fid=request.getParameter("fid");
		  int uid=(int)request.getSession().getAttribute("uid");
		  UserDao us=new UserDaoImpl();
		  if( us.delfri(fid, uid)){
			  response.getWriter().print("����ɾ���ɹ�");
		  }else{
			  response.getWriter().print("����BUG����ɾ");
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
			out.println("alert('����ɹ�!!�뾲�����')");	
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
				out.println("alert('�û������������')");	
				out.println("window.location.href = 'http://localhost:8090/UAV/';");
				out.println("</script>");
	     	}else if(uauth.equals("none")){
				  PrintWriter out=response.getWriter();
					out.println("<meta charset='utf-8'>");
					out.println("<script>");		
					out.println("alert('�û��˺��ѱ���')");	
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
			out.println("alert('��Ʒ�ϴ��ɹ�!!')");	
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
			response.getWriter().print("�ٱ��ɹ�");
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
