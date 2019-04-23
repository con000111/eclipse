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

import com.uav.dao.impl.BaseDaoImpl;
import com.uav.entity.User;

/**
 * Servlet implementation class DoDispatcher
 */
@WebServlet("/action")//进入WEB-INF目录的方式
public class DoDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoDispatcher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url=request.getParameter("url");
		System.out.println("dodispatcher:"+url);
		
		if(request.getSession().getAttribute("uid")==null){
			response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type","text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset='utf-8'>");
			out.println("<script>");		
			out.println("alert('亲，请先登陆!!')");	
			out.println("history.back();");
			out.println("</script>");
			out.println("</head>");
			out.println("<body>");	
			out.println("</body>");
			out.println("</html>");
		}else{
			if(url.equals("WEB-INF/page/userinfo.jsp")){
				System.out.println("dodispatcher111111:"+url);
				BaseDaoImpl base=new BaseDaoImpl();
				List<String> list=new ArrayList<String>();
			    	 int uid=0;
				    uid=((User)request.getSession().getAttribute("userinfo")).getUid();
			    	list=base.workname(uid);			
					request.setAttribute("works", list); 
		        }
			request.getRequestDispatcher(url).forward(request, response);
		}
		
		

	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
