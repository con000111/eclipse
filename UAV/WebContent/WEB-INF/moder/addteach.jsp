<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UAV-发布教程</title>
       <link rel="stylesheet" type="text/css" href="layui/layui.css">
       <link rel="stylesheet" type="text/css" href="layui/global.css">
</head>
<body>
<jsp:include page="../view/nav.jsp" flush="true"/>

<div class="layui-container fly-marginTop">
  <div class="fly-panel" pad20 style="padding-top: 5px;">
    <div class="layui-form layui-form-pane">
      <div class="layui-tab layui-tab-brief" lay-filter="user">
        <ul class="layui-tab-title">
          <li class="layui-this">发布教程</li>
        </ul>
        <div class="layui-form layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
          <div class="layui-tab-item layui-show">
            <form action="Moder?method=addteach" method="post">
             
              <div class="layui-row layui-col-space15 layui-form-item" >
                  
                 <div class="layui-col-md9">
                  <label for="L_title" class="layui-form-label">标题</label>
                  <div class="layui-input-block">
                    <input type="text" id="L_title" name="title" required lay-verify="required" autocomplete="off" class="layui-input">
                  </div>
                </div>
              </div>
              <div class="layui-form-item layui-form-text">
                <div class="layui-input-block">
                  <textarea id="L_content" name="content" lay-verify="required" placeholder="详细描述" class="layui-textarea fly-editor" style="height: 260px;"></textarea>
                </div>
              </div>
              <div class="layui-form-item">
                <button type="button" class="layui-btn" id="test1" >
                    <i class="layui-icon">&#xe67c;</i>上传视频
                </button> 
                <button class="layui-btn" lay-filter="*" >立即发布</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
<script type="text/javascript">
layui.use(['layedit','upload'], function(){
	  var layedit = layui.layedit;
	  var upload=layui.upload;
	  var index=layedit.build('L_content',{
	    uploadImage:{
	      url:'UpAdown?method=uppostimgs',
	    }
	  })
})
</script>
<script>
layui.use(['upload','layer'], function(){
  var upload = layui.upload;
  var layer=layui.layer;
   
  //执行实例
  var uploadInst = upload.render({
    elem: '#test1' //绑定元素
    ,url: '/UAV/UpAdown?method=upwork' //上传接口
    ,accept:'video'
    ,size:30000
    ,done: function(res){
      layer.msg(res.msg);
    }
    ,error: function(){
    	 layer.msg("未知错误");
    }
  });
});
</script>

</html>