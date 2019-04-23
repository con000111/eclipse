/*
 功能：保存cookies函数 
 参数：name，cookie名字；value，值
 */
 function SetCookie(name,value){
   var Days = 30*12;  //cookie 将被保存一年
   var exp = new Date(); //获得当前时间
   exp.setTime(exp.getTime() + Days*24*60*60*1000); //换成毫秒
   document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
 } 
 /*
 功能：获取cookies函数 
 参数：name，cookie名字
 */
 function getCookie(name){
   var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
   if(arr != null){
     return unescape(arr[2]); 
   }else{
     return null;
   }
 } 
 /*
 功能：删除cookies函数 
 参数：name，cookie名字
 */
 
 function delCookie(name){
   var exp = new Date(); //当前时间
   exp.setTime(exp.getTime() - 1);
   var cval=getCookie(name);
   if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
 }
 

function checkbox(){
  if( document.getElementById(che).checked==true){
		alert("ok");
	}
/* var val=$("input[name='che']:checked").;
alert(val); */
}
 function SetPwdAndChk() {
    //取用户名
    var usr = document.getElementById('exampleInputEmail1').value;
		var pwd = document.getElementById('exampleInputPassword1').value;
    //如果记住密码选项被选中
    if (document.getElementById('exampleCheck1').checked == true) {
      //将用户名和密码写入到Cookie
      SetCookie(usr, pwd);
    } else {
      //如果没有选中记住密码,则立即过期
      delCookie(usr);
    }
  }
	function GetPwdAndChk() {
	var usr = document.getElementById('exampleInputEmail1').value;
	var pwd = getCookie(usr);
	if (pwd != null) {
		document.getElementById('exampleCheck1').checked = true;
		document.getElementById('exampleInputPassword1').value = pwd;
	} else {
		document.getElementById('exampleCheck1').checked = false;
		document.getElementById('exampleInputPassword1').value = "";
	}
	}