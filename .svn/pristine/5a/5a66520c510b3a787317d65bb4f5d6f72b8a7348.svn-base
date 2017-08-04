
$.zxGet = function(url,param,success){
	$.zxAjax(url,'get',param,success,function(){ajaxerror(url,"get",param,success)});
}
$.zxPost = function(url,param,success){
	$.zxAjax(url,'post',param,success,function(){ajaxerror(url,"post",param,success)});
}

function ajaxerror(url,method,param,success){
//	$.zxAjax("/FLSJIM/login.do","post",{username:'101776',password:'abc123'},function(data){
//		if(data.result == "success"){
//			$.zxAjax(url,method,param,success,ajaxerror(url,method,param,success));
//		}
//	},ajaxerror);
	autologin(function(){
		$.zxAjax(url,method,param,success,function(){ajaxerror(url,method,param,success)});
	})
}
var autologin_username = '';
var autologin_password = '';
function autologin(success,error){
	$.zxAjax("/MOA/login.do","post",{username:autologin_username,password:autologin_password},function(data){
		if(data.flag){
			success(data);
		}else{
			$.toast(data.message);
		}
	},error||function(){logout(data);});
}

function logout(data){
	$.toast('请重新登录!');
}

$.zxAjax = function(url,method,param,success,error){
	$.ajax({
		url:url,
		async:true,
		cache:false,
		data:param,
		dataType:'json',
		type:method,
		success:function(data){
			if(data.result == "error" && data.code == "100001"){
				error(data);
			}else{
				success(data);
			}
		},
		error:error
	});
}

$.zxgetimg = function(url){
	$.ajax({
		url:'/FLSJIM/getimg.do',
		async:true,
		cache:false,
		data:{url:url},
		dataType:'text',
		type:'get',
		success:function(data){
			alert(data);
		}
	});
}