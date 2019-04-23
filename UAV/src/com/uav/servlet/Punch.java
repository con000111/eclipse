package com.uav.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uav.dao.UserDao;
import com.uav.dao.impl.UserDaoImpl;
import com.uav.entity.User;

/**
 * Servlet implementation class Punch
 */
@WebServlet("/Punch")
public class Punch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Punch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDao user=new UserDaoImpl();

		int sum=(int) request.getAttribute("sum");
		int uid=0;
		uid=((User)request.getSession().getAttribute("userinfo")).getUid();

		if(user.punch(uid,sum)){
			response.getWriter().write("打卡成功");
		}else{
			response.getWriter().write("打卡失败");
		};
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
