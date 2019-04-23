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

import com.mysql.cj.Session;
import com.uav.dao.BaseDao;
import com.uav.dao.UserDao;
import com.uav.dao.impl.BaseDaoImpl;
import com.uav.dao.impl.UserDaoImpl;
import com.uav.entity.Post;
import com.uav.entity.Punch;
import com.uav.entity.User;

/**
 * Servlet implementation class Preload
 */
@WebServlet("/Preload")
public class Preload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Preload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //我的消息
		if(request.getSession().getAttribute("uid")!=null){
        	  BaseDao bs=new BaseDaoImpl();
        	  int uid=(int) request.getSession().getAttribute("uid");
        	  int mycount=bs.whomuch(uid);
        	  request.setAttribute("mycount",mycount);
          }
		//轮播内容
		BaseDao bs=new BaseDaoImpl();
		List list=new ArrayList<Post>();
		list=bs.carousel();
		request.setAttribute("carousel", list);
		  
          
		   
		  Punch punch=new Punch();
		  UserDao us=new UserDaoImpl();
			int uid=99999;
			int sum=0;
			if(request.getSession().getAttribute("uid")!=null){
				
				uid=(int) request.getSession().getAttribute("uid");
			}
		//置顶帖
			List toppost=new ArrayList<Post>();
			toppost=bs.toppost();
			request.setAttribute("toppost", toppost);

			System.out.println(uid);
			punch=us.record(uid);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();	
			/*System.out.println("时间差："+(date.getTime()-punch.getPunchtime().getTime()) / (24 * 60 * 60 * 1000));*/
	       request.setAttribute("count", punch.getCount());//打卡人数
			if((date.getTime()-punch.getPunchtime().getTime()) / (24 * 60 * 60 * 1000)<1 ){
	        	request.setAttribute("punch", "yes");
	        	
	        	sum=punch.getSum();
	        }else {
	        	request.setAttribute("punch", "no");
	        	if(2>=(date.getTime()-punch.getPunchtime().getTime()) / (24 * 60 * 60 * 1000)){
	        		sum=punch.getSum()+1;	
	        		
	        	}
	        	
	        }
	        request.setAttribute("sum", sum);
		  if(request.getSession().getAttribute("auth")==null){
			  request.getSession().setAttribute("auth", "comm");
		  }
		  
		  request.getRequestDispatcher("WEB-INF/index.jsp").forward(request,response);
		
	}

}
