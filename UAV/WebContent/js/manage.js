function user(){
  layui.use('table', function(){
  var table = layui.table; 
  table.render({
    elem: '#admin'
    ,url:'/UAV/Manage?method=queryuser'
    ,toolbar: '#toolbarDemo'
    ,title: '用户数据表'
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field:'uid', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
       ,{field:'username', title:'用户名', width:100, edit: 'text'}
      ,{field:'password', title:'密码', width:120, edit: 'text', sort: true}
      ,{field:'nick', title:'昵称', width:100,edit:'text'}
      ,{field:'sex', title:'性别' ,edit: 'text'}
      ,{field:'address', title:'地址', width:100, sort: true,edit: 'text'}
      ,{field:'sign', title:'签名', width:200,edit: 'text'}
      ,{field:'uauth', title:'权限', width:100, sort: true,edit: 'text'}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}   
    ]]
    ,id:'reload'
    ,page: true
  });
  
  //头工具栏事件
  table.on('toolbar(admin)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
      case 'getCheckData':
        var data = checkStatus.data;
        layer.alert(JSON.stringify(data));
      break;
      case 'getCheckLength':
        var data = checkStatus.data;
        layer.msg('选中了：'+ data.length + ' 个');
      break;
      case 'isAll':
        layer.msg(checkStatus.isAll ? '全选': '未全选');
      break;
    };
  });
  
  //监听行工具事件
  table.on('tool(admin)', function(obj){
    var data = obj.data;
    var uid=data.uid;
 
    if(obj.event === 'del'){
      layer.confirm('真的删除行么', function(index){
    	    $.ajax({
    	         url: 'Manage?method=deluser'
    	        ,data: {uid:uid}
    	        , success:function(res){
    	            console.log(res);
    	        }
            }),
        obj.del();
        layer.close(index);
      });
    } else if(obj.event === 'edit'){	
    	layer.confirm('真的更新么', function(index){
    	  $.ajax({
 	         url: 'Manage?method=upduser'
 	        ,type:'post'
 	        ,contentType:"application/x-www-form-urlencoded; charset=UTF-8"
 	        ,data: {
 	        	username:data.username,
 	        	password:data.password,
 	        	nick:data.nick,
 	        	sex:data.sex,
 	        	address:data.address,
 	        	sign:data.sign,
 	        	uauth:data.uauth,
 	        	fraction:data.fraction,
 	        	uid:data.uid}
    	    ,success:function(res){
	            console.log(res);
	        }
    	  }),
        layer.close(index);
      });
    }else if(obj.event=='add'){
        layui.use(['layer'],function () {
            var layer = layui.layer,$=layui.$;
            layer.open({
                type:1,//类型
                area:['400px','250px'],//定义宽和高
                title:'插入用户',//题目
                shadeClose:false,//点击遮罩层关闭
                content: $('#user')//打开的内容
            });
        })
    }
    
  });
});   
}



function post(){
	layui.use('table', function(){
  var table = layui.table;
  table.render({
    elem: '#admin'
    ,url:'/UAV/Manage?method=querypost'
    ,toolbar: '#toolbarDemo'
    ,title: '帖子表'
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field:'pid', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
      ,{field:'subjects', title:'主题', width:120, edit: 'text'}
      ,{field:'content', title:'内容', width:250, edit: 'text', sort: true}
      ,{field:'date', title:'时间', width:150,edit: 'text'}
      ,{field:'uid', title:'作者', width:100,edit: 'text'}
      ,{field:'forum', title:'板块', width:80, sort: true,edit: 'text'}
      ,{field:'like',title:'喜欢',width:80,sort:true,edit:'text'}
      ,{field:'grade',title:'级别',width:40,sort:true,edit:'text'}
      ,{field:'postimg',title:'图片地址',width:100,edit:'text'}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}   
    ]]
    ,page: true
  });
  
  //头工具栏事件
  table.on('toolbar(admin)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
      case 'getCheckData':
        var data = checkStatus.data;
        layer.alert(JSON.stringify(data));
      break;
      case 'getCheckLength':
        var data = checkStatus.data;
        layer.msg('选中了：'+ data.length + ' 个');
      break;
      case 'isAll':
        layer.msg(checkStatus.isAll ? '全选': '未全选');
      break;
    };
  });
  
  //监听行工具事件
  table.on('tool(admin)', function(obj){
    var data = obj.data;
    var pid=data.pid;
    //console.log(obj)
    if(obj.event === 'del'){
      layer.confirm('真的删除行么', function(index){
    	  $.ajax({
 	         url: 'Manage?method=delpost'
 	        ,data: {pid:pid}
 	        , success:function(res){
 	            console.log(res);
 	        }
         }),
        obj.del();
        layer.close(index);
      });
    } else if(obj.event === 'edit'){
    	layer.confirm('真的更新行么', function(index){
      	  $.ajax({
   	         url: 'Manage?method=updpost'
   	        ,type:'post'
   	        ,data: {
   	        	pid:data.pid,
   	        	subjects:data.subjects,
   	        	content:data.content,
   	        	date:data.date,
   	        	uid:data.uid,
   	        	forum:data.forum,
   	        	like:data.like,
   	        	grade:data.grade,
   	        	postimg:data.postimg	
   	        }
   	        , success:function(res){
   	            console.log(res);
   	        }
           }),
        layer.close(index);
      });
    }else if(obj.event=='add'){
    	$('#myhidden').click();
    }
  });
});

}


