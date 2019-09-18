$(function(){
	var member = getMember();
	if(utils.isNullVal(member)){
		$(".enter a").attr('href','main.html');
		$(".enter").show();
		return;
	}

	$.ajax({
		url:config.ctx + '/member/getMemberInfo.do',
		dataType:"json",
		data:{
			memberId:member.memberId,
			encryptKey:member.encryptKey,
		},
		type:"post", 
		success:function(res){
			if(parseInt(res.status) == 0){//成功
				var $galleryName = $(".enter p");
				if(!utils.isNullVal(res.galleryName)){
					$galleryName.text(res.galleryName);
				}
				
				$(".enter a").attr('href','main.html');
			}else{//失败
				$(".enter a").attr('href','login.html');
			}
			$(".enter").show();
		},
		error:function(res){
			console.log(res)
			$(".enter a").attr('href','main.html');
			$(".enter").show();
		}
	})
	
	
})
