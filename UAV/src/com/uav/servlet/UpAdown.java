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
	//上传作品图片
	public JSONObject uppic(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 String which=request.getParameter("which");
		 String savePath=this.getServletContext().getRealPath("/WEB-INF/images/works");
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
			         return null;
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
			        	  filename = item.getName();
				          /*徐sir  修改图片名并且 将头像名存储到数据库中*/
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
			             //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
			             //处理获取到的上传文件的文件名的路径部分，只保留文件名部分

			             //获取item中的上传文件的输入流
			             InputStream in = item.getInputStream();
			             
			             /*response.setContentType("image/*");//徐sir创建输出格式
	*/		             
			             //创建一个文件输出流
			             FileOutputStream out = new FileOutputStream(savePath + "\\" + myfilename);
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
			     
			 }JSONObject resObj = new JSONObject();
				resObj.put("msg", "ok");
				response.setContentType("application/json;charset=utf-8");
				
				response.getWriter().write(resObj.toString());
				return resObj;
	}
	//通过文件名获取作品的头像名
	public void downpic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			  String fileName=request.getParameter("filename");
			  
		    
		        //上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
		      /*  String fileSaveRootPath=this.getServletContext().getRealPath("/WEB-INF/images");*/
			 
		        String path=this.getServletContext().getRealPath("/WEB-INF/images/works/");
		        String pathname=path+"\\"+fileName;
		        System.out.println("UpAdownpathname:"+pathname);
		        File file = new File(path);
		        //如果文件不存在
		        if(!file.exists()){
		            request.setAttribute("message", "您要下载的资源已被删除！！");
		            request.getRequestDispatcher("/index.jsp").forward(request, response);
		            return;
		        }
		        
		        //设置响应头，控制浏览器下载该文件
		        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		        response.setContentType("image/*");
		      /*  OutputStream toClient=response.getOutputStream();*/
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
   //通过图片名获取大神榜用户的头像
	public void rankimg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		  String rankimg=request.getParameter("rankimg");
	        String path=this.getServletContext().getRealPath("/WEB-INF/images/headimg/");
	        String pathname=path+"\\"+rankimg;//路径+文件名
	        File file = new File(path);
	        //如果文件不存在
	        if(!file.exists()){
	            request.setAttribute("message", "您要下载的资源已被删除！！");
	            request.getRequestDispatcher("/index.jsp").forward(request, response);
	            return;
	        }
	        
	        //设置响应头，控制浏览器下载该文件
	        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(rankimg, "UTF-8"));
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
	//上传帖子图片
	public void uppostimgs(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int uid=(int) request.getSession().getAttribute("uid");
		String path=this.getServletContext().getRealPath("/WEB-INF/images/"+uid+"");
		System.out.println("脑壳疼："+path);
		
		File myfile=new File(path);
		if (!myfile.exists() && !myfile.isDirectory()) {
		     System.out.println(path+"目录不存在，需要创建");
		     //创建目录
		     myfile.mkdir();
		 }
		String savePath=this.getServletContext().getRealPath("/WEB-INF/images/"+uid+"/postimg");
		  System.out.println(savePath);
		  File file = new File(savePath);
			 //判断上传文件的保存目录是否存在
			 if (!file.exists() && !file.isDirectory()) {
			     System.out.println(savePath+"目录不存在，需要创建");
			     //创建目录
			     file.mkdir();
			 }
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
			         return ;
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
			        	  filename = item.getName();
			        	  System.out.println(filename);
				          /*徐sir  修改图片名并且 将头像名存储到数据库中*/
			        	  if(filename==null || filename.trim().equals("")){
				                 continue;
				             }
			             
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
		  
		    String pathname=savePath + "\\" + filename;	  
		    pathname=pathname.replaceAll("\\\\", "/");
			 System.out.println("pathname:::::"+pathname);
			 request.getSession().setAttribute("pathname", pathname);//保存上传的帖子图片的名字，上传到帖子时候一起上传到数据库
			Map map=new HashMap();
			map.put("src","UpAdown?method=postimgs&pathname="+pathname+"");


			 pathname=pathname.replaceAll("\\\\", "/");
			 System.out.println("pathname:::::"+pathname);
			 request.getSession().setAttribute("pathname", pathname);//保存上传的帖子图片的名字，上传到帖子时候一起上传到数据库
			
			 JSONObject jsonsrc = JSONObject.fromObject(map);
			 /*String abc="{"+"\"code\":0"+",\"msg\":\"徐sir\""+",\"count\":20"+",\"data\":"+"{"+"\"src\":\"WEB-INF\\\""+"}"+"}";*/
			 String abc="{"+"\"code\":0"+",\"msg\":\"徐sir\""+",\"count\":20"+",\"data\":"+jsonsrc+"}";
			 /*JSONObject resObj = new JSONObject();*/
			/* resObj.put("msg", "帖子图片上传OK!");*/
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(abc);
				
				return ;
	}
	//获取帖子的图片
	public void postimgs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		    String pathname=request.getParameter("pathname");   
		    System.out.println("wwwwwwww"+pathname);
	        //设置响应头，控制浏览器下载该文件
	        response.setHeader("Content-type","image/*");
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
	
	
	//上传帖子图片