function discuss(){
  layui.use('table', function(){
  var table = layui.table;
  
  table.render({
    elem: '#admin'
    ,url:'/UAV/Manage?method=querydiscuss'
    ,toolbar: '#toolbarDemo'
    ,title: '评论表'
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field:'did', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
      ,{field:'uid', title:'发表帖子的用户名的id', width:100, edit: 'text'}
      ,{field:'discuss', title:'评论', width:200, edit: 'text', sort: true}
      ,{field:'likenum', title:'点赞数', width:100, edit: 'text'}
      ,{field:'pid', title:'评论人id', width:100, edit: 'text'}
      ,{field:'time', title:'评论时间', width:150, sort: true, edit: 'text'}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}   
    ]]
    ,page: true
  });
  
  //头工具栏事件
  table.on('toolbar(admin)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
      case 'getCheckData':
        var data = checkStatus.data;
        layer.alert(JSON.stringify(data));
      break;
      case 'getCheckLength':
        var data = checkStatus.data;
        layer.msg('选中了：'+ data.length + ' 个');
      break;
      case 'isAll':
        layer.msg(checkStatus.isAll ? '全选': '未全选');
      break;
    };
  });
  
  //监听行工具事件
  table.on('tool(admin)', function(obj){
    var data = obj.data;
    var did=data.did;
    //console.log(obj)
    if(obj.event === 'del'){
      layer.confirm('真的删除行么', function(index){
    	  $.ajax({
    		  url:'Manage?method=deldiscuss',
    		  data:{did:did},
    		  success:function(data){
    			  alert(data)
    		  }
    	  })
    	  
        obj.del();
        layer.close(index);
      });
    } else if(obj.event === 'edit'){
        
        $.ajax({
       	 url:'Manage?method=upddiscuss',
       	 type:'post',
       	 data:{
       		 did:data.did,
       		 uid:data.uid,
       		 discuss:data.discuss,
       		 likenum:data.likenum,
       		 pid:data.pid,
       		 time:data.time 
       	 },success:function(data){
       		 alert(data);
       	 }    		 
        })	  
        obj.update({
          email: value
        });
        alert(value);
        layer.close(index);
     
    }
  });
});
}




