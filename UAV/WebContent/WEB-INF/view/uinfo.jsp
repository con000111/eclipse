<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();//
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<meta charset="utf-8">
</head>
<link rel="stylesheet" type="text/css" href="../../lib4/bootstrap.css">
<style type="text/css">
	.radio-check {
		position: relative;
		height: 35px;
	}
	.radio-check > input {
		position: absolute;
		left: 0;
		top: 0;
		width: 20px;
		height:20px;
		opacity: 0;
		z-index:99;
	}
	.radio-check > label {
		position: absolute;
		left: 30px;
		line-height: 20px;
		top: 0px;
	}

	.radio-check > label:before {
		content: '';
		position: absolute;
		left: -30px;
		top: 0px;
		display: inline-block;
		width: 20px;
		height: 20px;
		border-radius: 50%;
		border: 1px solid #ddd;
		transition: all 0.3s ease;
		-webkit-transition: all 0.3s ease;
		-moz-transition: all 0.3s ease;
	}
	.radio-check > label:after {
		content: '';
		position: absolute;
		left: -30px;
		top: 0px;
		display: inline-block;
		width: 10px;
		height: 10px;
		border-radius: 50%;
		margin-top: 6px;
		margin-left: 6px;
		transition: all 0.3s ease;
		-webkit-transition: all 0.3s ease;
		-moz-transition: all 0.3s ease;
	}

	.radio-check input[type='radio']:checked + label:before {
		border-color: #4cd764;
	}
	.radio-check input[type='radio']:checked + label:after {
		background: #4cd764;
	}
</style>

<style type="text/css">/*//头像CSS*/
	#headimg{
		width: 100px;
		height: 100px;

	}
	div a span{
    border-radius: 0 0 7px 7px;
    display: block;
    opacity: 0;
    margin-top: 80px;
    position: absolute;
    background: rgba(0, 0, 0, 0.25);
	  z-index: 2;
  	font-size: 14px;
  	text-align:center;
  	transition: opacity 1s ease-in-out;
  	margin-left: 20px;
  	color: white;
	}
	div a:hover span {
		opacity: 1
	}
	
</style>
<body>
	<div class="input-group-prepend">
          
          <a style="height: 110px;width: 110px;display: block;" href="#"  data-toggle="modal" data-target="#exampleModal"><img id="headimg" style="border-radius: 50%;" src="UserServlet?method=getheadimg" onerror="this.src='img/2.jpg'" >
    <span style="margin-top:-20px">编辑头像</span></a>
    </div>
<form action="UserServlet?method=alterinfo" method="post">
    <div class="col-sm-3 my-1">
        <div class='radio-check'>
    	   
		    <input type='radio' name='sex' id='sex1' value="男" checked />
		    <label for='test2' class>男</label>
		</div>
		<div class='radio-check'>
		    <input type='radio' name='sex' id='sex2' value="女" />
			<label for='test1' class>女</label>
		</div>
      
      <div class="input-group">
        <div class="input-group-prepend">
          <div class="input-group-text">昵称</div>
        </div>
        <input name="nick" type="text" class="form-control" id="inlineFormInputGroupUsername" placeholder="昵称" value="${sessionScope.userinfo.nick }" maxlength="5">
      </div>
    
    </div>
      <div class="col-sm-3 my-1">
      <div class="input-group">
        <div class="input-group-prepend">
          <div class="input-group-text">城市</div>
        </div>
        <input name="address" type="text" class="form-control" id="inlineFormInputGroupUsername" placeholder="请填写所在城市" value="${sessionScope.userinfo.address }">
      </div>
      </div>
        
  <div class="form-group" style="margin-left:15px">
    <div class="input-group-text" style="width: 9%">个性签名：</div>
    <textarea name=sign class="form-control" id="exampleFormControlTextarea1" rows="3">${sessionScope.userinfo.sign }</textarea>
  </div>
  <input type="submit" value="修改信息" class="btn btn-primary">
</form>
</body>
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">上传头像</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<!-- 内容开始 -->
      <img id="headimg" style="width:100px;height:100px;border-radius: 300px;" src="UserServlet?method=getheadimg" >
       <input type="file" name="file" id = "input_file" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" onchange="imgPreview(this)" >  
       <img id="preview" style="width:100px;height:100px;border-radius: 300px;"/>

            <!-- 内容结束 -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" onclick="mysubmit()" class="btn btn-primary">上传</button>
      </div>

    </div>
  </div>
</div>
<script src="../../lib4/jquery-1.9.1.js"></script>
<script src="../../lib4/bootstrap.min.js"></script>
</html>
<script type="text/javascript">
  function imgPreview(fileDom) {
    //判断是否支持FileReader
    if(window.FileReader) {
        var reader = new FileReader();
    } else {
        alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
    }
    //获取文件
    var file = fileDom.files[0];
    var imageType = /^image\//;
    //是否是图片
    if(!imageType.test(file.type)) {
        alert("请选择图片！");
        return;
    }
    //读取完成
    reader.onload = function(e) {
        //获取图片dom
        var img = document.getElementById("preview");
        //图片路径设置为读取的图片
        img.src = e.target.result;
    };
    reader.readAsDataURL(file);
}

function mysubmit(){
  var formData = new FormData(); 
  
  var name=document.getElementById('input_file').value;
  var index=name.lastIndexOf('.');
  var suffix=name.substring(index);
  var file=${sessionScope.userinfo.uid}+'headimg';
  console.log(file+suffix);
formData.append('file', $('#input_file')[0].files[0],$('#input_file')[0].files[0].name=file+suffix);  //添加图片信息的参数
/* formData.append('sizeid',123);  */ //添加其他参数
$.ajax({
    url: '<%=path %>/UserServlet?method=upheadimg',
    type: 'POST',
    cache: false, //上传文件不需要缓存
    data: formData,
    processData: false, // 告诉jQuery不要去处理发送的数据
    contentType: false, // 告诉jQuery不要去设置Content-Type请求头
    statusCode:{
		404:function(){
			alert("404");
		},
		500:function(){
			alert("500");
		}
		
	},
	
    success:function(data) {
    	 alert("上传成功");
    	 location.reload();
      },
      error: function (data) {
          alert("上传失败");
      }

    
}) 

}
</script>
<!-- 性别按钮的修正 -->

<script type="text/javascript">
var sex="${sessionScope.userinfo.sex }";
if(sex=='女'){
	document.getElementById('sex2').checked="checked";
}else {
	document.getElementById('sex1').checked="checked";
}

</script>
