<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title></title>
	<meta charset="utf-8">
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
  .fly-list li{
    width:100%;
    height:5%;
  }
</style>

</head>
<body>
 <ul class="layui-nav layui-bg-cyan fixed-top" style="width:1350px">
		<div style="padding:0 5%  0 5% ;">
      <img src="img/logo.png">
      <li class="layui-nav-item"><a href="index.jsp">首页</a></li>
      <li class="layui-nav-item layui-this"><a href="action?url=WEB-INF/page/forum.jsp">论坛</a></li>
      <li class="layui-nav-item"><a href="action?url=WEB-INF/page/work.jsp">飞行作品</a></li>
      <li class="layui-nav-item">
       <a href="javascript:;">版块</a>
       <dl class="layui-nav-child">
        <dd><a href="javascript:void(0)">大疆</a>
             <ul id="dj">
             </ul>
        </dd>
        <dd><a href="javascript:void(0)">零度</a>
              <ul id="ld"></ul>
        </dd>
        <dd><a href="javascript:void(0)">亿航
            <ul id="yh"></ul>
        </a></dd>
        <dd><a href="javascript:void(0)">美嘉欣</a>
             <ul id="mjx"></ul>
        </dd>
      </dl>
    </li>
   
    <li class="layui-nav-item">
       <a href="javascript:;">服务</a>
       <dl class="layui-nav-child">
        <dd><a href="forbid.jsp">禁飞区查询</a>
        </dd>
        </dl>
    </li>
    <div style="display: inline;margin-left: 21%"></div>

      <button class="btn btn-outline-success my-2 my-sm-0" type="buttom" onclick="window.location.href='action?url=WEB-INF/page/addposts.jsp'" >发帖</button>
      <button class="btn btn-outline-success my-2 my-sm-0" type="buttom" onclick="window.location.href='action?url=WEB-INF/page/google.jsp'" >搜索</button>
    
    
    <hr style="width: 0px;height: 30px;border: 1px solid white;display: inline;margin:5px">
    <div style="display: <%=session.getAttribute("uid")==null?"none":"inline" %>;">
    <img src="UserServlet?method=getheadimg" style="width: 36px;height: 36px;margin-left: 10px;border-radius: 100%">
    <li class="layui-nav-item">
       <a href="javascript:;">${sessionScope.userinfo.nick}</a>
       <dl class="layui-nav-child">
        <dd><a href="action?url=WEB-INF/page/userinfo.jsp">个人中心</a></dd>
        
                <dd><a href="action?url=WEB-INF/page/message.jsp">消息管理</a></dd>
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
    <div style="display: <%=session.getAttribute("uid")==null?"inline":"none" %>;">
    	<button class="btn btn-outline-success my-2 my-sm-0" onclick="window.location.href='login.html'">登陆</button>

    <button class="btn btn-outline-success my-2 my-sm-0" onclick="window.location.href='register.html'" >
    注册</button>
    </div>
    
