package com.uav.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uav.util.SendMail;
import com.uav.util.VerCode;



/**
 * Servlet implementation class Sendmail
 */
@WebServlet(
		urlPatterns = { "/Sendmail" }, 
		initParams = { 
				@WebInitParam(name = "smtphost", value = "smtp.qq.com", description = "SMTP Host"), 
				@WebInitParam(name = "port", value = "465", description = "Mail Port"), 
				@WebInitParam(name = "mailusername", value = "x9768fg@foxmail.com", description = "Mail Host Username"), 
				@WebInitParam(name = "mailpassword", value = "hbrrfumrsafcbefd", description = "Mail Host Password"), 
				@WebInitParam(name = "mailfrom", value = "x9768fg@foxmail.com", description = "Mail From")
		})
public class Sendmail extends HttpServlet {
	private ServletConfig Servletconf; 
	private String username;
	private String email;
	private String msg;
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.Servletconf=config;
	}
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sendmail() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String username="您好！";
		email=request.getParameter("email");
		String code=VerCode.getRandomString(4);
		
		//给验证码设置session
		request.getSession().setAttribute("vercode", code);
		
		System.out.println(username+email);
		System.out.println(Servletconf.getInitParameter("smtphost"));
		if(sendmail(email,username,code))
		{
			msg="验证码发送成功";
			response.getWriter().print(msg);
		}
		else
		{
			msg="验证发送失败";
			response.getWriter().print(msg);		
		}

	}
	private boolean sendmail(String mailto,String username,String code){
		String MailTo=mailto;
		String MailSubject="UAV社区-官方邮件";
		String MailBCopyTo="";
	    String MailCopyTo="";
	    String MailBody="<p>邮箱验证码</p>"
	    +"<p>亲爱的 UAV社区用户,"+username+",<br />" +
	    		"<br />" +
	    		"	欢迎您，您本次验证码为：</p>" +
	    		"	<p>"+code+"</p>" +
	    		"	  <br />	UAV-社区 International Team </p>	<p>&nbsp;</p>";
		String SMTPHost = Servletconf.getInitParameter("smtphost");
		String Port=Servletconf.getInitParameter("port");
		String MailUsername = Servletconf.getInitParameter("mailusername");
		String MailPassword = Servletconf.getInitParameter("mailpassword");
		String MailFrom = Servletconf.getInitParameter("mailfrom");
		if(SMTPHost==null||SMTPHost==""||MailUsername==null||MailUsername==""||MailPassword==null||MailPassword==""||MailFrom==null||MailFrom=="")
		{
			System.out.println("Servlet parameter Wrongs");
		}
		SendMail send=new SendMail(SMTPHost,Port,MailUsername,MailPassword);
		if(send.sendingMimeMail(MailFrom, MailTo, MailCopyTo, MailBCopyTo, MailSubject, MailBody)){
			return true;
		}
		else
		{
			return false;
		}
	}
	private void toResponse(HttpServletResponse response,String toString) throws IOException
	{
		response.setCharacterEncoding("gb2312");
		PrintWriter out=response.getWriter();
		out.println(toString);
	}


}
