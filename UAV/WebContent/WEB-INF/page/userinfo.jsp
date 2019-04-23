`<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();//
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <title>UAV-用户信息</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="lib4/bootstrap.css">  
  <link rel="stylesheet" href="layui/layui.css">	
</head>
<style>
.layui-upload-img{width: 92px; height: 92px; margin: 0 10px 10px 0;}
</style>
<style type="text/css">
   div ul li{
      display: block;
      float: left;
      margin-left: 15px;
    }
    /* 
    div li:hover a img{
       width:150px;
       height:150px;
       position:absolute;
    } */
    
    #scroll_ul,#scrollleft{
    background:gray;
    overflow: hidden;
    overflow-x: hidden;
    overflow-y: hidden;
    padding:  0 20px 0 20px;
    padding-top:5px;
    padding-bottom:5px;
    }
</style>
<style type="text/css">
    	 p[name='after']{
    	 	opacity: 0;
    	 	transition: opacity 1s ease-in-out;
    	 }
    	li div:hover p{
            opacity: 1;
          
    	 }
    	 li div p:last-child{
    	 display: none;
    	 position:absolute;
    	 margin-left:9px;
    	 color:white;
    	 }
    	 li div img{
    	 display: none;
    	 width: 180px;
    	 height: 120px
    	 }
  .tab-content div li{
     position: relative;
    height: 45px;
    line-height: 22px;
    padding: 15px 15px 15px 75px;
    border-bottom: 1px dotted #e2e2e2;
}}

    </style>
<body>

<jsp:include page="../view/nav.jsp" flush="true"/>
<div class="container">
 <p style="margin-bottom: 0px">
  


	
	<div >
		<div class="card text-center">
  <div class="card-header">
    <ul class="nav nav-tabs card-header-tabs" >
      <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#info" onclick="info()">个人信息</a>
      </li>
      <li class="nav-item">
        <a class="nav-link " data-toggle="tab" href="#post" onclick="post()" >我的帖子</a>
      </li>
      <li class="nav-item">
        <a class="nav-link " data-toggle="tab" href="#collect" onclick="collect()">我的收藏</a>
      </li>
      <li class="nav-item">
        <a class="nav-link " data-toggle="tab" href="#friend" onclick="friend()">我的好友</a>
      </li>
    </ul>
  </div>
 

<div class="tab-content">
 <div id="info" class="container tab-pane active"><jsp:include page="../view/uinfo.jsp" flush="true"/></div>
 <div id="post" class="container tab-pane fade">
   post
 </div>
 <div id="collect" class="container tab-pane fade"></div>
 <div id="friend" class="container tab-pane fade"></div>
</div>



</body>

</html>

<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="lib4/bootstrap.min.js"></script>
<script type="text/javascript" src="layui/layui.js"></script>
<script type="text/javascript">
  function info(){
    var path='WEB-INF/view/uinfo.jsp';
    $.ajax({
		type:"post",
		url:"<%=path%>/action",
		
		data:{url:path},
		statusCode:{
			404:function(){
				alert("404");
			},
			500:function(){
				alert("500");
			}
			
		},
		success:function(data,textStatus){
			$("#info").html(data);
	       }
	})
  }
  function post(){
	  $.ajax({
			url:'UserServlet?method=mypost',
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
		        output.push('<li><a style="float:left;" href="UserServlet?method=detailpost&pid='+value.pid+'&where=detail" target="_blank">'+value.subjects+'</a><span style="float:right;">'+value.date+'</span> <span class="layui-btn layui-btn-xs" type="edit" style="float:right;margin:5px"><a href="UserServlet?method=detailpost&pid='+value.pid+'&where=alter" style="color:white">编辑此贴</a></span><span class="layui-btn layui-btn-xs" type="edit" style="float:right;margin:5px"><a href="UserServlet?method=delpost&pid='+value.pid+'" style="color:white">删除此贴</a></span></li>');
	           });
	           $("#post").html(output.join(''));
		       }
		})

  }
function collect(){
	$.ajax({		
		url:'UserServlet?method=mycoll&uid=${sessionScope.uid}',
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
	        output.push('<li><a style="float:left;" href="UserServlet?method=detailpost&pid='+value.pid+'&where=detail" target="_blank">'+value.subjects+'</a>&nbsp&nbsp<span class="layui-btn layui-btn-xs" type="edit" style="float:right;margin:5px"><a href="UserServlet?method=delcoll&pid='+value.pid+'" style="color:white">取消收藏</a></span><span style="float:right;">'+value.date+'</span>&nbsp&nbsp</li>');
           });
           $("#collect").html(output.join(''));
	       }
	})
}
function friend(){
	$.ajax({
		url:'UserServlet?method=myfriend',
		success:function(data){
			output=[];
			$.each(data,function(key,value){
				output.push('<li><a style="float:left" href="action?url=WEB-INF/page/seeuser.jsp&uid='+value.uid+'">'+value.nick+'</a><button class="layui-btn layui-btn-xs" data-toggle="modal" style="float:right"  data-target="#chatmodal" onclick="myhid('+value.uid+')">发送留言</button><button class="layui-btn layui-btn-xs" style="float:right;margin-right:10px"  onclick="delfri('+value.uid+')">删除好友</button></li>');
			});
			$("#friend").html(output.join(''));
		}
	})
}
</script>
   <script>
  
  //  无缝隙滚动代码
  var scroll_speed = 30;
  var tabLeft = document.getElementById("scrollleft");
  var tab_a = document.getElementById("scroll_a");
  var tab_b = document.getElementById("scroll_b");
  tab_b.innerHTML = tab_a.innerHTML;
  

  function scroll_left() {
    if (tabLeft.scrollLeft >= tab_b.offsetWidth)//读取元素滚动条到元素左边的距离  ,offsetWidth(tab_b的大小width+2xborder+2xpadding)
      tabLeft.scrollLeft -= tab_b.offsetWidth
    else {
      tabLeft.scrollLeft++;
    } 
  }
  console.log(tabLeft.scrollLeft);
  console.log(tab_b.offsetWidth);
  var MyMar = setInterval(scroll_left, scroll_speed);
  tabLeft.onmouseover = function () {
    clearInterval(MyMar)
  };
  tabLeft.onmouseout = function () {
    MyMar = setInterval(scroll_left, scroll_speed)
  };
</script>

<script type="text/javascript" src="js/upfile.js">
</script>
<!-- Modal -->
<div class="modal fade" id="chatmodal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header" style="padding-bottom:0px">
          <p>发送留言</p>
      </div>
      <div class="modal-body">
      
      	  <textarea rows="5" cols="60" id="message">
      	  </textarea>
      
      </div>
      <div class="modal-footer">
        <button type="button" id="mycancel" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" onclick="send()" class="btn btn-primary">发送</button>
      </div>

    </div>
  </div>
</div>
<input type=hidden value="" id="myhidden">
<script>
function myhid(e){
    $("#myhidden").attr("value",e);
    
}
function send(){
var toid=$("#myhidden").val();
var text=$("#message").val().trim();
$.ajax({
	url:'UserServlet?method=mess',
	type:'post',
	data:{content:text,toid:toid},
	success:function(data){
		alert(data);
	}
});
document.getElementById("mycancel").click();
} 
function delfri(e){
	var fid=e;
	$.ajax({
		url:'UserServlet?method=delfri',
		data:{fid:fid},
		success:function(data){
			alert(data);
		}
	})
}
</script>


