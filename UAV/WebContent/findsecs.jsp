<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UAV-安全中心</title>
</head>
<body>
<jsp:include page="WEB-INF/view/nav.jsp" flush="true"/>
<div class="layui-container fly-marginTop">
<div class="fly-panel fly-panel-user" pad20>
<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
  <ul class="layui-tab-title">
    <li>找回密码</li>
  </ul>
  <div class="layui-tab-content" style="height: 100px;">
    
    
    <div class="layui-tab-item">
     <div class="layui-form layui-form-pane">
      <form method="post" action="UserServlet?method=modif">
              <div class="layui-form-item">
                <label for="sL_email" class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                  <input type="text" value="${user.email }" id="sL_email" name="email" required lay-verify="email" autocomplete="off" class="layui-input">
                </div>
                 <button class="layui-btn" onclick="sendcode()">发送验证码</button>
              </div>
              <div class="layui-form-item">
                <label for="sL_vercode" class="layui-form-label">人类验证</label>
                <div class="layui-input-inline">
                  <input type="text" id="sL_vercode" name="vercode" required lay-verify="required" placeholder="请输入邮件验证码 " autocomplete="off" class="layui-input">
                </div>
                <button class="layui-btn" alert="1" lay-filter="*" lay-submit>找回</button>
              </div>   
            </form>
             </div>
    
    
    </div>
   

  </div>
</div> 
</div>
</div>
</body>
</html>
<script>
layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form
	  ,layer = layui.layer
})
function sendcode(){
	var emil=$('#fL_email').val();
	$.ajax({
		url:'Sendmail',
		type:'post',
		data:{email:emil},
		success:function(data){
			alert(data);
		}
	})
}

</script>