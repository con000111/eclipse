	
	Array.prototype.indexOf = function(val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };
    Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };


	// 百度地图API功能
	var map = new BMap.Map("allmap",{enableMapClick:false});    // 创建Map实例
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 5);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);    


	var ztypes  = null;
	var zimgPath = null;
	var markerClusterer = null;
	function test(point,ztypes,i,name,rad){
		if(ztypes==1){
			zimgPath = "/UAV/img/jc.png";
			color = "#27BEFF";
		}else if(ztypes==2){
			zimgPath = '/UAV/img/ts.png';
			color = "#EA4433";
		}else if(ztypes==3){
			zimgPath = '/UAV/img/rkmj.png';
			color = "#EA4433";
		}
		var myIcon = new BMap.Icon(zimgPath, new BMap.Size(24,35),{

		});
		var marker = new BMap.Marker(point,{icon:myIcon});        // 创建标注    
		map.addOverlay(marker);  


		var opts = {
		  width : 200,     // 信息窗口宽度
		  height: 60,     // 信息窗口高度
		  title : "" , // 信息窗口标题
		  enableMessage:true,//设置允许信息窗发送短息
		  message:""
		}
		var infoWindow = new BMap.InfoWindow(name, opts);  // 创建信息窗口对象 
		marker.addEventListener("click", function(){          
			map.openInfoWindow(infoWindow,point); //开启信息窗口
		});

		var circle = new BMap.Circle(point,rad,{fillColor:color, strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
		map.addOverlay(circle);            //增加圆
		circle.show();

		if(ztypes==1){
			jc[i] = marker;
			jccircle[i] = circle;
		}else if(ztypes==2){
			ts[i] = marker;
			tscircle[i] = circle;
		}else if(ztypes==3){
			rkmj[i] = marker;
			rkmjcircle[i] =circle;
		}


	   markers.push(marker);
	}

	var jsondata = null;
	var markers = [];
	var point = null;
	var jc = new Array();
	var ts = new Array();
	var rkmj = new Array();
	var jccircle = new Array();
	var tscircle = new Array();
	var rkmjcircle = new Array();
	function addP(ztypes,markerClusterer){
		showP(ztypes);
		if(ztypes==1){
			jsondata = jcjson;
		}else if(ztypes==2){
			jsondata = tsjson;
		}else if(ztypes==3){
			jsondata = rkmjjson;
		}
		var i = 0;

		var jsonobj = eval(jsondata);

		var j;
		for (j in jsonobj) {
			if(typeof(jsonobj[j])=='object'){
		   		var lon = jsonobj[j]['lon'];
		   		var lat = jsonobj[j]['lat'];
		   		var name = jsonobj[j]['name'];
		   		var rad = jsonobj[j]['rad'];
		   		point = new BMap.Point(lon, lat);
		   		test(point,ztypes,i,name,rad);
			}
			i++;
		};

		//最简单的用法，生成一个marker数组，然后调用markerClusterer类即可。
		if(markerClusterer==null){
			markerClusterer = new BMapLib.MarkerClusterer(map);
		}
		markerClusterer.addMarkers(markers);
		return markerClusterer;
		// alert(markerClusterer);
	}


	function delP(markers){

		var markerClusterer = new BMapLib.MarkerClusterer(map);
	}

	function setCenter(lon,lat,level,cont){

		//map.centerAndZoom(new BMap.Point(lon, lat), level);
		var sContent = cont;
		var point = new BMap.Point(lon, lat);
		map.centerAndZoom(point, level);
		var infoWindow = new BMap.InfoWindow(sContent);
	  // 创建信息窗口对象
		map.openInfoWindow(infoWindow,point); //开启信
	}

	function showP(ztypes){
		var obj = null;
		if(ztypes==1){
			obj = ".jcbox";
		}else if(ztypes==2){
			obj = ".tsbox";
		}else if(ztypes==3){
			obj = ".rkmjbox";
		}
		$(obj).show();
	}

var ztypes = 0;
var msks = [];
var jcjson = $("#jcjson").val();
var tsjson = $("#tsjson").val();
var rkmjjson = $("#rkmjjson").val();
$(function(){
	markerClusterer = addP(1,markerClusterer);
	markerClusterer = addP(2,markerClusterer);
	// markerClusterer = addP(3,markerClusterer);
	$('.zmapbox').click(function(){
		ztypes = $(this).val();
		var num = ztypes-1;
		if($(this).is(':checked')){
			markerClusterer = addP(ztypes,markerClusterer);
		}else{
			var darr = null;
			var carr = null;
			if(ztypes==1){
				darr = jc;
				carr = jccircle;
				$(".jcbox").hide();
			}else if(ztypes==2){
				darr = ts;
				carr = tscircle;
				$(".tsbox").hide();
			}else if(ztypes==3){
				darr = rkmj;
				carr = rkmjcircle;
				$(".rkmjbox").hide();
			}
			markerClusterer.removeMarkers(darr);
			for(var h in darr){
				markers.remove(darr[h]);
				map.removeOverlay(darr[h]);
				carr[h].hide();
			}
		}
	})

	// $(".cbox").click(function(){
	// 	var seltbox = $(this).next();
	// 	if(seltbox.is(':visible')){
	// 		seltbox.hide();
	// 	}else{
	// 		seltbox.show();
	// 	}
		
	// })

	$(".zmselt").mouseleave(function(){
		$(this).find('.zmseltbox').hide();
	})

	$(".zmbtn").click(function(){
		var zmkeywords = $("#zmkeywords").val();
		if(zmkeywords=="请输入关键字"){
			return;
		}
		var counid = $("#counid").val();
		var url = "http://proxy.e12e.com/?http://map.zerotech.com/index/getmapdata";
		var zmapbox = $(".zmapbox");
		var types = '';
		var num;
		for (var i = 0; i < zmapbox.length; i++) {
			var zmb = zmapbox.eq(i);
			if(zmb.is(":checked")){
				num = zmb.val();
				if(num==3){
					num=5;
				}
				types += ","+num;
			}
		};
		$.post(url,{zmkeywords:zmkeywords,counid:counid,types:types},function(data){
			$(".shbox").empty();
			$(".jcbox").hide();
			$(".tsbox").hide();
			$(".rkmjbox").hide();
			if(data['status']==1){
				var jsondata = data['json'];
				var jsonstr = '';
				var jsonm = null;
				for (var m = 0; m < jsondata.length; m++) {
					jsonm = data['json'][m];
					var zmbg = null;
					var zmtips = '';
					if(jsonm['types']==1){
						zmbg = "jcbg";
						zmtips = "机场";
					}else if(jsonm['types']==2){
						zmbg = "tsbg";
						zmtips = "特殊区域";
					}else if(jsonm['types']==5){
						zmbg = "rkmjbg";
						zmtips = "人口密集区域"
					}
					jsonstr += '<div class="zmapfloor"><div class="zf1 zf1bg '+zmbg+'"><span>'+zmtips+'</span></div><div class="zf1"><span>中国大陆</span></div><div class="zf1"><span>'+jsonm['cityname']+'</span></div><div class="zf1"><a href="#allmap" onclick=setCenter("'+jsonm['lon']+'","'+jsonm['lat']+'","15","'+jsonm['name']+'")>'+jsonm['name']+'</a></div></div>';
				};
				$(".shbox").append(jsonstr);
			}else{	
				alert('没有找到结果');
			}
		},"json");
	})

	$(".zmsearleft").focus(function(){
		var zmv = $(this).val();
		if(zmv=='请输入关键字'){
			$(this).val('');
		}
	})

	$(".zmsearleft").blur(function(){
		var zmv = $(this).val();
		if(zmv==''){
			$(this).val('请输入关键字');
		}
	})

	$(document).keydown(function(event){ 
		if(event.keyCode==13){ 
			if($("#zmkeywords").is(":focus")){
				$(".zmbtn").click(); 
			}
			
		} 
	}); 

	$(".zmzhou").click(function(){
		var zmn = $(this).text();
		$(".czbox").text(zmn);
		$(this).parent().hide();
		var url = "/index/getcountrydata";
		var zmzhou = $(this).attr("value");
		$.post(url,{zmzhou:zmzhou},function(data){
			$(".zmseltauto").empty();
			if(data['status']==1){
				var jsonstr = "";
				var jsonm = null;
				var jsondata = data['json'];
				$(".cgbox").text(jsondata[0]['cname']);
				for (var m = 0; m < jsondata.length; m++) {
					jsonm = jsondata[m];
					jsonstr += "<a href='javascript:;' value='"+jsonm['id']+"' class='zmseltz zmguo'>"+jsonm['cname']+"</a>";
				};
				$(".zmseltauto").append(jsonstr);
			}else if(data['status']==0){
				alert("没有这个洲的国家");
				$(".cgbox").empty();
				$(".zmseltauto").empty();
			}

		},'json');
	})

	$(".zmguo").click(function(){
		var zmh = $(this).text();
		$(".cgbox").text(zmh);
		$(this).parent().hide();
		
	})

























})