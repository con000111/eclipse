<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UAV-消息查看</title>
</head>
<body>
<jsp:include page="../view/nav.jsp" flush="true"/>
<div class="container" style="background-color: #F2F2F2;">
<div style="padding: 20px; background-color: #F2F2F2;">
  <div class="layui-row layui-col-space15" id="mymessage">   
 
  </div>
</div> 
</div>
</body>
<input type=hidden value="" id="myhidden">
<!-- Modal -->
<div class="modal fade" id="chatmodal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header" style="padding-bottom:0px">
          <p>发送留言</p>
      </div>
      <div class="modal-body">
      
      	  <textarea rows="5" cols="63" id="message">
      	  </textarea>
      
      </div>
      <div class="modal-footer">
        <button type="button" id="mycancel" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" onclick="send()" class="btn btn-primary">发送</button>
      </div>

    </div>
  </div>
</div>

</html>
<script>
function recall(e){
	 $("#myhidden").attr("value",e);
}
function send(){
	var toid=$("#myhidden").val();
	var text=$("#message").val().trim();
	$.ajax({
		url:'UserServlet?method=mess',
		type:'post',
		data:{content:text,toid:toid},
		success:function(data){
			alert(data);
		}
	});
	$("#mycancel").click();
}
$.ajax({
	url:'UserServlet?method=newmess',
	success:function(data){
		output=[];
        $.each(data,function(key,value){
        output.push('<div class="layui-col-md6"><div class="layui-card"><div class="layui-card-header"><a href="action?url=WEB-INF/page/seeuser.jsp&uid='+value.fromid+'">from:'+value.nick+'</a></div><div class="layui-card-body" style="height:80px">'+value.content+'<br><span>'+value.date+'</span><button class="layui-btn" style="float:right" data-toggle="modal" onclick="recall('+value.fromid+')" data-target="#chatmodal">回复</button></div></div></div>');
       });
       $("#mymessage").html(output.join(''));
       }
})

</script>
