<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UAV-查看作品</title>
	<link rel="stylesheet" href="layui/layui.css"  media="all">
	<link rel="stylesheet" type="text/css" href="lib4/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="fonticon/font-awesome.css">
	<link rel="stylesheet" type="text/css" href="layui/global.css">
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
  <div class="col-md-8" style="padding-right:0px"><div class="layui-row layui-col-space15">
    
      <div class="fly-panel detail-box">
        <h3>${detailwork[0].title }</h3>
        
         <div class="detail-body photos" id="detailwork">

         
       
        </div>
        <video style="width:720px" src="UpAdown?method=downwork&pathname=${detailwork[0].workpath } " controls></video>
      </div>

      <div class="fly-panel detail-box" id="flyReply">
        <fieldset class="layui-elem-field layui-field-title" style="text-align: center;">
          <legend>回帖</legend>
        </fieldset>

        <ul class="jieda" id="mydiscuss">
         

        </ul>
        
        <div class="layui-form layui-form-pane">
          <form action="UserServlet?method=addpost&pid=${requestScope.detailpost[0].pid }" method="post">
            <div class="layui-form-item layui-form-text">
              <a name="comment"></a>
              <div class="layui-input-block">
                <textarea id="L_content" name="content" required lay-verify="required" placeholder="请输入内容"  class="layui-textarea fly-editor" style="height: 150px;"></textarea>
              </div>
            </div>
            <div class="layui-form-item">
              <input type="hidden" name="jid" value="123">
              <button class="layui-btn" lay-filter="*" lay-submit>提交回复</button>
            </div>
          </form>
        </div>
      </div>
    
    </div>
    </div>
  <div class="col-md-4">
  <div style="width:100%;height:400px;">
      <div class="layui-col-md12">
      <div class="layui-card">
          <div class="layui-card-body">
            <div class="detail-about">
            <a class="fly-avatar" href="action?url=WEB-INF/page/seeuser.jsp&uid=${detailwork[0].uid }">
            <img src="UpAdown?method=uidheadimg&uid=${detailwork[0].uid }" alt="">
            </a>
            <div class="fly-detail-user">
            <a href="action?url=WEB-INF/page/seeuser.jsp&uid=${detailwork[0].uid }" class="fly-link">
              <cite>${detailwork[0].nick }</cite>
            </a>           
           </div>
           <div class="detail-hits" id="LAY_jieAdmin" data-id="123">
             <span>${requestScope.detailwork[0].date }</span>
           </div>
              <span class="fly-list-nums"> 
              <a href="#comment"><i class="iconfont" title="回复">&#xe60c;</i> 66</a>
              <i class="layui-icon" title="点赞数">&#xe6c6;</i>${requestScope.detailwork[0].like }
             </span>
           </div>
        
        
        </div>
    </div>
    </div>
    <div class="layui-btn-group">
    <button class="layui-btn" onclick="collect()"><i class="layui-icon">&#xe600;</i>收藏</button>
    <button class="layui-btn " onclick="like()"><i class="layui-icon">&#xe6c6;</i>点赞</button>
    <a href="#L_content"><button class="layui-btn"><i class="layui-icon">&#xe642;</i>评论</button></a>
    <button class="layui-btn" onclick="share()"><i class="layui-icon">&#xe641;</i>分享</button>
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
$('#work').addClass('layui-this').siblings().removeClass('layui-this');
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
var data=' ${requestScope.detailwork[0].content }';
$('#detailwork').html(data);
</script>
<script type="text/javascript">

  layui.use('flow', function(){
  var $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
  var flow = layui.flow;
  var pid='${detailpost[0].pid}';
  flow.load({
    elem: '#mydiscuss' //指定列表容器
    ,isAuto:false
    ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
      var lis = [];
      //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
      $.post('UserServlet?method=mydiscuss&pid=${detailpost[0].pid} &page='+page, function(res){
        //假设你的列表返回在data集合中
        
        layui.each(res.data, function(index, item){
          console.log(item);
          lis.push('<li><div class="detail-about detail-about-reply"><a class="fly-avatar" href=""><img src="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg" alt=" "></a><div class="fly-detail-user"><a href="" class="fly-link"><cite>'+item.uid+'</cite></a></div><div class="detail-hits"><span>'+item.time+'</span></div></div><div class="detail-body jieda-body photos"><p>'+item.discuss+'</p></div></li>');
        }); 
         next(lis.join(''), page < res.count); 
      });
    }
  });
});
</script>
<script type="text/javascript">
function collect(){
	var pid='${detailpost[0].pid}';
	var uid='${sessionScope.uid}';
	$.ajax({
		url:'UserServlet?method=collect',
		data:{pid:pid,uid:uid},	

			success:function(data,textStatus){
				alert(data);
		}
	
	})
}
function like(){
	var pid='${detailpost[0].pid}';
	$.ajax({
		url:'UserServlet?method=like',
		data:{pid:pid},
		
			success:function(data,textStatus){
				console.log(data);
				alert(data);
			
		}
		
	})
}
function share(title,url,picurl){
	var url = document.URL;
	var sharesinastring='http://v.t.sina.com.cn/share/share.php?url='+url+'&content=utf-8&sourceUrl='+url+'&pic='+picurl;
	window.open(sharesinastring,'newwindow','height=400,width=400,top=100,left=100');
	}
</script>
</html>