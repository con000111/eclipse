
function post(){
	layui.use('table', function(){
  var table = layui.table;
  table.render({
    elem: '#admin'
    ,url:'/UAV/Moder?method=querypost'
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
    }else if(obj.event=='totop'){
    	$.ajax({
    		url:'Moder?method=totop',
    		data:{pid:data.pid,operate:1},
    		success:function(data){
    			layer.msg(data);
    		}
    	})
    }else if(obj.event=='notop'){
    	$.ajax({
    		url:'Moder?method=totop',
    		data:{pid:data.pid,operate:0},
    		success:function(data){
    			layer.msg(data);
    		}
    	})
    }
  });
});

}


function discuss(){
  layui.use('table', function(){
  var table = layui.table;
  
  table.render({
    elem: '#admin'
    ,url:'/UAV/Moder?method=querydiscuss'
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
      ,{fixed: 'right', title:'操作', toolbar: '#discussbar', width:150}   
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



//条件查询帖子
function psubmit(){
	layer.close(layer.index); 	
	var key=$('#pkey').val();
	layui.use('table', function(){
		  var table = layui.table;
		  table.render({
		    elem: '#admin'
		    ,method: 'post'
		    ,url:'/UAV/Moder?method=kquerypost'
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
function upost(){
	layui.use('table', function(){
		  var table = layui.table;
		  table.render({
		    elem: '#admin'
		    ,url:'/UAV/Moder?method=unreadpost'
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
function judwork(){
	layui.use('table', function(){
		  var table = layui.table;
		  table.render({
		    elem: '#admin'
		    ,url:'/UAV/Moder?method=unreadvideo'
		    ,toolbar: '#toolbarDemo'
		    ,title: '未审核作品表'
		    ,cols: [[
		      {type: 'checkbox', fixed: 'left'}
		      ,{field:'id', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
		      ,{field:'title', title:'主题', width:120}
		      ,{field:'content', title:'内容', width:250}
		      ,{field:'date', title:'时间', width:150, sort: true}
		      ,{field:'uid', title:'发表人ID', width:80}
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
		 	         url: 'Moder?method=judvideo'
		 	        ,data: {status:'pass',id:data.id}
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
		   	         url: 'Moder?method=judvideo'
		   	        ,type:'post'
		   	        ,data:{status:'lost',id:data.id}
		   	        , success:function(res){
		   	        	layer.msg("已未通过");
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
function dealreport(){
	layui.use('table', function(){
		  var table = layui.table;
		  table.render({
		    elem: '#admin'
		    ,url:'/UAV/Moder?method=report'
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
		      ,{fixed: 'right', title:'操作', toolbar: '#reportdisc', width:200}   
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
		    	window.open("http://localhost:8090/UAV/UserServlet?method=detailpost&pid="+data.pid+"&where=detail");
		    }
		  });
		});
}