function usubmit(){
	layer.close(layer.index); 
	
	layui.use('table', function(){
		  var table = layui.table; 
		  var key=$('#ukey').val();
		  table.render({
		    elem: '#admin'
		    ,method:'post'
		    ,url:'/UAV/Manage?method=ukeyquery'
		    ,where: {key:key}
		    ,toolbar: '#toolbarDemo'
		    ,title: '用户数据表'
		    ,cols: [[
		      {type: 'checkbox', fixed: 'left'}
		      ,{field:'uid', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
		       ,{field:'username', title:'用户名', width:100, edit: 'text'}
		      ,{field:'password', title:'密码', width:120, edit: 'text', sort: true}
		      ,{field:'nick', title:'昵称', width:100,edit:'text'}
		      ,{field:'sex', title:'性别' ,edit: 'text'}
		      ,{field:'address', title:'地址', width:100, sort: true,edit: 'text'}
		      ,{field:'sign', title:'签名', width:200,edit: 'text'}
		      ,{field:'uauth', title:'权限', width:100, sort: true,edit: 'text'}
		      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}   
		    ]]
		    ,id:'reload'
		    ,page: true
		  });
		  
		  //头工具栏事件
		  table.on('toolbar(admin)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      case 'getCheckData':
		        var data = checkStatus.data;
		        layer.alert(JSON.stringify(data));
		      break;
		      case 'getCheckLength':
		        var data = checkStatus.data;
		        layer.msg('选中了：'+ data.length + ' 个');
		      break;
		      case 'isAll':
		        layer.msg(checkStatus.isAll ? '全选': '未全选');
		      break;
		    };
		  });
		  
		  //监听行工具事件
		  table.on('tool(admin)', function(obj){
		    var data = obj.data;
		    var uid=data.uid;
		 
		    if(obj.event === 'del'){
		      layer.confirm('真的删除行么', function(index){
		    	    $.ajax({
		    	         url: 'Manage?method=deluser'
		    	        ,data: {uid:uid}
		    	        , success:function(res){
		    	            console.log(res);
		    	        }
		            }),
		        obj.del();
		        layer.close(index);
		      });
		    } else if(obj.event === 'edit'){	
		    	layer.confirm('真的更新么', function(index){
		    	  $.ajax({
		 	         url: 'Manage?method=upduser'
		 	        ,type:'post'
		 	        ,contentType:"application/x-www-form-urlencoded; charset=UTF-8"
		 	        ,data: {
		 	        	username:data.username,
		 	        	password:data.password,
		 	        	nick:data.nick,
		 	        	sex:data.sex,
		 	        	address:data.address,
		 	        	sign:data.sign,
		 	        	uauth:data.uauth,
		 	        	fraction:data.fraction,
		 	        	uid:data.uid}
		    	    ,success:function(res){
			            console.log(res);
			        }
		    	  }),
		        layer.close(index);
		      });
		    }else if(obj.event=='add'){
		        layui.use(['layer'],function () {
		            var layer = layui.layer,$=layui.$;
		            layer.open({
		                type:1,//类型
		                area:['400px','250px'],//定义宽和高
		                title:'插入用户',//题目
		                shadeClose:false,//点击遮罩层关闭
		                content: $('#user')//打开的内容
		            });
		        })
		    }
		    
		  });
		});   
}
//条件查询帖子
function psubmit(){
	layer.close(layer.index); 	
	var key=$('#pkey').val();
	layui.use('table', function(){
		  var table = layui.table;
		  table.render({
		    elem: '#admin'
		    ,method: 'post'
		    ,url:'/UAV/Manage?method=pkeyquery'
		    ,where: {key:key}
		    ,toolbar: '#toolbarDemo'
		    ,title: '帖子数据表'
		    ,cols: [[
		      {type: 'checkbox', fixed: 'left'}
		      ,{field:'pid', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
		      ,{field:'subjects', title:'主题', width:120, edit: 'text'}
		      ,{field:'content', title:'内容', width:250, edit: 'text', sort: true}
		      ,{field:'date', title:'时间', width:150,edit: 'text'}
		      ,{field:'uid', title:'作者', width:100,edit: 'text'}
		      ,{field:'forum', title:'板块', width:80, sort: true,edit: 'text'}
		      ,{field:'like',title:'喜欢',width:80,sort:true,edit:'text'}
		      ,{field:'grade',title:'级别',width:40,sort:true,edit:'text'}
		      ,{field:'postimg',title:'图片地址',width:100,edit:'text'}
		      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}   
		    ]]
		    ,page: true
		  });
		  
		  //头工具栏事件
		  table.on('toolbar(admin)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      case 'getCheckData':
		        var data = checkStatus.data;
		        layer.alert(JSON.stringify(data));
		      break;
		      case 'getCheckLength':
		        var data = checkStatus.data;
		        layer.msg('选中了：'+ data.length + ' 个');
		      break;
		      case 'isAll':
		        layer.msg(checkStatus.isAll ? '全选': '未全选');
		      break;
		    };
		  });
		  
		  //监听行工具事件
		  table.on('tool(admin)', function(obj){
		    var data = obj.data;
		    var pid=data.pid;
		    //console.log(obj)
		    if(obj.event === 'del'){
		      layer.confirm('真的删除行么', function(index){
		    	  $.ajax({
		 	         url: 'Manage?method=delpost'
		 	        ,data: {pid:pid}
		 	        , success:function(res){
		 	            console.log(res);
		 	        }
		         }),
		        obj.del();
		        layer.close(index);
		      });
		    } else if(obj.event === 'edit'){
		    	layer.confirm('真的更新行么', function(index){
		      	  $.ajax({
		   	         url: 'Manage?method=updpost'
		   	        ,type:'post'
		   	        ,data: {
		   	        	pid:data.pid,
		   	        	subjects:data.subjects,
		   	        	content:data.content,
		   	        	date:data.date,
		   	        	uid:data.uid,
		   	        	forum:data.forum,
		   	        	like:data.like,
		   	        	grade:data.grade,
		   	        	postimg:data.postimg	
		   	        }
		   	        , success:function(res){
		   	            console.log(res);
		   	        }
		           }),
		        layer.close(index);
		      });
		    }else if(obj.event=='add'){
		    	$('#myhidden').click();
		    }
		  });
		});
}

