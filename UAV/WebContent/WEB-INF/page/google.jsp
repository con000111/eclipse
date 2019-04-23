<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title></title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="layui/layui.css">
	<style>
	body,html{
	height:100%
	}
	</style>
</head>
<body>
<jsp:include page="../view/nav.jsp" flush="true"/>
<div class="container">
	<form class="layui-form layui-form-pane" action="UserServlet?method=querypost" method="post">
		<div class="layui-form-item">
			<div class="layui-input-inline" style="margin-right: 0px;width: 10%">
				<select name="key" lay-verify="required" lay-search="">
					<option value="0" >帖子</option>
					<option value="1">用户</option>

				</select>
			</div>
			<div class="layui-input-inline" style="width: 25%">
				<input type="text" name="keyword" lay-verify="keyword" placeholder="请输入" autocomplete="off" class="layui-input">
			</div>
			<div class="layui-input-inline">
				<button lay-submit lay-filter="*" class="layui-btn">搜索</button> 
			</div>
		</div>
	</form>

	<div id="result_list"></div>
	  <div id='demo20'></div>
		
</div>
</body>
<script type="text/javascript" src="layui/layui.js"></script>
<script>
	layui.use(['form', 'layedit'], function(){
		var form = layui.form
		,layer = layui.layer
		form.verify({
			keyword: [
			/^[\S]{1,6}$/
			,'搜索内容必须1到6位，且不能出现空格'
			] 
		});

	});
	</script>
	<script>
	
	layui.use(['laypage', 'layer'], function(){
		  var laypage = layui.laypage;
		  var data=${requestScope.post};
		 
		  laypage.render({
		    elem: 'demo20'
		    ,count: data.length
		     ,first: '首页'
		    ,last: '尾页'
		    ,limit:2
		    ,groups:3
		    ,layout:['count','page','next']
		    ,jump: function(obj){
		      //模拟渲染
		      document.getElementById('result_list').innerHTML = function(){
		        var arr = []
		        ,thisData = data.concat().splice(obj.curr*obj.limit - obj.limit, obj.limit);
		        layui.each(thisData, function(index, item){
		          arr.push('<p><h4><a href="UserServlet?method=detailpost&pid='+item.pid+'&where=detail">'+item.subjects+'</a></h4><h5>作者:'+item.uid+'</h5><span>'+item.date+'</span>&nbsp&nbsp&nbsp板块:<span>'+item.forum+'</span><hr></p>');
		        });
		        return arr.join('');
		      }();
		    }
		  });})
		   
	
	
</script>
<script type="text/javascript">
layui.use(['laypage', 'layer'], function(){
	  var laypage = layui.laypage;
	  var user=${requestScope.user};
laypage.render({
	    elem: 'demo20'
	    ,count: user.length
	     ,first: '首页'
	    ,last: '尾页'
	    ,limit:4
	    ,groups:3
	    ,layout:['count','page','next']
	    ,jump: function(obj){
	      //模拟渲染
	      document.getElementById('result_list').innerHTML = function(){
	        var arr = []
	        ,thisData = user.concat().splice(obj.curr*obj.limit - obj.limit, obj.limit);
	        layui.each(thisData, function(index, item){
	          arr.push(' <ul class="fly-list" ><li style="width: 40%;height: 5%;float:left" ><a class="fly-avatar"><img src="UpAdown?method=rankimg&rankimg='+item.imgname+'" ></a><h2><a href="action?url=WEB-INF/page/seeuser.jsp&uid='+item.uid+'">'+item.nick+'</a><a>'+item.address+'</a></h2><div class="fly-list-info"><cite>性别:'+item.sex+'</cite><span class="fly-list-kiss layui-hide-xs" title="积分"><i class="iconfont icon-kiss"></i> '+item.fraction+'</span></div></li></ul>' );
	        });
	        return arr.join('');
	      }();
	    }
	  }); })
</script>
</html>