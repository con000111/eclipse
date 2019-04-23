<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" href="layui/layui.css"  media="all">
	<link rel="stylesheet" type="text/css" href="lib4/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="fonticon/font-awesome.css">
  <link rel="stylesheet" type="text/css" href="layui/global.css">
  <style type="text/css">
  html,body{
    background: url(img/back.png);
    background-repeat: round;
  }
</style>
<style type="text/css">
 .layui-nav .layui-nav-child dd ul{
display:block;
/* position:absolute; */
top:0px;
/* left:80px; */
background:#dddddd;
box-shadow:0 2px 4px rgba(0,0,0,.12);
border:1px solid #d2d2d2;
border-radius:2px
}
/* .layui-nav .layui-nav-child dd ul{display:none;}  */
.layui-nav .layui-nva-child dd ul a:hover{background:#f0f0f0;}
</style>
</head>
<body>

	<ul class="layui-nav layui-bg-cyan fixed-top" style="width:100%">
		<div style="padding:0 5%  0 5% ;">
      <img src="img/logo.png">
      <li class="layui-nav-item" id="index"><a href="index.jsp">首页</a></li>
      <li class="layui-nav-item" id="myforum"><a href="action?url=WEB-INF/page/forum.jsp">论坛</a></li>
      <li class="layui-nav-item" id="work"><a href="action?url=WEB-INF/page/work.jsp">飞行作品</a></li>
      <li class="layui-nav-item" id="section">
       <a href="javascript:;" >版块</a>
       <dl class="layui-nav-child">
        <dd><a href="javascript:void(0)">大疆</a>
             <ul id="dj">
             </ul>
        </dd>
        <dd><a href="javascript:void(0)">零度</a>
              <ul id="ld"></ul>
        </dd>
        <dd><a href="javascript:void(0)">亿航</a>
            <ul id="yh"></ul>
        </dd>
        <dd><a href="javascript:void(0)">美嘉欣</a>
             <ul id="mjx"></ul>
        </dd>
      </dl>
    </li>
   
    <li class="layui-nav-item" >
       <a href="javascript:;" id="forbid">服务</a>
       <dl class="layui-nav-child">
        <dd><a href="forbid.jsp" >禁飞区查询</a>
        <dd><a href="Preloads?method=teach" >UAV教程</a>
        </dd>
        </dl>
    </li>
    <div style="display: inline;margin-left: 26%"></div>

      <button class="btn btn-outline-success my-2 my-sm-0" type="buttom" onclick="window.location.href='action?url=WEB-INF/page/addposts.jsp'" >发帖</button>
      <button class="btn btn-outline-success my-2 my-sm-0" type="buttom" onclick="window.location.href='action?url=WEB-INF/page/google.jsp'" >搜索</button>
    
    
    <hr style="width: 0px;height: 30px;border: 1px solid white;display: inline;margin:5px">
    <%if(session.getAttribute("uid")!=null){ %>
    <div style="display:inline">
    <img src="UserServlet?method=getheadimg" style="width: 36px;height: 36px;margin-left: 10px;border-radius: 100%">
    <li class="layui-nav-item" >
       <a href="javascript:;">${sessionScope.userinfo.nick}</a>
       <dl class="layui-nav-child">
        <dd><a href="action?url=WEB-INF/page/userinfo.jsp">个人中心</a></dd>
        
                <dd><a href="action?url=WEB-INF/page/message.jsp">消息管理<span class="layui-badge">${mycount}</span></a></dd>
                <%if(request.getSession().getAttribute("auth").equals("moder")){ %>     
           <dd><a href="action?url=WEB-INF/moder/moder.jsp">版主后台管理</a></dd>
           <%} else  if(request.getSession().getAttribute("auth").equals("admin")){%>
             <dd><a href="action?url=WEB-INF/admin/admin.jsp">后台管理</a></dd>      
              <dd><a href="action?url=WEB-INF/moder/moder.jsp">版主后台管理</a></dd>
           <%} %>
         <dd><a href="UserServlet?method=myemail">账号安全</a></dd>
        <dd><a href="UserServlet?method=outlogin">退出登陆</a></dd>
       
      </dl>
    </li>
    </div>
    <%} else {%>
    <div style="display:inline">
    	<button class="btn btn-outline-success my-2 my-sm-0" onclick="window.location.href='login.html'">登陆</button>

    <button class="btn btn-outline-success my-2 my-sm-0" onclick="window.location.href='register.html'" >
    注册</button>
    </div>
    <%} %>
</div>
</ul>



<div style="right: 20px;bottom: 20px;position: fixed;width: 45px;height: 80px">
    <a id="toptip" href="#" data-toggle="tooltip" data-placement="left" title="顶部" style="text-decoration: none;" class="btn btn-outline-success my-2 my-sm-0">
      <i class="icon-arrow-up icon-large" ></i>
    </a>    
</div>
 <div id="accordionExample"></div>
</body >
<script src="layui/layui.js" charset="utf-8"></script>
<script>
  layui.use('element', function(){
  var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
  
  //监听导航点击
  element.on('nav(demo)', function(elem){
    //console.log(elem)
    layer.msg(elem.text());
  });
});
</script>
<script src="lib4/jquery-1.9.1.js"></script>
<script src="lib4/bootstrap.min.js"></script>
<script type="text/javascript" src="lib4/bootstrap.bundle.js"></script>
<script type="text/javascript">
  $('#toptip').tooltip();
</script>
<script>
$.ajax({
  url:'Preloads?method=getsection',
  data:{forum:"大疆"},
  type:"post",
  success:function(data){
    console.log(data);
    output=[];
        $.each(data,function(key,value){
        output.push('<li style="display:inline"><a style="display:inline" href="action?url=WEB-INF/page/plate.jsp&forum='+value+'">'+value+'</a></li>');
       });
       $("#dj").html(output.join(''));
       }
})
$.ajax({
  url:'Preloads?method=getsection',
  data:{forum:"零度"},
  type:"post",
  success:function(data){
    console.log(data);
    output=[];
        $.each(data,function(key,value){
        output.push('<li style="display:inline"><a style="display:inline" href="action?url=WEB-INF/page/plate.jsp&forum='+value+'">'+value+'</a></li>');
       });
       $("#ld").html(output.join(''));
       }
})
$.ajax({
  url:'Preloads?method=getsection',
  data:{forum:"美嘉欣"},
  type:"post",
  success:function(data){
    console.log(data);
    output=[];
        $.each(data,function(key,value){
        output.push('<li style="display:inline"><a style="display:inline" href="action?url=WEB-INF/page/plate.jsp&forum='+value+'">'+value+'</a></li>');
       });
       $("#mjx").html(output.join(''));
       }
})
</script>
<script>
$.ajax({
  url:'Preloads?method=getsection',
  data:{forum:"亿航"},
  type:"post",
  success:function(data){
    output=[];
        $.each(data,function(key,value){
        output.push('<li style="display:inline"><a style="display:inline" href="action?url=WEB-INF/page/plate.jsp&forum='+value+'">'+value+'</a></li>');
       });
       $("#yh").html(output.join(''));
       }
})
$.ajax({
	url:'Preloads?method=getrank',
	success:function(data){
		console.log(data);
        $.each(data,function(key,value){
        	
            output.push('<dd><a href="action?url=WEB-INF/page/seeuser.jsp&uid='+value.uid+' "><img src="UpAdown?method=rankimg&rankimg='+value.imgname+'"><cite>'+value.nick+' </cite><i>'+value.fraction+'积分</i></a></dd>');
           });
           $("#rank").html(output.join(''));
	}
})
</script>
 </html>