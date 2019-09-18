initLogin();
function initLogin(){
	layui.use(['layer','element'],function(){
		var $ = layui.jquery, layer = layui.layer;
		
		var active = {
			showLoginBox : function(){
				layer.open({
				  title:'',
			      type: 1,
			      closeBtn:0,
			      skin: 'layer-normal-center', 
			      area: ['390px', '560px'], 
			      shade: [0.01, '#fff'],
			      shadeClose:false,
			      move:false,
			      resize:false,
			      content: $('#login-box'),
				  anim :-1,
				  isOutAnim: false,
				  scrollbar: false
			    });
			}
		}
		active['showLoginBox'].call();
/*		$('.login-event').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });*/
	})
}


var login = (function(){
	var p = new Object();
	
	var isClicked = false;
	var serviceKey = null;
	
	var mobile = null;
	var vCode = null;
	var password = null;
	
	
	p.init = function(){
		bindBtns();
		loadWxLoginImg();
		verifybar.init(function(vCode){
			sendSmsCode(vCode)
		},function(){
			isClicked = false;
		});
	}
	
	function bindBtns(){
		$('.login-event').on('click', function(){
		    var event = $(this).attr('id');
		    switch(event){
		    case 'next':
		    	checkMobileReg();
		    	break;
		    case 'changeMobile':
		    	$('input[name="phone"]').removeAttr('disabled');
				toStep('step1');
				break;
		    case 'login':
		    	login();
				break;
		    case 'getVCode':
		    	loadVCode();
		    	break;
		    case 'register':
		    	register()
		    	break;
		    case 'forget':
		    	serviceKey = 'update_password';
		    	toStep('step4');
		    	break;
		    case 'submit':
		    	updatePassword();
		    	break;
			default:
				console.log(event)
		    }
		  });
	}
	
	function loadWxLoginImg(){
		$.ajax({
			url:config.ctx + '/admin/initWxPcLoginConfig.do',
			dataType:"json",
			data:null,
			type:"post", 
			success:function(res){
				if(parseInt(res.status) == 0){//成功
					console.log(res)
					var obj = new WxLogin({
                        id:"log-ewm", 
                        appid: res.app_id, 
                        scope: res.scope, 
                        redirect_uri: res.redirect_uri,
                        state: res.state,
                       /* style: "",
                        href: ""*///不能填本地地址
                      });
				}else{//失败
					console.log(res.msg)
				}
			},
			error:function(res){
				module.showMessage(res)
			}
		})
	}
	
	function loadVCode(){
		if(isClicked == true){
			return;
		}
		isClicked = true;
		var mobile = $('input[name="phone"]').val();
		if(utils.isNullVal(mobile)){
			module.showMessage('请填写手机号码');
			isClicked = false;
			return;
		}else if(!utils.isMobileNum(mobile)){
			module.showMessage('手机号码格式不正确');
			isClicked = false;
			return;
		}
		
		if(!serviceKey){
			return;
		}
		
		verifybar.loadVerifyCode();	
	}
	
	function sendSmsCode(vCode){
		var mobile = $('input[name="phone"]').val();
		$.ajax({
			url:config.ctx + '/member/send-sms-code.do',
			dataType:"json",
			data:{
				phone:mobile,
				key:serviceKey,
				vCode:vCode
			},
			type:"post", 
			success:function(res){
				if(parseInt(res.status) == 0){//成功
					module.countDown({
						seconds:60,
						startFunc:function(count){
							$("#getVCode").unbind('click').addClass('disabled').text(count+'s');
						},
						countFunc:function(count){
							$("#getVCode").text(count+'s');
						},
						endFunc:function(){
							$("#getVCode").removeClass('disabled').text('获取验证码').click(function(){
								loadVCode();
							});
						}
					})	
				}else{//失败
					module.showMessage(res.msg)
				}
				isClicked = false;
			},
			error:function(res){
				module.showMessage(res)
				isClicked = false;
			}
		})
		
	}
	
	function updatePassword(){
		if(isClicked == true){
			return;
		}
		isClicked = true;
		
		if(!checkMobile() || !checkVCode() || !checkPassword()){
			isClicked = false;
			return;
		}
		
		$.ajax({
			url:config.ctx + '/member/retrievePassword.do',
			dataType:"json",
			data:{
				phone:mobile,
				smsCode:vCode,
				password:password,
				rePasswd:password
			},
			type:"post", 
			success:function(res){
				if(parseInt(res.status) == 0){//成功
					setLocalData(config.member, JSON.stringify(res));
					module.showMessage('密码修改成功',1500,function(){
						gotoPage('main.html');
					})
				}else{//失败
					module.showMessage(res.msg)
				}
				isClicked = false;
			},
			error:function(res){
				module.showMessage(res)
				isClicked = false;
			}
		})
	}
	
	function register(){
		if(isClicked == true){
			return;
		}
		isClicked = true;
		
		if(!checkMobile() || !checkVCode() || !checkPassword()){
			isClicked = false;
			return;
		}
		
		$.ajax({
			url:config.ctx + '/member/memberRegister.do',
			dataType:"json",
			data:{
				phone:mobile,
				password:password,
				smsCode:vCode,
				license:'agree'
			},
			type:"post", 
			success:function(res){
				if(parseInt(res.status) == 0){//成功
					setLocalData(config.member, JSON.stringify(res));
					module.showMessage('注册成功',1500,function(){
						gotoPage('main.html');
					})
				}else{//失败
					module.showMessage(res.msg)
				}
				isClicked = false;
			},
			error:function(res){
				module.showMessage(res)
				isClicked = false;
			}
		})
		
	}
	
	function login(){
		if(isClicked == true){
			return;
		}
		isClicked = true;
		
		if(!checkMobile() || !checkPassword()){
			isClicked = false;
			return;
		}
		
		$.ajax({
			url:config.ctx + '/member/memberLogin.do',
			dataType:"json",
			data:{
				phone:mobile,
				password:password
			},
			type:"post", 
			success:function(res){
				if(parseInt(res.status) == 0){//成功
					console.log(res)
					setLocalData(config.member, JSON.stringify(res));
					gotoPage('main.html?page=desktop');
				}else{//失败
					module.showMessage(res.msg)
				}
				isClicked = false;
			},
			error:function(res){
				module.showMessage(res)
				isClicked = false;
			}
		})
		
	}
	
	function checkMobileReg(){
		if(isClicked == true){
			return;
		}
		isClicked = true;

		if(!checkMobile()){
			isClicked = false;
			return;
		}
		
		$.ajax({
			url:config.ctx + '/member/checkMemberPhone.do',
			dataType:"json",
			data:{
				phone:mobile
			},
			type:"post", 
			success:function(res){
				if(parseInt(res.status) == 0){//成功
					if(parseInt(res.state) == 0){//手机号码未注册
						toStep('step3')
						serviceKey = 'register';
						$('input[name="phone"]').attr('disabled','disabled');
					}else if(parseInt(res.state) == 1){//手机号码已注册
						toStep('step2')
						$('input[name="phone"]').attr('disabled','disabled');
					}
				}else{//失败
					module.showMessage(res.msg)
				}
				isClicked = false;
			},
			error:function(res){
				module.showMessage(res)
				isClicked = false;
			}
		})
		
	}
	
	function toStep(step){
		$(".log-mo").removeClass("step1 step2 step3 step4");
		$(".log-mo").addClass(step);
	}
	
	function checkMobile(){
		mobile = $('input[name="phone"]').val();
		if(utils.isNullVal(mobile)){
			module.showMessage('请填写手机号码');
			return false;
		}else if(!utils.isMobileNum(mobile)){
			module.showMessage('手机号码格式不正确');
			return false;
		}		
		return true;
	}
	
	function checkVCode(){
		vCode = $('input[name="vCode"]').val();
		if(utils.isNullVal(vCode)){
			module.showMessage('请填写手机验证码');
			return false;
		}
		return true;
	}
	
	function checkPassword(){
		password = $('input[name="password"]').val();
		if(utils.isNullVal(password)){
			module.showMessage('请填写密码');
			return false;
		}else if(!utils.checkPassword(password)){
			module.showMessage('请输入6-20位数字与字母组合密码');
			return false;
		}
		return true;
	}
	
	return p;
})()

login.init();