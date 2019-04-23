<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>UAV-教程</title>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
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
<style type="text/css">
.layui-nav .layui-nav-child dd:hover ul{
display:block;
position:absolute;
top:0px;
left:80px;
background:#dddddd;
box-shadow:0 2px 4px rgba(0,0,0,.12);
border:1px solid #d2d2d2;
border-radius:2px
}
.layui-nav .layui-nav-child dd ul{display:none;}
.layui-nav .layui-nva-child dd ul a:hover{background:#f0f0f0;}
</style>
</head>
<body>
 <jsp:include page="/WEB-INF/view/nav.jsp" flush="true"/>
 
 
 
 
  <div style="margin-top:8px ;"></div>
  <div class="container" style="background-color: #F2F2F2;">
    <div class="row" >
      <div class="col-md-8" style="padding-right:0px">

       <div class="fly-panel" style="margin-bottom: 0;">

        <div class="fly-panel-title fly-filter">
          <a href="action?url=WEB-INF/page/forum.jsp" class="layui-this">综合</a>
        </div>

        <ul class="fly-list" id="flow-father">   
         <c:forEach var="U" items="${teach}"  > 
         <li style="height:5%">
         <a href="action?url=WEB-INF/page/seeuser.jsp&uid=${U.uid }" class="fly-avatar">
         <img src="UpAdown?method=uidheadimg&uid=${U.uid }" alt="">
         </a>
         <h2><a class="layui-badge">UAV教程</a><a href="UserServlet?method=seeteach&tid=${U.pid }">${U.subjects }</a></h2>
         <div class="fly-list-badge"></div><div class="fly-list-info"><a href="action?url=WEB-INF/page/seeuser.jsp&uid=${U.uid }" link><cite>${U.nick }</cite></a><span>${U.date }</span></div>
         </li>
         </c:forEach> 
        </ul>

    </div>


  </div>
  <div class="col-md-4">
    
      <div class="layui-card">
        <div class="layui-card-header">发帖</div>
        <div class="layui-card-body" style="height: 200px;background-repeat: round;">
         
         <a href="action?url=WEB-INF/page/addpost.jsp" targe="_blank"> <button class="layui-btn layui-btn-radius layui-btn-lg" style="width: 85%;margin-left: 25px "><i class="layui-icon" style="font-size: 30px;">&#xe642;</i>&nbsp&nbsp点击发帖</button></a>

          <p style="text-align: center;margin:20px 0 12px 0">今日发帖数</p>
          <span style="font-size: 40px;margin-left: 30%" id="postcount"></span>
        </div>
      </div>
      <div class="fly-panel fly-rank fly-rank-reply" id="LAY_replyRank">
        <h3 class="fly-panel-title">板块推荐</h3>
        <dl>
          <dd> 
            <a href="action?url=WEB-INF/page/plate.jsp&forum=御系列">
            <button class="layui-btn layui-btn-radius">御系列</button>
            </a>
          </dd>
          <dd>
            <a href="action?url=WEB-INF/page/plate.jsp&forum=精灵系列">
              <button class="layui-btn layui-btn-radius">精灵系列</button>
            </a>
          </dd>
           <dd>
            <a href="action?url=WEB-INF/page/plate.jsp&forum=亿航">
               <button class="layui-btn layui-btn-radius">亿航</button>
            </a>
          </dd>
           <dd>
            <a href="action?url=WEB-INF/page/plate.jsp&forum=晓Spark">
               <button class="layui-btn layui-btn-radius">晓Spark</button>
            </a>
          </dd>
          
         
          <dd>
            <a href="action?url=WEB-INF/page/plate.jsp&forum=DOBBY">
               <button class="layui-btn layui-btn-radius">DOBBY</button>
            </a>
          </dd>
          <dd>
            <a href="action?url=WEB-INF/page/plate.jsp&forum=DOLLCAP">
               <button class="layui-btn layui-btn-radius">DOLLCAP</button>
            </a>
          </dd>
        </dl>
      </div>
      
  </div>
</div>
</div>





</div>
</body>
<script type="text/javascript">

$('#forbid').addClass('layui-this').siblings().removeClass('layui-this');

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


</html>