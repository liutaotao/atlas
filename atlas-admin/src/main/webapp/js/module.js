var module = function(){};

/*
	param={
		seconds:60,
		startFunc:function(){},
		countFunc:function(){},
		endFunc:function(){}
	}
 */
module.countDown = function(param){
	if(!param){
		return;
	}
	var count = 60;
	if(param.seconds){
		count = param.seconds;
	}
	if(param.startFunc && typeof(param.startFunc) == "function"){
		param.startFunc(count);
	}
	function countTime(){
		setTimeout(function(){
			if(count > 1){
				--count;
				if(param.countFunc && typeof(param.countFunc) == "function"){
					param.countFunc(count);
				}
				countTime();
			}else{
				if(param.endFunc && typeof(param.endFunc) == "function"){
					param.endFunc();
				}
			}
		}, 1000)
	}
	countTime();
}

module.showMessage = function(msg,time,endFunc){
	if(!time){
		time = 2000;
	}
	layui.use('layer',function(){
		var layer = layui.layer;
		layer.msg(msg,{
			time:time
		},function(){
			if(endFunc && typeof endFunc === 'function'){
				endFunc();
			}
		})
	})
}