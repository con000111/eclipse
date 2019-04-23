package com.uav.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.uav.dao.BaseDao;
import com.uav.dao.UserDao;
import com.uav.dao.impl.BaseDaoImpl;
import com.uav.dao.impl.UserDaoImpl;
import com.uav.entity.User;
import com.uav.util.BaseServlet;
import com.uav.util.LayuiCode;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UpAdown
 */
@WebServlet("/UpAdown")
public class UpAdown extends BaseServlet {
	//�ϴ���ƷͼƬ
	public JSONObject uppic(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 String which=request.getParameter("which");
		 String savePath=this.getServletContext().getRealPath("/WEB-INF/images/works");
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
			         return null;
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
			        	  filename = item.getName();
				          /*��sir  �޸�ͼƬ������ ��ͷ�����洢�����ݿ���*/
				           String postfix = item.getName().substring(item.getName().lastIndexOf("."));
				           BaseDao base=new BaseDaoImpl();
				           int uid=0;	
				           uid = ((User) request.getSession().getAttribute("userinfo")).getUid();
				           String myfilename=uid+which+postfix;
				           base.upwork(myfilename, uid);
				           System.out.println("myfilename:"+myfilename);
				           
				           
			             if(filename==null || filename.trim().equals("")){
			                 continue;
			             }
			             //ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
			             //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������

			             //��ȡitem�е��ϴ��ļ���������
			             InputStream in = item.getInputStream();
			             
			             /*response.setContentType("image/*");//��sir���������ʽ
	*/		             
			             //����һ���ļ������
			             FileOutputStream out = new FileOutputStream(savePath + "\\" + myfilename);
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
			     
			 }JSONObject resObj = new JSONObject();
				resObj.put("msg", "ok");
				response.setContentType("application/json;charset=utf-8");
				
