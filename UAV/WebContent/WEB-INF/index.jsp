<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
</style>
<style type="text/css"> 
.flow{
  padding: 15px;
  width: 100%;
  background: white;
}
.flow-first{
  width: 100%;
  height:200px;

}
.flow div div img{
  width: 100%;
  height: 200px;
  border-radius: 10px;
 
 background-attachment:scroll;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat; 
}
</style>

</head>
<body>
<jsp:include page="/WEB-INF/view/nav.jsp" flush="true"/>

<div style="margin-top:10px ;"></div>
<div class="container" style="background-color: #F2F2F2;">
  <div class="layui-carousel" id="test10">
  <div carousel-item="" id=carousel>
  <c:forEach var="U" items="${carousel}"  > 
    <div style="width:100%;height:360px"><a href="UserServlet?method=detailpost&where=detail&pid=${U.pid }" target="_blank"><img src="UpAdown?method=postimgs&pathname=${U.postimg }" style="width:100%;height:360px"><cite>${U.subjects }</cite></a></div>
  </c:forEach>
  </div>
  </div>



<div style="margin-top:8px ;"></div>


<div class="row">
  <div class="col-md-8" style="padding-right:0px">
  	
     <div class="fly-panel" style="margin-bottom: 0;">
        
        <div class="fly-panel-title fly-filter">
          <a href="" class="layui-this">综合</a>
<!--           <span class="fly-filter-right layui-hide-xs">
            <a href="" class="layui-this">按最新</a>
            <span class="fly-mid"></span>
            <a href="">按热议</a>
          </span> -->
        </div>	   
        
 <div class="flow" id="flow-father">
  
</div>

        


      </div>
   

  </div>
  <div class="col-md-4">
   <div class="layui-card">
        <div class="layui-card-header">置顶帖</div>
        <div class="layui-card-body" style="height: 200px;background-repeat: round;">
        <ul class="fly-list" >
        <c:forEach var="T" items="${toppost }" >
          <li style="padding:0px;height:30px">
          <h2>
          <a class="layui-badge" target="_blank" href="action?url=WEB-INF/page/plate.jsp&forum=${T.forum }">${T.forum }</a>
          <a href="UserServlet?method=detailpost&pid=${T.pid }&where=detail">${T.subjects }
          </a>
          </h2>
          </li>
          </c:forEach>
       </ul>
        </div>
      </div>
      <div class="layui-card">
        <div class="layui-card-header">每日签到</div>
        <div class="layui-card-body" style="height: 200px;background-repeat: round;">
          <button type="button" id="punch" class="btn btn-success" style="width: 100%;display: block;"><i class="icon-calendar icon-2x"></i>&nbsp&nbsp点击签到
          </button>
          <button type="button" id="punched" class="layui-btn layui-btn-disabled" style="width: 100%;display: none;"><i class="icon-calendar icon-2x"></i>&nbsp&nbsp今日已签到
          </button>
          <p style="text-align: center;margin:20px 0 12px 0">今日签到人数</p>
          <span style="font-size: 40px;margin-left: 30%"><%=request.getAttribute("count") %></span>
          <p style="text-align: center;margin:20px 0 12px 0">已连续打卡<span>${requestScope.sum }</span>天</p>
          
        </div>
      </div>
      
       
      
      <div class="fly-panel fly-rank fly-rank-reply" id="LAY_replyRank">
        <h3 class="fly-panel-title">大神榜</h3>
        <dl id="rank">
 
       
        </dl>
      </div>
    </div>
   
  </div>
</div>


</div>


</body >
<script src="layui/layui.js" charset="utf-8" id="js-layui"></script>
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
<script type="text/javascript" src="lib4/bootstrap.bundle.js"></script>
<script type="text/javascript">
  layui.use(['layer', 'form'],
   function(){     
  var layer = layui.layer
  ,form = layui.form;
  layer.msg('欢迎来到UAV社区');
});
  
  $("#punch").click(function(){ 
	  var uid='${sessionScope.uid}'
	  if(uid==0){
		  layer.msg('还没登陆呢');
	  }else{
		    document.getElementById("punch").style.display="none";
		    document.getElementById("punched").style.display="block";
		    $.ajax({
		    	
		        url:"UserServlet?method=spunch&sum=<%=request.getAttribute("sum") %>",
		        statusCode:{
		        404:function(){
		          alert("404");
		        },
		        500:function(){
		          alert("500");
		        }
		        
		      },
		      success:function(data,textStatus){
		       layer.msg('签到成功');
		      }
		  })
	  }

  });


</script>
<script>

var judpun="<%=request.getAttribute("punch")%>";

if(judpun=="yes"){
  document.getElementById("punch").style.display="none";
  document.getElementById("punched").style.display="block";
}
</script>


<script type="text/javascript">
	
layui.use('flow', function(){
  var $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
  var flow = layui.flow;
  flow.load({
    elem: '#flow-father' //指定列表容器
    ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
      var lis = [];
      //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
      $.get('Preloads?method=flow&page='+page, function(res){
        //假设你的列表返回在data集合中
        
        layui.each(res.data, function(index, item){
          console.log(item);
          lis.push('<div style="margin-bottom: 20px;"><div class="flow-first"><img src="UpAdown?method=postimgs&pathname='+item.postimg+'"></div><a href="UserServlet?method=detailpost&where=detail&pid='+item.pid+'" target="_blank"><h5 style="margin-bottom:0px">'+item.subjects+'</h5></a><a><small style="font-weight: bold;color: #7c7c7c;">'+item.forum+'</small></a>&nbsp/&nbsp<span><a style="font-size:15px">'+item.nick+'</a><li style="font-size:15px">发表于：'+item.date+'</li></span></div>');
        }); 
        
        //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
        //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
        next(lis.join(''), page < res.count);  
       
      });
    }
  });
});

</script>
<script>
$('#index').addClass('layui-this').siblings().removeClass('layui-this');
layui.use(['carousel', 'form'], function(){
  var carousel = layui.carousel
  ,form = layui.form;
  carousel.render({
	    elem: '#test10'
	    ,interval: 3000
	    ,anim: 'fade'
	    ,height: '360px'
	    ,width:'100%'
	  });
  form.on('switch(autoplay)', function(){
	    ins3.reload({
	      autoplay: this.checked
	    });
	  });
})
</script>
</html>       
       