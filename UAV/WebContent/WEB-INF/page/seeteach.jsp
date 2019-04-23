<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>UAV-查看教程</title>
	<meta charset="utf-8">
	<link rel="stylesheet" href="layui/layui.css"  media="all">
<style type="text/css">
html, body {
    background: url(img/back.png);
    background-repeat: round;
}</style>
</head>
<body>
<jsp:include page="/WEB-INF/view/nav.jsp" flush="true"/>

<div style="margin-top:8px ;"></div>
  <div class="container" style="background-color: #F2F2F2;">
    <div class="row" >
  <div class="col-md-8" style="padding-right:0px;margin:auto"><div class="layui-row layui-col-space15">
    
      <div class="fly-panel detail-box">
        <h1>${detteach.subjects }</h1>
         <div class="detail-about">
            <a class="fly-avatar" href="action?url=WEB-INF/page/seeuser.jsp&uid=${detteach.uid }">
            <img src="UpAdown?method=uidheadimg&uid=${detteach.uid }" alt="">
            </a>
            <div class="fly-detail-user">
            <a href="action?url=WEB-INF/page/seeuser.jsp&uid=${detteach.uid }" class="fly-link">
              <cite>${detteach.nick }</cite>
            </a>           
           </div>
           <div class="detail-hits" id="LAY_jieAdmin" data-id="123">
             <span>${detteach.date }</span>
           </div>              
           </div> 
         <div class="detail-body photos" id="detailpost">

         

        </div>
        <video style="width:720px" src="UpAdown?method=downwork&pathname=${detteach.postimg } " controls></video>
      </div>

      <div class="fly-panel detail-box" id="flyReply">
        <fieldset class="layui-elem-field layui-field-title" style="text-align: center;">
          <legend>结束</legend>
        </fieldset>

       
      </div>
    
    </div>
    </div>
  
</div>
 </div> 
  
 
      

</body>
<script src="lib4/jquery-1.9.1.js"></script>

<script type="text/javascript" src="lib4/bootstrap.bundle.js"></script>
<script src="layui/layui.js" charset="utf-8" id="js-layui"></script>
<script>
$('#forbid').addClass('layui-this').siblings().removeClass('layui-this');
  layui.use('element', function(){
  var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
  
  //监听导航点击
  element.on('nav(demo)', function(elem){
    //console.log(elem)
    layer.msg(elem.text());
  });
});
</script>
<script type="text/javascript">
var data=' ${detteach.content }';
$('#detailpost').html(data);
</script>

</html>