$(function(){
	
	var code = $.getUrlParam('code');
	var state = $.getUrlParam('state');
	
	$.ajax({
		url:config.ctx + '/user/wxLoginCallBack.do',
		dataType:"json",
		data:{
			code:code,
			state:state
		},
		type:"post", 
		success:function(res){
			if(parseInt(res.status) == 0){//成功
				console.log(res)
				setLocalData(config.member, res);
				window.location.href = getLocalData(config.backUrl);
			}else{//失败
				console.log(res.msg)
			}
		},
		error:function(res){
			console.log(res)
		}
	})
})