				response.getWriter().write(resObj.toString());
				return resObj;
	}
	//ͨ���ļ�����ȡ��Ʒ��ͷ����
	public void downpic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			  String fileName=request.getParameter("filename");
			  
		    
		        //�ϴ����ļ����Ǳ�����/WEB-INF/uploadĿ¼�µ���Ŀ¼����
		      /*  String fileSaveRootPath=this.getServletContext().getRealPath("/WEB-INF/images");*/
			 
		        String path=this.getServletContext().getRealPath("/WEB-INF/images/works/");
		        String pathname=path+"\\"+fileName;
		        System.out.println("UpAdownpathname:"+pathname);
		        File file = new File(path);
		        //����ļ�������
		        if(!file.exists()){
		            request.setAttribute("message", "��Ҫ���ص���Դ�ѱ�ɾ������");
		            request.getRequestDispatcher("/index.jsp").forward(request, response);
		            return;
		        }
		        
		        //������Ӧͷ��������������ظ��ļ�
		        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		        response.setContentType("image/*");
		      /*  OutputStream toClient=response.getOutputStream();*/
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
   //ͨ��ͼƬ����ȡ������û���ͷ��
	public void rankimg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		  String rankimg=request.getParameter("rankimg");
	        String path=this.getServletContext().getRealPath("/WEB-INF/images/headimg/");
	        String pathname=path+"\\"+rankimg;//·��+�ļ���
	        File file = new File(path);
	        //����ļ�������
	        if(!file.exists()){
	            request.setAttribute("message", "��Ҫ���ص���Դ�ѱ�ɾ������");
	            request.getRequestDispatcher("/index.jsp").forward(request, response);
	            return;
	        }
	        
	        //������Ӧͷ��������������ظ��ļ�
	        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(rankimg, "UTF-8"));
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
	//�ϴ�����ͼƬ
	public void uppostimgs(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int uid=(int) request.getSession().getAttribute("uid");
		String path=this.getServletContext().getRealPath("/WEB-INF/images/"+uid+"");
		System.out.println("�Կ��ۣ�"+path);
		
		File myfile=new File(path);
		if (!myfile.exists() && !myfile.isDirectory()) {
		     System.out.println(path+"Ŀ¼�����ڣ���Ҫ����");
		     //����Ŀ¼
		     myfile.mkdir();
		 }
		String savePath=this.getServletContext().getRealPath("/WEB-INF/images/"+uid+"/postimg");
		  System.out.println(savePath);
		  File file = new File(savePath);
			 //�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
			 if (!file.exists() && !file.isDirectory()) {
			     System.out.println(savePath+"Ŀ¼�����ڣ���Ҫ����");
			     //����Ŀ¼
			     file.mkdir();
			 }
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
			         return ;
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
			        	  filename = item.getName();
			        	  System.out.println(filename);
				          /*��sir  �޸�ͼƬ������ ��ͷ�����洢�����ݿ���*/
			        	  if(filename==null || filename.trim().equals("")){
				                 continue;
				             }
			             
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
		  
		    String pathname=savePath + "\\" + filename;	  
		    pathname=pathname.replaceAll("\\\\", "/");
			 System.out.println("pathname:::::"+pathname);
			 request.getSession().setAttribute("pathname", pathname);//�����ϴ�������ͼƬ�����֣��ϴ�������ʱ��һ���ϴ������ݿ�
			Map map=new HashMap();
			map.put("src","UpAdown?method=postimgs&pathname="+pathname+"");


			 pathname=pathname.replaceAll("\\\\", "/");
			 System.out.println("pathname:::::"+pathname);
			 request.getSession().setAttribute("pathname", pathname);//�����ϴ�������ͼƬ�����֣��ϴ�������ʱ��һ���ϴ������ݿ�
			
			 JSONObject jsonsrc = JSONObject.fromObject(map);
			 /*String abc="{"+"\"code\":0"+",\"msg\":\"��sir\""+",\"count\":20"+",\"data\":"+"{"+"\"src\":\"WEB-INF\\\""+"}"+"}";*/
			 String abc="{"+"\"code\":0"+",\"msg\":\"��sir\""+",\"count\":20"+",\"data\":"+jsonsrc+"}";
			 /*JSONObject resObj = new JSONObject();*/
			/* resObj.put("msg", "����ͼƬ�ϴ�OK!");*/
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(abc);
				
				return ;
	}
	//��ȡ���ӵ�ͼƬ
	public void postimgs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		    String pathname=request.getParameter("pathname");   
		    System.out.println("wwwwwwww"+pathname);
	        //������Ӧͷ��������������ظ��ļ�
	        response.setHeader("Content-type","image/*");
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
	
	
	//�ϴ�����ͼƬ