function apply(){
	layui.use('table', function(){
		  var table = layui.table; 
		  table.render({
		    elem: '#admin'
		    ,url:'/UAV/Manage?method=dealapply'
		    ,toolbar: '#toolbarDemo'
		    ,title: '用户申请表'
		    ,cols: [[
		      {type: 'checkbox', fixed: 'left'}
		      ,{field:'aid', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
		       ,{field:'uid', title:'申请人ID', width:100}
		      ,{field:'name', title:'申请人姓名', width:120}
		      ,{field:'reason', title:'申请理由', width:200}
		      ,{field:'forum', title:'申请的板块', width:150} 
		      ,{field:'date', title:'申请时间', width:150, sort: true}
		      ,{fixed: 'right', title:'操作', toolbar: '#applytable', width:150} 
		    ]]
		    ,page: true
		  });
		  
		  //头工具栏事件
		  table.on('toolbar(admin)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      case 'getCheckData':
		        var data = checkStatus.data;
		        layer.alert(JSON.stringify(data));
		      break;
		      case 'getCheckLength':
		        var data = checkStatus.data;
		        layer.msg('选中了：'+ data.length + ' 个');
		      break;
		      case 'isAll':
		        layer.msg(checkStatus.isAll ? '全选': '未全选');
		      break;
		    };
		  });
		  
		  //监听行工具事件
		  table.on('tool(admin)', function(obj){
		    var data = obj.data;
		    var uid=data.uid;
		 
		    if(obj.event === 'pass'){
		      layer.confirm('确认通过', function(index){
		    	    $.ajax({
		    	         url: 'Manage?method=deluser'
		    	        ,data: {uid:uid}
		    	        , success:function(res){
		    	            console.log(res);
		    	        }
		            }),
		        obj.del();
		        layer.close(index);
		      });
		    } else if(obj.event === 'lost'){	
		    	layer.confirm('确认不通过', function(index){
		    	  $.ajax({
		 	         url: 'Manage?method=upduser'
		 	        ,type:'post'
		 	        ,contentType:"application/x-www-form-urlencoded; charset=UTF-8"
		 	        ,data: {
		 	        	username:data.username,
		 	        	password:data.password,
		 	        	nick:data.nick,
		 	        	sex:data.sex,
		 	        	address:data.address,
		 	        	sign:data.sign,
		 	        	uauth:data.uauth,
		 	        	fraction:data.fraction,
		 	        	uid:data.uid}
		    	    ,success:function(res){
			            console.log(res);
			        }
		    	  }),
		        layer.close(index);
		      });
		    }else if(obj.event=='add'){
		        layui.use(['layer'],function () {
		            var layer = layui.layer,$=layui.$;
		            layer.open({
		                type:1,//类型
		                area:['400px','250px'],//定义宽和高
		                title:'插入用户',//题目
		                shadeClose:false,//点击遮罩层关闭
		                content: $('#user')//打开的内容
		            });
		        })
		    }
		    
		  });
		});   
}
function secmanage(){
	layui.use('table', function(){
		  var table = layui.table; 
		  table.render({
		    elem: '#admin'
		    ,url:'/UAV/Manage?method=getsection'
		    ,toolbar: '#toolbarDemo'
		    ,title: '板块管理'
		    ,cols: [[
		      {type: 'checkbox', fixed: 'left'}
		      ,{field:'id', title:'ID号', width:80}
		       ,{field:'section', title:'板块', width:150,edit: 'text'}
		      ,{field:'vendor', title:'厂商', width:150,edit: 'text'}
		      ,{field:'uid', title:'版主id', width:80,edit: 'text'}
		      ,{fixed: 'right', title:'操作', toolbar: '#secmanage', width:240} 
		    ]]
		    ,page: true
		  });
		  
		  //头工具栏事件
		  table.on('toolbar(admin)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      case 'getCheckData':
		        var data = checkStatus.data;
		        layer.alert(JSON.stringify(data));
		      break;
		      case 'getCheckLength':
		        var data = checkStatus.data;
		        layer.msg('选中了：'+ data.length + ' 个');
		      break;
		      case 'isAll':
		        layer.msg(checkStatus.isAll ? '全选': '未全选');
		      break;
		    };
		  });
		  
		  //监听行工具事件
		  table.on('tool(admin)', function(obj){
		    var data = obj.data;
		    var uid=data.uid;
		 
		    if(obj.event === 'add'){
		     
		    	  layui.use(['layer'],function () {
		              var layer = layui.layer,$=layui.$;
		              layer.open({
		                  type:1,//类型
		                  area:['400px','250px'],//定义宽和高
		                  title:'添加板块',//题目
		                  shadeClose:false,//点击遮罩层关闭
		                  content: $('#modelsec')//打开的内容
		              });
		          })
	
		     
		    } else if(obj.event === 'edit'){	
		    	layer.confirm('确认修改', function(index){
		    	  $.ajax({
		 	         url: 'Manage?method=editsection'
		 	        ,type:'post'
		 	        ,data: {
		 	        	vendor:data.vendor,
		 	        	section:data.section,
		 	        	uid:data.uid,
		 	        	id:data.id
		 	        	}
		    	    ,success:function(res){
			            layer.msg(res);
			        }
		    	  }),
		        layer.close(index);
		      });
		    }else if(obj.event=='del'){
		    	layer.confirm('确认修改', function(index){
		        layui.use(['layer'],function () {
		        	$.ajax({
		        		url:'Manage?method=delsection',
		        		type:'post',
		        		data:{uid:data.uid,section:data.section},
		        		success:function(data){
		        			layer.msg(data);
		        		}
		        	})
		        	 layer.close(index);})
		        })
		       
		    }
		    
		  });
		}); 
}

