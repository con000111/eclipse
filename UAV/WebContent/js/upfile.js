 layui.use('upload', function(){
 var $ = layui.jquery
 ,upload = layui.upload;
 //拖拽上传
 upload.render({
	    elem: '#test1'
	    ,url: '/UAV/UpAdown?method=uppic&which=test1'
	    ,size: 2048 //限制文件大小，单位 KB
	    ,choose:function(obj){
	    	obj.preview(function(index,file,result)		
	    		{  
	    		
	    			document.getElementById("demo").src = result;
	    			document.getElementById("demo").style.display='block';
	    			document.getElementById("demo1").style.display='none';
	                document.getElementById("demo2").style.display='none';
	                document.getElementById("demo3").style.display='block';
	    		});
	    }
	    ,done: function(res){
	      console.log(res)
	    }
	  });
  upload.render({
    elem: '#test2'
    ,url: '/UAV/UpAdown?method=uppic&which=test2'
    ,size: 2048 //限制文件大小，单位 KB
    ,before:function(obj){
    	
    	obj.preview(function(index,file,result)
    		{   
    			document.getElementById("2demo").src = result;
    			document.getElementById("2demo").style.display='block';
    			document.getElementById("2demo1").style.display='none';
                document.getElementById("2demo2").style.display='none';
                document.getElementById("2demo3").style.display='block';
    		});
    }
    ,done: function(res){
      console.log(res)
    }
  });
  upload.render({
	    elem: '#test3'
	    ,url: '/UAV/UpAdown?method=uppic&which=test3'
	    ,size: 2048 //限制文件大小，单位 KB
	    ,before:function(obj){
	    	
	    	obj.preview(function(index,file,result)
	    		{  
	    			document.getElementById("3demo").src = result;
	    			document.getElementById("3demo").style.display='block';
	    			document.getElementById("3demo1").style.display='none';
	                document.getElementById("3demo2").style.display='none';
	                document.getElementById("3demo3").style.display='block';
	    		});
	    }
	    ,done: function(res){
	      console.log(res)
	    }
	  });
  upload.render({
	    elem: '#test4'
	    ,url: '/UpAdown?method=uppic&which=test4'
	    ,size: 2048 //限制文件大小，单位 KB
	    ,before:function(obj){
	    	
	    	obj.preview(function(index,file,result)
	    		{   
	    			document.getElementById("4demo").src = result;
	    			document.getElementById("4demo").style.display='block';
	    			document.getElementById("4demo1").style.display='none';
	                document.getElementById("4demo2").style.display='none';
	                document.getElementById("4demo3").style.display='block';
	    		});
	    }
	    ,done: function(res){
	      console.log(res)
	    }
	  });
  upload.render({
	    elem: '#test5'
	    ,url: '/UAV/UpAdown?method=uppic&which=test5'
	    ,size: 2048 //限制文件大小，单位 KB
	    ,before:function(obj){
	    	
	    	obj.preview(function(index,file,result)
	    		{  
	    			document.getElementById("5demo").src = result;
	    			document.getElementById("5demo").style.display='block';
	    			document.getElementById("5demo1").style.display='none';
	                document.getElementById("5demo2").style.display='none';
	                document.getElementById("5demo3").style.display='block';
	    		});
	    }
	    ,done: function(res){
	      console.log(res)
	    }
	  });

	
})
var test1=document.getElementById('demo');
var test2=document.getElementById('2demo');
var test3=document.getElementById('3demo');
var test4=document.getElementById('4demo');
var test5=document.getElementById('5demo');
if(test1.src!=null){
		document.getElementById("demo").style.display='block';
	    document.getElementById("demo1").style.display='none';
	    document.getElementById("demo2").style.display='none';
	    document.getElementById("demo3").style.display='block';
}
if(test2.src!=null){
	document.getElementById("2demo").style.display='block';
    document.getElementById("2demo1").style.display='none';
    document.getElementById("2demo2").style.display='none';
    document.getElementById("2demo3").style.display='block';
}
if(test3.src!=null){
	document.getElementById("3demo").style.display='block';
    document.getElementById("3demo1").style.display='none';
    document.getElementById("3demo2").style.display='none';
    document.getElementById("3demo3").style.display='block';
}
if(test4.src!=null){
	document.getElementById("4demo").style.display='block';
    document.getElementById("4demo1").style.display='none';
    document.getElementById("4demo2").style.display='none';
    document.getElementById("4demo3").style.display='block';
}
if(test5.src!=null){
	document.getElementById("5demo").style.display='block';
    document.getElementById("5demo1").style.display='none';
    document.getElementById("5demo2").style.display='none';
    document.getElementById("5demo3").style.display='block';
}else{
	document.getElementById("5demo").style.display='none';
    document.getElementById("5demo1").style.display='block';
    document.getElementById("5demo2").style.display='block';
    document.getElementById("5demo3").style.display='none';
}