/*	public void uppostimg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int uid=(int) request.getSession().getAttribute("uid");
		String path=this.getServletContext().getRealPath("/WEB-INF/images/"+uid+"");
		File myfile=new File(path);
		if (!myfile.exists() && !myfile.isDirectory()) {
		     System.out.println(path+"Ŀ¼�����ڣ���Ҫ����");
		     //����Ŀ¼
		     myfile.mkdir();
		 }
		String savePath=this.getServletContext().getRealPath("/WEB-INF/images/"+uid+"/postimg");
		  System.out.println(savePath);
		  File file = new File(savePath);
			 //�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
			 if (!file.exists() && !file.isDirectory()) {
			     System.out.println(savePath+"Ŀ¼�����ڣ���Ҫ����");
			     //����Ŀ¼
			     file.mkdir();
			 }
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
			         return ;
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
			        	  filename = item.getName();
			        	  System.out.println(filename);
				          ��sir  �޸�ͼƬ������ ��ͷ�����洢�����ݿ���
			        	  if(filename==null || filename.trim().equals("")){
				                 continue;
				             }
			             
			             //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
			             filename = filename.substring(filename.lastIndexOf("\\")+1);
			             //��ȡitem�е��ϴ��ļ���������
			             InputStream in = item.getInputStream();
			             
			             response.setContentType("image/*");//��sir���������ʽ
			             
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
			request.getSession().setAttribute("postimg", filename);//�����ϴ�������ͼƬ�����֣��ϴ�������ʱ��һ���ϴ������ݿ�
			Map map=new HashMap();
			map.put("src","UpAdown?method=postimg&postimg="+filename+"");
			 JSONObject jsonsrc = JSONObject.fromObject(map);
			 String abc="{"+"\"code\":0"+",\"msg\":\"��sir\""+",\"count\":20"+",\"data\":"+"{"+"\"src\":\"WEB-INF\\\""+"}"+"}";
			 String abc="{"+"\"code\":0"+",\"msg\":\"��sir\""+",\"count\":20"+",\"data\":"+jsonsrc+"}";
			 JSONObject resObj = new JSONObject();
			 resObj.put("msg", "����ͼƬ�ϴ�OK!");
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(abc);
				
				return ;
	}
	//��ȡ���ӵ�ͼƬ
	public void postimg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		    String rankimg=request.getParameter("postimg");
		    int uid=(int) request.getSession().getAttribute("uid");
	        String path=this.getServletContext().getRealPath("/WEB-INF/images/"+uid+"/postimg/");
	        String pathname=path+"\\"+rankimg;//·��+�ļ���
	        File file = new File(path);
	        //����ļ�������
	        if(!file.exists()){
	            request.setAttribute("message", "��Ҫ���ص���Դ�ѱ�ɾ������");
	            request.getRequestDispatcher("/index.jsp").forward(request, response);
	            return;
	        }
	        
	        //������Ӧͷ��������������ظ��ļ�
	        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(rankimg, "UTF-8"));
	       response.setContentType("image/*");
	        OutputStream toClient=response.getOutputStream();
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
	}*/
	public void uidheadimg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uid=request.getParameter("uid");
		UserDao us=new UserDaoImpl();
		User user=new User();
		user=us.getuser(uid);
		String headimg=user.getImgname();
		 String path=this.getServletContext().getRealPath("/WEB-INF/images/headimg/");
	        String pathname=path+"\\"+headimg;//·��+�ļ���
	        File file = new File(path);
	        
	        //������Ӧͷ��������������ظ��ļ�
	        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(headimg, "UTF-8"));
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
	public void upwork(HttpServletRequest request, HttpServletResponse response) throws IOException{	
		 String savePath="F:/workspace/UAV/WebContent/video";
		
		 String filename = "";
		 String message="";
		  /*File file = new File(savePath);
			 //�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
			 if (!file.exists() && !file.isDirectory()) {
			     System.out.println(savePath+"Ŀ¼�����ڣ���Ҫ����");
			     //����Ŀ¼
			     file.mkdir();
			 }*/
	     try{
		     //ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
		     //1������һ��DiskFileItemFactory����
		     DiskFileItemFactory factory = new DiskFileItemFactory();
		     //2������һ���ļ��ϴ�������
		     ServletFileUpload upload = new ServletFileUpload(factory);
		      //����ϴ��ļ�������������
		     upload.setHeaderEncoding("UTF-8"); 
		     //3���ж��ύ�����������Ƿ����ϴ���������
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
		        	  filename = item.getName();
	           
		             if(filename==null || filename.trim().equals("")){
		                 continue;
		             }
		             //��ȡitem�е��ϴ��ļ���������
		             InputStream in = item.getInputStream();
	             
		             //����һ���ļ������
		             FileOutputStream out = new FileOutputStream(savePath + "/" + filename);
		             
		             request.getSession().setAttribute("workpath", savePath + "/" + filename);
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
		     
		 }JSONObject resObj = new JSONObject();
			resObj.put("msg", "��Ʒ�ϴ��ɹ�");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(resObj.toString());
	}
	public void downwork(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String pathname=request.getParameter("pathname");   
        //������Ӧͷ��������������ظ��ļ�

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
}
