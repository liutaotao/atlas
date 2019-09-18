$(function(){
	var viewCode = null;
	var member = null;
	
	//定时发送，毫秒数
	var viewSeconds = 5000;
	var count = 0;
	var continueInterval = null;
	
	var viewInfo = 'com.atlas.viewinfo';
	
	var transactionId = null;
	
	function getMember(){
		return {
			memberId : 144,
			encryptKey : '196BJ0Xq6d9f7ZiiSny32S2TQ1olMZ1l'
		}
	}
	
	function init(){
		member = getMember();
		
		if(!member || !member.memberId){
			wxLogin();
			return;
		}
		
		//将用户信息，设备等信息设置到session中
		initSessionInfo()
		//i18n国际化
		utils.loadI18nProperties(function(){
			console.log($.i18n.prop('string_username'));
		})
		viewCode = $.getUrlParam("viewCode");
		loadWorksDetail();
		
	}
	
	function pageVisibilityChange(){
		if(typeof pageVisibility.hidden !== "undefined"){
			pageVisibility.visibilitychange(function(){
				if(this.hidden == true){
					clearInterval(continueInterval);
					leaveView();
				}else{
					reactiveView()
					continueView()
				}
			})
			return;
		}
		console.log('浏览器不支持pageVisibility')
	}
	
	function windowUnLoad(){
		//仅电脑端,android支持,ios不支持
		$(window).on('beforeunload',function(e){
			console.log('结束了')
			member = getMember();
			if(!member || !member.memberId){
				return;
			}
			if(!transactionId){
				return;
			}
			callInterface({
				url:config.ctx + '/user/endView.do',
				data:{
					memberId:member.memberId,
					viewCode:viewCode,
					transactionId:transactionId
				},
				succFunc:function(res){
					console.log('结束信息提交成功')
				}
			})
		})
		
	}
	
	function leaveView(){
		console.log('我离开了')
		member = getMember();
		if(!member || !member.memberId){
			wxLogin();
			return;
		}
		if(!transactionId){
			return;
		}
		callInterface({
			url:config.ctx + '/user/leaveView.do',
			data:{
				memberId:member.memberId,
				viewCode:viewCode,
				transactionId:transactionId
			},
			succFunc:function(res){
				console.log('离开信息提交成功')
			}
		})
	}
	
	function reactiveView(){
		console.log('我回来了')
		member = getMember();
		if(!member || !member.memberId){
			wxLogin();
			return;
		}
		if(!transactionId){
			return;
		}
		callInterface({
			url:config.ctx + '/user/reactiveView.do',
			data:{
				memberId:member.memberId,
				viewCode:viewCode,
				transactionId:transactionId
			},
			succFunc:function(res){
				console.log('回来信息提交成功')
			}
		})
	}
	
	/**每5秒发送一次请求*/
	function continueView(){
		continueInterval = setInterval(function(){
			member = getMember();
			if(!member || !member.memberId){
				wxLogin();
				return;
			}
			if(!transactionId){
				return;
			}
			callInterface({
				url:config.ctx + '/user/coutinueView.do',
				data:{
					memberId:member.memberId,
					viewCode:viewCode,
					count:++count,
					viewSeconds:viewSeconds/1000,
					transactionId:transactionId
				}
			})
		}, viewSeconds)
	}
	
	function wxLogin(){
		$.ajax({
			url:config.ctx + "/user/wxLogin.do",
			dataType:"json",
			data:null,
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					setLocalData(config.backUrl, window.location.href);
					window.location.href = res.wxLoginUrl;
				}
			},
			error:function(res){
				console.log(res)
			}
		})
	}
	
	function initSessionInfo(){
		var ua = navigator.userAgent;//获取userAgent信息
		var md = new MobileDetect(ua);
		console.log(md)
		var os = md.os();//获取系统  
	    var model = "";  //手机型号
	    var browser = '';
	    if (os == "iOS") {//ios系统的处理  
	        os = md.os() + md.version("iPhone");  
	        model = md.mobile();  
	    } else if (os == "AndroidOS") {//Android系统的处理  
			Array.prototype.contains = function (obj) {  
			    var i = this.length;  
			    while (i--) {  
			        if (this[i].indexOf(obj) > -1) {  
			            return i;  
			        }  
			    }  
			    return -1;  
			}  
	        os = md.os() + md.version("Android");  
	        var sss = ua.split(";");  
	        var i = sss.contains("Build/");  
	        if (i > -1) {  
	            model = sss[i].substring(0, sss[i].indexOf("Build/"));  
	        }  
	    }
	    
	    if(isWeiXin()){
    		browser = 'Weixin';
    	}else if(ua.indexOf('Trident') > -1){//IE内核
    		browser = 'trident';
    	}else if(ua.indexOf('Presto') > -1){//opera内核
    		browser = 'Presto';
    	}else if(ua.indexOf('AppleWebKit') > -1){// 苹果、谷歌内核
    		browser = 'webKit';
    	}else if(ua.indexOf('Gecko') > -1 && ua.indexOf('KHTML') == -1){ // 火狐内核
    		browser = 'Gecko';
    	} 
	    
		console.log(returnCitySN)
	    console.log(os + "---" + model+'====='+browser );//打印系统版本和手机型号  
		
		$.ajax({
			url:config.ctx + "/user/saveDeviceInfo.do",
			dataType:"json",
			data:{
				os:os, 
				model:model, 
				browser:browser, 
				cip:returnCitySN.cip, 
				cid:returnCitySN.cid, 
				cname:returnCitySN.cname
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					
				}
			},
			error:function(res){
				console.log(res)
			}
		})
	}
	
	function loadWorksDetail(){
		$.ajax({
			url:config.ctx + "/user/loadWorksDetail.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				viewCode:viewCode
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					$(".main .ctip").text(res.galleryName + " 作品导览图");
					$(".main .title").text(res.galleryName + " | " + res.name);
					fillArtwork(res.artworkDtoList);
					transactionId = res.transactionId;
					initImageView()
					
					//数据分析初始化
					continueView()
					pageVisibilityChange()
					windowUnLoad()
				}
			},
			error:function(res){
				console.log(res)
			}
		})
		
	}
	
	
	function fillArtwork(data){
		if(data.length > 0){
			$.each(data,function(index,obj){
				var artwork = 
					'<li>'+
						'<img src="'+obj.imageUrl+'"/>'+
						'<h3>'+obj.artistName+'</h3>'+
						'<p>'+obj.name+'</p>'+
						'<p>'+obj.materialName+'</p>'+
						'<p>'+obj.dimension+' '+obj.createYear+'</p>'+
						'<p class="price">'+utils.formatMoney(obj.price/100)+'</p>'+
					'</li>';
				$(".alist").append(artwork);
				
				var imgSlide = '<div class="swiper-slide">'+
								'<div id="img'+index+'">'+
								'<img src="'+obj.imageUrl+'" />'+
								'</div>'+
							'</div>';
				$(".swiper-wrapper").append(imgSlide)
			})
		}
	}

	var slideIndex = 0;
	function initImageView(){
		$(".swiper-container,.swiper-wrapper,.swiper-slide").width(
				$(window).width()).height($(window).height());
		var hammerTest = new Hammer(document.getElementById('imgView'));
		hammerTest.get('swipe').set({
			direction : Hammer.DIRECTION_HORIZONTAL,
			velocity  : 1,
			threshold : 20
		});

		var transStep = $('.swiper-slide').width();
		var slideLength = $(".swiper-wrapper .swiper-slide").length;

		hammerTest.on('swipeleft swiperight', function(ev) {
			console.log(ev.type);
			var matrix = $(".swiper-wrapper").css('transform').replace(
					'matrix(', '').replace(')', '');
			var matrixArray = matrix.split(', ');
			var transX = parseFloat(matrixArray[4]);
			if (ev.type == 'swipeleft') {
				if (slideIndex < slideLength-1) {
					$(".swiper-wrapper").css('transform',
							'translateX(' + (transX - transStep) + 'px)');
					$("#img" + slideIndex).smartZoom('destroy');
					slideIndex++;
					$("#img" + slideIndex).smartZoom({
						'containerClass' : 'zoomableContainer'
					});
				}
			} else {
				if (slideIndex > 0) {
					$(".swiper-wrapper").css('transform',
							'translateX(' + (transX + transStep) + 'px)');
					$("#img" + slideIndex).smartZoom('destroy');
					slideIndex--;
					$("#img" + slideIndex).smartZoom({
						'containerClass' : 'zoomableContainer'
					});
				}
			}
			console.log(transX + ' ' + slideIndex)
		});
		
		var isSwiperShow = false;
		
		$(".alist li img").click(function(){
			if(isSwiperShow == true){
				return;
			}
			isSwiperShow = true;
			$("#img" + slideIndex).smartZoom('destroy');
			var index = $(this).parents('li').index();
			console.log('click:'+index)
			slideIndex = index;
			$(".swiper-wrapper").css('transform',
					'translateX(-' + ((index)* transStep) + 'px)').parent().show();
			$("#img" + slideIndex).smartZoom({
				'containerClass' : 'zoomableContainer'
			});
			$('.swiper-container').css({'opacity':1})
		})
		
		hammerTest.on('tap',function(){
			$(".swiper-container").hide().css({'opacity':0})
			setTimeout(function(){
				isSwiperShow = false;
			}, 100)
			
		})
	}
	
	init()
})