function uauth(){
	layui.use('table', function(){
		  var table = layui.table; 
		  table.render({
		    elem: '#admin'
		    ,url:'/UAV/Manage?method=getevaluate'
		    ,toolbar: '#toolbarDemo'
		    ,title: '用户权限表'
		    ,cols: [[
		      {type: 'checkbox', fixed: 'left'}
		      ,{field:'uid', title:'用户ID号', width:120}
		       ,{field:'like', title:'获得的喜欢数', width:180,sort: true}
		      ,{field:'unlike', title:'被举报数', width:180,sort: true}
		      ,{field:'auth', title:'用户权限', width:150,sort: true}
		      ,{fixed: 'right', title:'操作', toolbar: '#myauth', width:110} 
		    ]]
		    ,page: true
		  });
		  
		  //头工具栏事件
		  table.on('toolbar(admin)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      case 'getCheckData':
		        var data = checkStatus.data;
		        layer.alert(JSON.stringify(data));
		      break;
		      case 'getCheckLength':
		        var data = checkStatus.data;
		        layer.msg('选中了：'+ data.length + ' 个');
		      break;
		      case 'isAll':
		        layer.msg(checkStatus.isAll ? '全选': '未全选');
		      break;
		    };
		  });
		  
		  //监听行工具事件
		  table.on('tool(admin)', function(obj){
		    var data = obj.data;
		    if(obj.event === 'none'){
		          $.ajax({
		        	  url:'Manage?method=uauth',
		        	  data:{uid:data.uid,auth:'none'},
		        	  success:function(data){
		        		  layer.msg(data);
		        	  }
		        		  
		          })
		    }else if(obj.event === 'comm'){
		    	$.ajax({
		        	  url:'Manage?method=uauth',
		        	  data:{uid:data.uid,auth:'comm'},
		        	  success:function(data){
		        		  layer.msg(data);
		        	  }
		        		  
		          })
		    }
		    
		  });
		}); 
}
function dealpost(){
	layui.use('table', function(){
		  var table = layui.table;
		  table.render({
		    elem: '#admin'
		    ,url:'/UAV/Manage?method=dealpost'
		    ,where: {status:"unread"}
		    ,toolbar: '#toolbarDemo'
		    ,title: '未审核帖子表'
		    ,cols: [[
		      {type: 'checkbox', fixed: 'left'}
		      ,{field:'pid', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
		      ,{field:'subjects', title:'主题', width:120}
		      ,{field:'content', title:'内容', width:250}
		      ,{field:'date', title:'时间', width:150, sort: true}
		      ,{field:'forum', title:'板块', width:80, sort: true}
		      ,{fixed: 'right', title:'操作', toolbar: '#upost', width:180}   
		    ]]
		    ,page: true
		  });
		  
		  //头工具栏事件
		  table.on('toolbar(admin)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      case 'getCheckData':
		        var data = checkStatus.data;
		        layer.alert(JSON.stringify(data));
		      break;
		      case 'getCheckLength':
		        var data = checkStatus.data;
		        layer.msg('选中了：'+ data.length + ' 个');
		      break;
		      case 'isAll':
		        layer.msg(checkStatus.isAll ? '全选': '未全选');
		      break;
		    };
		  });
		  
		  //监听行工具事件
		  table.on('tool(admin)', function(obj){
		    var data = obj.data;
		    var pid=data.pid;
		    //console.log(obj)
		    if(obj.event === 'pass'){
		      layer.confirm('通过吗？', function(index){
		    	  $.ajax({
		 	         url: 'Moder?method=judge'
		 	        ,data: {status:'pass',pid:data.pid}
		 	        , success:function(res){
                      layer.msg("已通过");
		 	        }
		         }),
		         obj.del();
		        layer.close(index);
		      });
		    } else if(obj.event === 'lost'){
		    	layer.confirm('未通过？', function(index){
		      	  $.ajax({
		   	         url: 'Moder?method=judge'
		   	        ,type:'post'
		   	        ,data:{status:'lost',pid:data.pid}
		   	        , success:function(res){
		   	        	layer.msg("已未通过");
		   	        }
		           }),
		           obj.del();
		        layer.close(index);
		      });
		    }else if(obj.event=='see'){
		    	window.open("http://localhost:8090/UAV/UserServlet?method=detailpost&where=detail&pid="+data.pid);
		    }
		  });
		});
}
function dealreport(){
	layui.use('table', function(){
		  var table = layui.table;
		  table.render({
		    elem: '#admin'
		    ,url:'/UAV/Manage?method=dealreport'
		    ,toolbar: '#toolbarDemo'
		    ,title: '举报表'
		    ,cols: [[
		      {type: 'checkbox', fixed: 'left'}
		      ,{field:'did', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
		      ,{field:'discuss', title:'评论内容', width:150}
		      ,{field:'uid', title:'评论人id', width:80}
		      ,{field:'time', title:'评论时间', width:220, sort: true}
		      ,{field:'likenum', title:'举报次数', width:150}
		      ,{field:'forum', title:'板块', width:80}
		      ,{fixed: 'right', title:'操作', toolbar: '#reportdisc', width:180}   
		    ]]
		    ,page: true
		  });
		  
		  //头工具栏事件
		  table.on('toolbar(admin)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      case 'getCheckData':
		        var data = checkStatus.data;
		        layer.alert(JSON.stringify(data));
		      break;
		      case 'getCheckLength':
		        var data = checkStatus.data;
		        layer.msg('选中了：'+ data.length + ' 个');
		      break;
		      case 'isAll':
		        layer.msg(checkStatus.isAll ? '全选': '未全选');
		      break;
		    };
		  });
		  
		  //监听行工具事件
		  table.on('tool(admin)', function(obj){
		    var data = obj.data;
		    var pid=data.pid;
		    //console.log(obj)
		    if(obj.event === 'del'){
		      layer.confirm('删除吗？', function(index){
		    	  $.ajax({
		 	         url: 'Manage?method=deldiscuss'
		 	        ,data: {did:data.did}
		 	        , success:function(res){
                    layer.msg(res);
		 	        }
		         }),
		         obj.del();
		        layer.close(index);
		      });
		    } else if(obj.event === 'ban'){
		    	layer.confirm('举报值归零？', function(index){
		      	  $.ajax({
		   	         url: 'Moder?method=tozero'
		   	        ,type:'post'
		   	        ,data:{did:data.did}
		   	        , success:function(res){
		   	        	layer.msg(res);
		   	        }
		           }),
		           obj.del();
		        layer.close(index);
		      });
		    }else if(obj.event=='see'){
		    	window.open("http://localhost:8090/UAV/UserServlet?method=seework&id="+data.id);
		    }
		  });
		});
}