/*	public void uppostimg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int uid=(int) request.getSession().getAttribute("uid");
		String path=this.getServletContext().getRealPath("/WEB-INF/images/"+uid+"");
		File myfile=new File(path);
		if (!myfile.exists() && !myfile.isDirectory()) {
		     System.out.println(path+"目录不存在，需要创建");
		     //创建目录
		     myfile.mkdir();
		 }
		String savePath=this.getServletContext().getRealPath("/WEB-INF/images/"+uid+"/postimg");
		  System.out.println(savePath);
		  File file = new File(savePath);
			 //判断上传文件的保存目录是否存在
			 if (!file.exists() && !file.isDirectory()) {
			     System.out.println(savePath+"目录不存在，需要创建");
			     //创建目录
			     file.mkdir();
			 }
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
			         return ;
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
			        	  filename = item.getName();
			        	  System.out.println(filename);
				          徐sir  修改图片名并且 将头像名存储到数据库中
			        	  if(filename==null || filename.trim().equals("")){
				                 continue;
				             }
			             
			             //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
			             filename = filename.substring(filename.lastIndexOf("\\")+1);
			             //获取item中的上传文件的输入流
			             InputStream in = item.getInputStream();
			             
			             response.setContentType("image/*");//徐sir创建输出格式
			             
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
			request.getSession().setAttribute("postimg", filename);//保存上传的帖子图片的名字，上传到帖子时候一起上传到数据库
			Map map=new HashMap();
			map.put("src","UpAdown?method=postimg&postimg="+filename+"");
			 JSONObject jsonsrc = JSONObject.fromObject(map);
			 String abc="{"+"\"code\":0"+",\"msg\":\"徐sir\""+",\"count\":20"+",\"data\":"+"{"+"\"src\":\"WEB-INF\\\""+"}"+"}";
			 String abc="{"+"\"code\":0"+",\"msg\":\"徐sir\""+",\"count\":20"+",\"data\":"+jsonsrc+"}";
			 JSONObject resObj = new JSONObject();
			 resObj.put("msg", "帖子图片上传OK!");
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(abc);
				
				return ;
	}
	//获取帖子的图片
	public void postimg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		    String rankimg=request.getParameter("postimg");
		    int uid=(int) request.getSession().getAttribute("uid");
	        String path=this.getServletContext().getRealPath("/WEB-INF/images/"+uid+"/postimg/");
	        String pathname=path+"\\"+rankimg;//路径+文件名
	        File file = new File(path);
	        //如果文件不存在
	        if(!file.exists()){
	            request.setAttribute("message", "您要下载的资源已被删除！！");
	            request.getRequestDispatcher("/index.jsp").forward(request, response);
	            return;
	        }
	        
	        //设置响应头，控制浏览器下载该文件
	        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(rankimg, "UTF-8"));
	       response.setContentType("image/*");
	        OutputStream toClient=response.getOutputStream();
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
	}*/
	public void uidheadimg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uid=request.getParameter("uid");
		UserDao us=new UserDaoImpl();
		User user=new User();
		user=us.getuser(uid);
		String headimg=user.getImgname();
		 String path=this.getServletContext().getRealPath("/WEB-INF/images/headimg/");
	        String pathname=path+"\\"+headimg;//路径+文件名
	        File file = new File(path);
	        
	        //设置响应头，控制浏览器下载该文件
	        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(headimg, "UTF-8"));
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
	public void upwork(HttpServletRequest request, HttpServletResponse response) throws IOException{	
		 String savePath="F:/workspace/UAV/WebContent/video";
		
		 String filename = "";
		 String message="";
		  /*File file = new File(savePath);
			 //判断上传文件的保存目录是否存在
			 if (!file.exists() && !file.isDirectory()) {
			     System.out.println(savePath+"目录不存在，需要创建");
			     //创建目录
			     file.mkdir();
			 }*/
	     try{
		     //使用Apache文件上传组件处理文件上传步骤：
		     //1、创建一个DiskFileItemFactory工厂
		     DiskFileItemFactory factory = new DiskFileItemFactory();
		     //2、创建一个文件上传解析器
		     ServletFileUpload upload = new ServletFileUpload(factory);
		      //解决上传文件名的中文乱码
		     upload.setHeaderEncoding("UTF-8"); 
		     //3、判断提交上来的数据是否是上传表单的数据
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
		        	  filename = item.getName();
	           
		             if(filename==null || filename.trim().equals("")){
		                 continue;
		             }
		             //获取item中的上传文件的输入流
		             InputStream in = item.getInputStream();
	             
		             //创建一个文件输出流
		             FileOutputStream out = new FileOutputStream(savePath + "/" + filename);
		             
		             request.getSession().setAttribute("workpath", savePath + "/" + filename);
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
		     
		 }JSONObject resObj = new JSONObject();
			resObj.put("msg", "作品上传成功");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(resObj.toString());
	}
	public void downwork(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String pathname=request.getParameter("pathname");   
        //设置响应头，控制浏览器下载该文件

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
}
