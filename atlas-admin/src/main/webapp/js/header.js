
var header = (function(){
	var p = new Object();
	
	var member = null;
	
	var serviceKey = 'binding';
	var isSubmiting = false;
	
	var active = null;
	
	var galleryName = null;
	
	p.init = function(){
		if(!checkLogin()){
			return;
		}
		loadMember();
		
		verifybar.init(function(vCode){
			sendSmsCode(vCode)
		},function(){
			isSubmiting = false;
		})
		
		initHeader();
		initNameInput();
		bindEvent()
	}	
	
	function bindEvent(){
		$("#getVCode").click(function(){
			loadVCode();
		})
		$("#header-user-content input[name='nickname']").blur(function(){
			updateNickname();
		})
		$("#changeFace").click(function(){
			$("#header-user-content input[name='face']").click();
		})
		$("#header-user-content input[name='face']").change(function(){
			uploader.uploadImage($(this).get(0),'avatar',function(imgUrl){
  				modifyFace(imgUrl);
  			});
		})
	}
	
	function loadMember(){
		if(!checkLogin()){
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
					var member = res;
					setFace(member.face);
					setMobile(member.mobile);
					setNickname(member.nickname);
					setWXBind(member.ifBindWX);
					galleryName = member.galleryName;
					setGalleryName(galleryName);
				}else{//失败
					module.showMessage(res.msg)
				}
				isSubmiting = false;
			},
			error:function(res){
				module.showMessage(res)
				isSubmiting = false;
			}
		})
		
	}

	
	function initNameInput(){
		$("#galleryName .input-wrap input").keydown(resizeInput).keyup(resizeInput).each(resizeInput);
		$("#galleryName .input-wrap input").blur(function(){
			if(!checkLogin()){
				return;
			}
			var value = $(this).val();
			if(!value || !value.trim() || value.trim() == galleryName){
				return;
			}
			value = value.trim();
			console.log($(this).val().trim())
			$.ajax({
				url:config.ctx + "/admin/modifyGallaryName.do",
				dataType:"json",
				data:{
					memberId:member.memberId,
					encryptKey:member.encryptKey,
					gallaryName:value
				},
				type:"post",
				success:function(res){
					console.log(res)
					galleryName = value;
					if(res.status == 0){//成功
						setGalleryName(galleryName);
						module.showMessage("工作空间名字修改成功")
					}else{
						module.showMessage(res.msg)
					}
				},
				error:function(res){
					module.showMessage(res)
					//名称修改失败，需要恢复到原来名称
					setGalleryName(galleryName);
				}
			})
		})
	}
	
	function setGalleryName(name){
		$("#name-span").text(name);
		$("#gallery-name").val(name);
		resizeInput()
	}

	function resizeInput(){
		var value = $("#gallery-name").val();
		$("#name-span").text(utils.isNullVal(value)?'未命名的艺术空间':value);
		var textWidth = $("#name-span").width();
		$("#gallery-name").width(textWidth);
		$(".input-wrap input + div").css('left',(textWidth/2+10)+'px');
	}
	
	function modifyFace(imgUrl){
		if(isSubmiting == true){
			return;
		}
		isSubmiting = true;
		if(!checkLogin()){
			return;
		}
		if(utils.isNullVal(imgUrl)){
			module.showMessage('请选择头像')
			isSubmiting = false;
			return;
		}
		
		$.ajax({
			url:config.ctx + '/member/updateMemberFace.do',
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				memberFace:imgUrl
			},
			type:"post", 
			success:function(res){
				if(parseInt(res.status) == 0){//成功
					//修改头像
					setFace(imgUrl);
					module.showMessage('头像修改成功')//TODO 修改成功后，弹框是否需要消失
				}else{//失败
					module.showMessage(res.msg)
				}
				isSubmiting = false;
			},
			error:function(res){
				module.showMessage(res)
				isSubmiting = false;
			}
		})
		
	}
	
	function loadVCode(){
		if(isSubmiting == true){
			return;
		}
		isSubmiting = true;
		var mobile = $("#header-user-changeMobile input[name='mobile']").val();
		if(utils.isNullVal(mobile)){
			module.showMessage('请填写手机号码');
			isSubmiting = false;
			return;
		}else if(!utils.isMobileNum(mobile)){
			module.showMessage('手机号码格式不正确');
			isSubmiting = false;
			return;
		}
		
		if(!serviceKey){
			return;
		}
		
		verifybar.loadVerifyCode();	
	}
	
	function sendSmsCode(vCode){
		var mobile = $("#header-user-changeMobile input[name='mobile']").val();
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
				isSubmiting = false;
			},
			error:function(res){
				module.showMessage(res)
				isSubmiting = false;
			}
		})
		
	}
	
	function updateNickname(){
		if(isSubmiting == true){
			return;
		}
		isSubmiting = true;
		if(!checkLogin()){
			isSubmiting = false;
			return;
		}
		
		var nickname = $("#header-user-content input[name='nickname']").val();
		if(utils.isNullVal(nickname)){
			setNickname(member.nickname);
			isSubmiting = false;
			return;
		}else if(nickname.trim() == member.nickname){
			isSubmiting = false;
			return;
		}
		
		$.ajax({
			url:config.ctx + "/member/updateMemberNickName.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				nickName:nickname
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					setNickname(nickname)
					module.showMessage('昵称修改成功')
				}else{
					module.showMessage(res.msg);
				}
				isSubmiting = false;
			},
			error:function(res){
				module.showMessage(res)
				isSubmiting = false;
			}
		})
		
	}
	
	function changeMobile(cbOk){
		if(isSubmiting == true){
			return;
		}
		isSubmiting = true;
		if(!checkLogin()){
			isSubmiting = false;
			return;
		}
		
		var mobile = $("#header-user-changeMobile input[name='mobile']").val();
		if(utils.isNullVal(mobile)){
			module.showMessage('请填写手机号码');
			isSubmiting = false;
			return;
		}else if(!utils.isMobileNum(mobile)){
			module.showMessage('手机号码格式不正确');
			isSubmiting = false;
			return;
		}
		var vCode = $("#header-user-changeMobile input[name='vCode']").val();
		if(utils.isNullVal(vCode)){
			module.showMessage('请填写手机验证码');
			isSubmiting = false;
			return;
		}
		
		$.ajax({
			url:config.ctx + "/member/changeMemberPhone.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				phone:mobile,
				smsCode:vCode
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					resetMember(res);
					setMobile(res.mobile);
					module.showMessage('手机号码修改成功')
					if(cbOk){
						cbOk();
					}
				}else{
					module.showMessage(res.msg);
				}
				isSubmiting = false;
			},
			error:function(res){
				module.showMessage(res)
				isSubmiting = false;
			}
		})
	}
	
	function changePassword(cbOk){
		if(isSubmiting == true){
			return;
		}
		isSubmiting = true;
		if(!checkLogin()){
			isSubmiting = false;
			return;
		}
		
		var pwOld = $("#header-user-changePwd input[name='password']").val();
		var pw1 = $("#header-user-changePwd input[name='password1']").val();
		var pw2 = $("#header-user-changePwd input[name='password2']").val();
		
		if(utils.isNullVal(pwOld)){
			module.showMessage('请输入原密码');
			isSubmiting = false;
			return;
		}
		if(utils.isNullVal(pw1)){
			module.showMessage('请输入新密码');
			isSubmiting = false;
			return;
		}
		if(utils.isNullVal(pw2)){
			module.showMessage('请再次输入新密码');
			isSubmiting = false;
			return;
		}
		if(pw1 != pw2){
			module.showMessage('两次输入的新密码不同');
			isSubmiting = false;
			return;
		}
		if(!utils.checkPassword(pw1) || !utils.checkPassword(pw2)){
			module.showMessage('密码格式不正确');
			isSubmiting = false;
			return;
		}
		
		$.ajax({
			url:config.ctx + "/member/changeMemberPwd.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				oldPwd:pwOld.trim(),
				pwd:pw1.trim(),
				rePwd:pw2.trim()
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					module.showMessage('密码修改成功')
					if(cbOk){
						cbOk();
					}
				}else{
					module.showMessage(res.msg);
				}
				isSubmiting = false;
			},
			error:function(res){
				module.showMessage(res)
				isSubmiting = false;
			}
		})
		
	}
	
	function unbindWX(cbOk){
		if(isSubmiting == true){
			return;
		}
		isSubmiting = true;
		if(!checkLogin()){
			isSubmiting = false;
			return;
		}
		$.ajax({
			url:config.ctx + "/member/loosenWxBind.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					module.showMessage('解除微信绑定成功')
					setWXBind(false);
					if(cbOk){
						cbOk();
					}
				}else{
					module.showMessage(res.msg);
				}
				isSubmiting = false;
			},
			error:function(res){
				module.showMessage(res)
				isSubmiting = false;
			}
		})
	}
	
	function logout(){
		clearLocalData(config.member);
		gotoPage('login.html')
	}
	
	function initHeader(){
		layui.use('layer', function(){
		  var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
		  
		  //触发事件
		  active = {
		  	layerIndex : {
		  		hUserSetting:null,
		  		hUserInfo:null,
		  		changeMobile:null,
		  		changePwd:null,
		  		unbindWX:null,
		  		bindWX:null,
		  		logout:null
		  	},
		  	closeAll: function(){
		  		layer.closeAll();
		  	},
		    hUserSetting: function(){
		    	var that = this;
		    	var positionClass = 'position_t65_l-10';
		    	if($(that).hasClass('left-155')){
		    		positionClass = 'position_t65_l-155';
		    	}
				 active.layerIndex.hUserSetting = layer.open({
				  title:'',
			      type: 1,
			      skin: 'layui-layer-tipB layer-tips-box '+positionClass, 
			      area: ['190px', '160px'], 
			      shade: [0.01, '#fff'],
			      shadeClose:true,
			      offset:'rt',
			      move:false,
			      resize:false,
			      content: $('#header-user-setting'),
				  anim :-1,
				  isOutAnim: false
			    });
			},
			hUserInfo:function(){
		    	var positionClass = 'position_t65_l-10';
		    	if($('.user-avatar').hasClass('left-155')){
		    		positionClass = 'position_t65_l-155';
		    	}
				layer.close(active.layerIndex.hUserSetting);
				active.layerIndex.hUserInfo = layer.open({
				  title:'',
			      type: 1,
			      skin: 'layui-layer-tipB layer-tips-box '+positionClass, 
			      area: ['480px', '240px'], 
			      shade: [0.01, '#fff'],
			      shadeClose:true,
			      offset:'rt',
			      move:false,
			      resize:false,
			      content: $('#header-user-content'),
				  anim :-1,
				  isOutAnim: false
			    });
			},
			changeMobile:function(){
				layer.close(active.layerIndex.hUserInfo);
				active.layerIndex.changeMobile = layer.open({
				  title:'更换手机号',
			      type: 1,
			      skin: 'layer-normal-center btn_position_t-30', 
			      area: ['330px', '250px'], 
			      shade: [0.3, '#000'],
			      shadeClose:true,
			      move:false,
			      resize:false,
			      content: $('#header-user-changeMobile'),
			      btn:['提交','取消'],
			      btnAlign:'c',
			      yes:function(){
			    	  changeMobile(function(){
			    		  layer.close(active.layerIndex.changeMobile);
			    	  })
			      },
			      no:function(){
			      	layer.close(active.layerIndex.changeMobile);
			      },
				  anim :-1,
				  isOutAnim: false
			    });
			},
			changePwd:function(){
				layer.close(active.layerIndex.hUserInfo);
				active.layerIndex.changePwd = layer.open({
				  title:'修改密码',
			      type: 1,
			      skin: 'layer-normal-center btn_position_t-30', 
			      area: ['330px', '280px'], 
			      shade: [0.3, '#000'],
			      shadeClose:true,
			      move:false,
			      resize:false,
			      content: $('#header-user-changePwd'),
			      btn:['提交','取消'],
			      btnAlign:'c',
			      yes:function(){
			    	  changePassword(function(){
			    		  layer.close(active.layerIndex.changePwd);
			    	  })
			      },
			      no:function(){
			      	layer.close(active.layerIndex.changePwd);
			      },
				  anim :-1,
				  isOutAnim: false
			    });
			},
			unbindWX:function(){
				layer.close(active.layerIndex.hUserInfo);
				active.layerIndex.unbindWX = layer.open({
				  title:'解除微信绑定',
			      type: 1,
			      skin: 'layer-normal-center btn_position_t-30', 
			      area: ['330px', '230px'], 
			      shade: [0.3, '#000'],
			      shadeClose:true,
			      move:false,
			      resize:false,
			      content: '<div style="line-height:90px;text-align:center;color:#50565A;">您确定要解除和微信账号绑定么？</div>',
			      btn:['确定','取消'],
			      btnAlign:'c',
			      yes:function(){
			    	  unbindWX(function(){
			    		  layer.close(active.layerIndex.unbindWX);
			    	  })
			      },
			      no:function(){
			      	layer.close(active.layerIndex.unbindWX);
			      },
				  anim :-1,
				  isOutAnim: false
			    });
			},
			bindWX:function(){
				layer.close(active.layerIndex.hUserInfo);
				active.layerIndex.bindWX = layer.open({
				  title:'绑定微信',
			      type: 2,
			      skin: 'layer-normal-center btn_position_t-30', 
			      area: ['330px', '260px'], 
			      shade: [0.3, '#000'],
			      shadeClose:true,
			      move:false,
			      resize:false,
			      content: ['wx-bind.html','no'],
				  anim :-1,
				  isOutAnim: false
			    });
			},
			logout:function(){
				layer.close(active.layerIndex.hUserSetting);
				active.layerIndex.logout = layer.open({
				  title:'退出登录',
			      type: 1,
			      skin: 'layer-normal-center btn_position_t-30', 
			      area: ['330px', '230px'], 
			      shade: [0.3, '#000'],
			      shadeClose:true,
			      move:false,
			      resize:false,
			      content: '<div style="line-height:90px;text-align:center;color:#50565A;">您确定要退出登录么？</div>',
			      btn:['确定','取消'],
			      btnAlign:'c',
			      yes:function(){
			    	  logout();
			      },
			      no:function(){
			      	layer.close(active.layerIndex.logout);
			      },
				  anim :-1,
				  isOutAnim: false
			    });
			}
		  };
		  $('.header-event').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
		});
	}
	
	function setMobile(mobile){
		$("#header-user-setting ul li:first-child a p:nth-child(2)").text(mobile)
		$("#header-user-content input[name='mobile']").val(mobile);
	}
	
	function setFace(face){
		$("#memberface").css('background-image','url('+face+')');
		$("#header-user-content .avatar .pic").css('background-image','url('+face+')');
	}

	function setNickname(nickname){
		$("#header-user-setting ul li:first-child a p:nth-child(1)").text(nickname)
		$("#header-user-content input[name='nickname']").val(nickname);
	}
	
	function setWXBind(ifBindWX){
		$("#header-user-content .layui-input-block span[data-bind='"+!ifBindWX+"'],#header-user-content .layui-input-block a[data-bind='"+!ifBindWX+"']").hide();
		$("#header-user-content .layui-input-block span[data-bind='"+ifBindWX+"'],#header-user-content .layui-input-block a[data-bind='"+ifBindWX+"']").show();
	}
	
	function checkLogin(){
		member = getMember();
		if(utils.isNullVal(member)){
			module.showMessage('您未登录')
			gotoPage('login.html');
			return false;
		}
		return true;
	}
	
	function resetMember(member){
		setLocalData(config.member, JSON.stringify(member));
	}
	
	//子页面frame 在绑定完成后来调用
	p.changeWXBind = function(isBind){
		setWXBind(isBind);
		active.closeAll();
	}
	
	return p;
})()

header.init();
