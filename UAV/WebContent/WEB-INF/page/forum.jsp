<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>UAV-论坛</title>
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
 <jsp:include page="/WEB-INF/view/nav.jsp" flush="true"/>
 
 
 
 
  <div style="margin-top:8px ;"></div>
  <div class="container" style="background-color: #F2F2F2;">
    <div class="row" >
      <div class="col-md-8" style="padding-right:0px">

       <div class="fly-panel" style="margin-bottom: 0;">

        <div class="fly-panel-title fly-filter">
          <a href="action?url=WEB-INF/page/forum.jsp" class="layui-this">按时间</a>
          <span class="fly-mid"></span>
          <a href="action?url=WEB-INF/page/forumhot.jsp">按热议</a>

        </div>

        <ul class="fly-list" id="flow-father">    
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
		      $.post('Preloads?method=timepost&page='+page, function(res){
		        //假设你的列表返回在data集合中
		        
		        layui.each(res.data, function(index, item){
		          console.log(item);
		          lis.push('<li style="height:5%"><a href="action?url=WEB-INF/page/seeuser.jsp&uid='+item.uid+'" class="fly-avatar"><img src="UpAdown?method=uidheadimg&uid='+item.uid+'" alt=""></a><h2><a class="layui-badge">'+item.forum+'</a><a href="UserServlet?method=detailpost&pid='+item.pid+'&where=detail">'+item.subjects+'</a></h2><div class="fly-list-badge"></div><div class="fly-list-info"><a href="action?url=WEB-INF/page/seeuser.jsp&uid='+item.uid+'" link><cite>'+item.nick+'</cite></a><span>'+item.date+'</span><i class="layui-icon" title="点赞数">&#xe6c6 '+item.like+'</i></div></li>');
		        }); 
		        next(lis.join(''), page < res.count);  
		      });
		    }
		  });
		});

</script>
<script type="text/javascript">
$('#myforum').addClass('layui-this').siblings().removeClass('layui-this');
  $.ajax({
    url:'Preloads?method=postcount',
    type:'post',
    success:function(data) {
       $('#postcount').html(data);
      }
  });
</script>


</html>