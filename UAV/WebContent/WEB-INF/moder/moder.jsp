<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UAV社区-版主管理界面</title>
	<link rel="stylesheet" href="layui/layui.css"  media="all">
	<link rel="stylesheet" type="text/css" href="lib4/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="fonticon/font-awesome.css">
  <link rel="stylesheet" type="text/css" href="layui/global.css">
 <style type="text/css">
   html,body{
	margin: 0px;
	padding: 0px;
	height: 100%;
	overflow:auto;
}
</style>
</head>
<body>
<jsp:include page="../view/nav.jsp" flush="true"/>

  <iframe src="action?url=/WEB-INF/view/moder.html" style="width: 100%;height: 100%"></iframe>

</body>
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
</html>