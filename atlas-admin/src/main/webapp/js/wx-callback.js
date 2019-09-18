$(function(){
	
	var code = $.getUrlParam('code');
	var state = $.getUrlParam('state');
	var memberId = null;
	var member = getMember();
	if(member && member.memberId){
		memberId = member.memberId
	}
	
	$.ajax({
		url:config.ctx + 'wxCallBack.do',
		dataType:"json",
		data:{
			code:code,
			state:state,
			memberId:memberId
		},
		type:"post", 
		success:function(res){
			if(parseInt(res.status) == 0){//成功
				console.log(res)
				if(res.type == 1){//登录成功
					setLocalData(config.member, JSON.stringify(res.member));
					gotoPage('main.html');
				}else if(res.type == 2){//绑定成功
					setLocalData(config.member, JSON.stringify(res.member));
					parent.header.changeWXBind(true);
				}
			}else{//失败
				console.log(res.msg)
			}
		},
		error:function(res){
			console.log(res)
		}
	})
})