</div>
</ul>
 
 
 
  <div style="margin-top:8px ;"></div>
  <div class="container" style="background-color: #F2F2F2;">
    <div class="row" >
      <div class="col-md-8" style="padding-right:0px">

       <div class="fly-panel" style="margin-bottom: 0;">

        <div class="fly-panel-title fly-filter">
          <a href="action?url=WEB-INF/page/forum.jsp" >按时间</a>
          <span class="fly-mid"></span>
          <a href="action?url=WEB-INF/page/forumhot.jsp"  class="layui-this">按热议</a>

        </div>

        <ul class="fly-list" id="flow-father">    
        </ul>

    </div>


  </div>
  <div class="col-md-4">
    
      <div class="layui-card">
        <div class="layui-card-header">发帖</div>
        <div class="layui-card-body" style="height: 200px;background-repeat: round;">
         
         <a href="action?url=WEB-INF/page/addposts.jsp" targe="_blank"> <button class="layui-btn layui-btn-radius layui-btn-lg" style="width: 85%;margin-left: 25px "><i class="layui-icon" style="font-size: 30px;">&#xe642;</i>&nbsp&nbsp点击发帖</button></a>

          <p style="text-align: center;margin:20px 0 12px 0">今日发帖数</p>
          <span style="font-size: 40px;margin-left: 30%" id="postcount">123123</span>
        </div>
      </div>
      <div class="fly-panel fly-rank fly-rank-reply" id="LAY_replyRank">
        <h3 class="fly-panel-title">板块推荐</h3>
        <dl>
          <dd> 
            <a href="action?url=WEB-INF/page/plate.jsp&forum=大疆">
            <button class="layui-btn layui-btn-radius">大疆</button>
            </a>
          </dd>
          <dd>
            <a href="action?url=WEB-INF/page/plate.jsp&forum=零度">
              <button class="layui-btn layui-btn-radius">零度</button>
            </a>
          </dd>
           <dd>
            <a href="action?url=WEB-INF/page/plate.jsp&forum=华科尔">
               <button class="layui-btn layui-btn-radius">华科尔</button>
            </a>
          </dd>
          
          <dd>
            <a href="action?url=WEB-INF/page/plate.jsp&forum=亿航">
               <button class="layui-btn layui-btn-radius">亿航</button>
            </a>
          </dd>
          <dd>
            <a href="action?url=WEB-INF/page/plate.jsp&forum=小米">
               <button class="layui-btn layui-btn-radius">小米</button>
            </a>
          </dd>
          <dd>
            <a href="action?url=WEB-INF/page/plate.jsp&forum=美嘉欣">
               <button class="layui-btn layui-btn-radius">美嘉欣</button>
            </a>
          </dd>
        </dl>
      </div>
      <div class="fly-panel" ">
        <button class="layui-btn layui-btn-radius" style="margin: 15px 5px 15px 20%;width: 60%;height: 50px">阅读版规</button>
      </div>
  </div>
</div>
</div>
</div>
</body>
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

	layui.use('flow', function(){
		  var $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
		  var flow = layui.flow;
		  flow.load({
		    elem: '#flow-father' //指定列表容器
		    ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
		      var lis = [];
		      //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
		      $.post('Preloads?method=likepost&page='+page, function(res){
		        //假设你的列表返回在data集合中
		        
		        layui.each(res.data, function(index, item){
		          console.log(item);
		          lis.push('<li><a href="action?url=WEB-INF/page/seeuser.jsp&uid='+item.uid+'" class="fly-avatar"><img src="UpAdown?method=uidheadimg&uid='+item.uid+'" alt=""></a><h2><a class="layui-badge">'+item.forum+'</a><a href="UserServlet?method=detailpost&pid='+item.pid+'&where=detail">'+item.subjects+'</a></h2><div class="fly-list-badge"></div><div class="fly-list-info"><a href="action?url=WEB-INF/page/seeuser.jsp&uid='+item.uid+'" link><cite>'+item.nick+'</cite></a><span>'+item.date+'</span><i class="layui-icon" title="点赞数">&#xe6c6 '+item.like+'</i></div></li>');
		        }); 
		        next(lis.join(''), page < res.count);  
		      });
		    }
		  });
		});
  

</script>
<script type="text/javascript">
  $.ajax({
    url:'Preloads?method=postcount',
    type:'post',
    success:function(data) {
       $('#postcount').html(data);
      }
  });
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
        output.push('<li><a href="action?url=WEB-INF/page/plate.jsp&forum=大疆">'+value+'</a></li>');
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
        output.push('<li><a href="action?url=WEB-INF/page/plate.jsp&forum=零度">'+value+'</a></li>');
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
        output.push('<li><a href="action?url=WEB-INF/page/plate.jsp&forum=美嘉欣">'+value+'</a></li>');
       });
       $("#mjx").html(output.join(''));
       }
})
$.ajax({
  url:'Preloads?method=getsection',
  data:{forum:"亿航"},
  type:"post",
  success:function(data){
    output=[];
        $.each(data,function(key,value){
        output.push('<li><a href="action?url=WEB-INF/page/plate.jsp&forum=亿航">'+value+'</a></li>');
       });
       $("#yh").html(output.join(''));
       }
})
</script>
</html>