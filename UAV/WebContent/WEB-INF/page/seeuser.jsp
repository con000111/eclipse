<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UAV-用户信息</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">	
</head>
<style type="text/css">/*//头像CSS*/
	#headimg{
		width: 100px;
		height: 100px;
	}
	#post li {
    position: relative;
    height: 45px;
    line-height: 22px;
    padding: 15px 15px 15px 75px;
    border-bottom: 1px dotted #e2e2e2;
}
   
</style>
<body>
<jsp:include page="../view/nav.jsp" flush="true"/>
<div class="card text-center">
  <div class="card-header">
    <ul class="nav nav-tabs card-header-tabs" >
      <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#info" >他的信息</a>
      </li>
      <li class="nav-item">
        <a class="nav-link " data-toggle="tab" href="#post">他的帖子</a>
      </li>
    </ul>
  </div>


    <div class="tab-content">
	 <div id="info" class="container tab-pane active"> 
	 </div>
	 <div id="post" class="container tab-pane fade">
	     <li><a></a><span></span></li>
	 </div>
    </div>
</div>
</body>
</html>
<script>
var uid=<%=request.getParameter("uid")%>
$.ajax({
	url:'UserServlet?method=otherpost&uid=<%=request.getParameter("uid")%>',
	statusCode:{
		404:function(){
			alert("404");
		},
		500:function(){
			alert("500");
		}
		
	},
	success:function(data,textStatus){
		var mydata=data,
		output=[];
        $.each(data,function(key,value){
        output.push('<li><a style="float:left;" href="UserServlet?method=detailpost&pid='+value.pid+'&where=detail" target="_blank">'+value.subjects+'</a><span style="float:right;">'+value.date+'</span></li>');
       });
       $("#post").html(output.join(''));
       }
})

</script>
<script>
var uid=<%=request.getParameter("uid")%>
$.ajax({
	url:'UserServlet?method=otherinfo&uid=<%=request.getParameter("uid")%>',
	statusCode:{
		404:function(){
			alert("404");
		},
		500:function(){
			alert("500");
		}
		
	},
	success:function(data,textStatus){
		var mydata=data,
		output=[];
        $.each(data,function(key,value){
        output.push('<P  id="addspan"> 头像：<img alt="" src="UpAdown?method=rankimg&rankimg='+value.imgname+'" style="border-radius: 50%;" id="headimg"></P><P> 昵称：<span>'+value.nick+'</span></P><p> 性别：<span>'+value.sex+'</span></p><p> 地址：<span>'+value.address+'</span></p><p> 签名：<span>'+value.sign+'</span></p><p> 积分：<span>'+value.fraction+'</span></p><button class="layui-btn" onclick=addfri()>点击添加好友</button>	');
       });
       $("#info").html(output.join(''));
       }
})
function addfri(){
    $.ajax({
    	url:'UserServlet?method=addfri&fid=<%=request.getParameter("uid")%>',
    	success:function(data){
    		alert(data);
    	}
    })
}
</script>