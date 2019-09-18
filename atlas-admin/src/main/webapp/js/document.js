$(function(){
	$("#back").click(function(){
		var back = window.document.referrer;
		if(back.indexOf('atalas') > -1 || back.indexOf('atlas') > -1){
			history.back();
		}else{
			window.location.href='main.html';
		}
	})
	
	
	layui.use('layer',function(){
		var $ = layui.jquery,layer = layui.layer;
		
		var active = {
			layerIndexs:{
				showMore : null,
				showShare : null,
				shareDoc : null,
				createArtItem:null,
				previewDoc:null,
				editDoc:null,
				feedBack:null,
				delDoc:null
			},
			showMore:function(){
				var that = this;
				active.layerIndexs.showMore = layer.open({
				      type: 4,
				      skin: 'layer-tips-box layer-tips-white', 
				      area: ['160px', '50px'], 
				      shade: [0.01, '#fff'],
				      shadeClose:true,
				      move:false,
				      resize:false,
				      closeBtn:false,
				      content: ['<a id="delFile" class="doc-event" data-type="delDoc" href="javascript:void(0)" style="display:block;line-height:30px;text-indent:20px;color:#FF183D;font-size:14px;">删除文档</a>',that],
				      tips:3,
				      anim :-1,
				      isOutAnim: false,
				      success:function(){
				    	  bindDocEvent()
				      }
				})
			},
			delDoc: function(){
				var that = this;
				
				layer.closeAll();
				active.layerIndexs.delDoc = layer.open({
				  title:'删除文档',
			      type: 1,
			      skin: 'layer-normal-center btn_position_t-30', 
			      area: ['330px', '230px'], 
			      shade: [0.3, '#000'],
			      shadeClose:true,
			      move:false,
			      resize:false,
			      content: '<div style="line-height:90px;text-align:center;color:#50565A;">您确定要删除此文档么？</div>',
			      btn:['确定','取消'],
			      btnAlign:'c',
			      yes:function(){
					  var docId = $(that).data('docid');
					  fileOp.deleteWorks(function(){
						  layer.close(active.layerIndexs.delDoc);
						})
			      },
			      no:function(){
			      	layer.close(active.layerIndex.delDoc);
			      },
				  anim :-1,
				  isOutAnim: false
			    });
			},
			showShare:function(){
				var that = this;
				active.layerIndexs.showShare = layer.open({
				      type: 4,
				      skin: 'layer-tips-box layer-tips-white share-box', 
				      area: ['250px', '120px'], 
				      shade: [0.01, '#fff'],
				      shadeClose:true,
				      move:false,
				      resize:false,
				      closeBtn:false,
				      content: [$("#share-box").html(),that],
				      tips:3,
				      anim :-1,
				      isOutAnim: false,
				      success:function(){
				    	  bindDocEvent()
				      }
				})
			},
			shareDoc:function(){
				var that = this;
				
				if($(that).hasClass('active')){
					showShareBox()
				}else{
					fileOp.modifyPermission('link', showShareBox)
				}
				
				function showShareBox(){
					layer.close(active.layerIndexs.showShare);
					active.layerIndexs.createArtItem = layer.open({
					      type: 4,
					      skin: 'layer-tips-box layer-tips-white share-href-box', 
					      area: ['326px', '250px'], 
					      shade: [0.01, '#fff'],
					      shadeClose:true,
					      move:false,
					      resize:false,
					      closeBtn:false,
					      content: [$("#share-href-box").html(),'.header-right .share'],
					      tips:3,
					      anim :-1,
					      isOutAnim: false,
					      success:function(){
					    	  var input = $('.share-href input[name="viewUrl"]')
					    	  input.val(input.attr('data-viewUrl'));
					    	  bindDocEvent()
					      }
					})
				}
			},
			privateDoc:function(){
				var that = this;
				if($(that).hasClass('active')){
					layer.closeAll();
					return;
				}else{
					fileOp.modifyPermission('private', function(){
						layer.closeAll();
					})
				}
			},
			createArtItem:function(){
				layer.closeAll();
				active.layerIndexs.createArtItem = layer.open({
					  title: '',
				      type: 1,
				      skin: 'layer-tips-box layer-form-1 new-doc-item new-doc-item-tip', 
				      area: ['382px', '527px'], 
				      offset:['120px'],
				      shade: [0.01, '#fff'],
				      shadeClose:true,
				      move:false,
				      resize:false,
				      closeBtn:false,
				      content: $("#new-doc-item"),
				      btn:['确定'],
				      btnAlign:'c',
				      yes:function(){
				      	fileOp.addArtwork(function(){
				      		layer.close(active.layerIndexs.createArtItem);
				      	});
				      },
				      success:function(){
				    	$('.new-doc-item').css("top",'125px')
				    	$('.new-doc-item .layui-layer-content').css('height','449px');
				    	$(".layui-layer-content input[name='imageUrl']").unbind("change").change(function(){
				    		var that = this;
				  			
				  			uploader.uploadImage($(that).get(0),'artwork',function(imgUrl){
				  				$(that).attr("data-imgUrl",imgUrl).parent().css({
				  					'background-image':'url('+imgUrl+')',
				  					'background-size':'cover',
				  					'background-position':'center'
				  				});
				  			});
				  		})
						$('#new').addClass('active');
				      },
				      end:function(){
				    	$(".new-doc-item input").val('');
				    	$(".new-doc-item input[name='imageUrl']").parent().css({
		  					'background':'url(img/sbg_add_pic.png)  120px 20px no-repeat',
		  					'background-size':'80px'
		  				});
				      	$('#new').removeClass('active');
				      },
				      anim :-1,
				      isOutAnim: false
				})
			},
			previewDoc:function(){
				layer.closeAll();
				
				fileOp.createPreviewEwm(function(previewUrl){
					active.layerIndexs.previewDoc = layer.open({
					      type: 4,
					      skin: 'layer-tips-box layer-tips-white tips-i-position-left_45', 
					      area: ['190px', '190px'], 
					      shade: [0.01, '#fff'],
					      shadeClose:true,
					      move:false,
					      resize:false,
					      closeBtn:false,
					      content: ['<div style="text-align:center;color:#3A424C;"><img style="width:155px;margin:0 auto;" src="'+previewUrl+'"/><p style="position:absolute;bottom:15px;width: 100%;">手机扫码即可预览</p></div>','#preview'],
					      tips:3,
					      end:function(){
					      	$('#preview').removeClass('active');
					      },
					      success:function(){
							$('#preview').addClass('active');
					      },
					      anim :-1,
					      isOutAnim: false
					})
				})
				
				
			},
			editDoc:function(){
				layer.closeAll();
				
				fileOp.loadSortDoc(function(){
					active.layerIndexs.editDoc = layer.open({
					      type: 1,
					      skin: 'layer-tips-box edit_doc_box', 
					      area: ['780px', '480px'], 
					      shade: [0.01, '#fff'],
					      shadeClose:true,
					      move:false,
					      resize:false,
					      closeBtn:false,
					      offset:['125px'],
					      content: $('#edit_doc_box'),
					      end:function(){
					      	$('#edit').removeClass('active');
					      },
					      success:function(){
					      	$(".edit_doc_box .layui-layer-content").height(390);
					      	$(".edit_doc_box ul").sortable();
					      	$('#edit').addClass('active');
					      	
					      },
					      btn:['确定'],
					      btnAlign:'c',
					      yes:function(){
					      	fileOp.modifyArtworkSeq(function(){
					      		layer.close(active.layerIndexs.editDoc);
					      		fileOp.loadDoc();
					      	});
					      },
					      anim :-1,
					      isOutAnim: false
					      
					})
				})
				
			},
			feedBack: function(){
				 active.layerIndexs.feedBack = layer.open({
				  title:'',
			      type: 1,
			      skin: 'layui-layer-tipB layer-tips-box layer-feedback position_t-10_l50 lc', 
			      area: ['430px', '220px'], 
			      shade: [0.01, '#fff'],
			      shadeClose:true,
			      offset:'lb',
			      move:false,
			      resize:false,
			      content: '<div style="padding:15px 20px 0;"><textarea placeholder="请提出您的宝贵意见，以帮助改进我们的产品" style="width:370px;height:110px;"></textarea></div>',
			      btn:['发布'],
			      btnAlign:'c',
			      yes:function(){
			    	  fileOp.feedBack(function(){
			    		  layer.close(active.layerIndexs.feedBack);
			    	  })
			      },
				  anim :-1,
				  isOutAnim: false
			    });
			}
		}
		
		
		function bindDocEvent(){
		$('.doc-event').unbind("click").on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
		}
		bindDocEvent()
	})
	
})


var fileOp = (function(){
	var p = new Object();
	
	var works = {};
	var member = null;
	
	var isLoading = false;
	
	var privateCode = 'private';
	var shareCode = 'link';
	
	p.init = function(){
		works.id = $.getUrlParam("docId");
		if(!checkLogin() || !checkWork()){
			return;
		}
		initNameInput();
		p.loadDoc();
	}
	
	p.loadDoc = function(){

		if(!checkLogin() || !checkWork()){
			return;
		}
		
		$.ajax({
			url:config.ctx + "/admin/queryWorks.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				worksId:works.id
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					works.detail = res;
					setWorksName(works.detail.name);
					p.loadArtworks();
					$("#file-update-time").text(getLastUpdate(works.detail.lastUpdate)+"更新")
					
					if(res.viewPermission == 3){//私有
						$("#share-box a").removeClass('active');
						$("#share-box a[data-type='privateDoc']").addClass('active');
					}else if(res.viewPermission == 2){//公开
						$("#share-box a").removeClass('active');
						$("#share-box a[data-type='shareDoc']").addClass('active');
						
						$('.share-href input[name="viewUrl"]').val(res.viewUrl).attr('data-viewUrl',res.viewUrl);
						$(".share-href .share-ewm img").attr('src',res.viewUrlQr);
						
					}
					//设置分享中设置复制链接按钮
					var clipboard = new Clipboard('.share-href input[name="viewUrl"] + a', {
					    // 点击copy按钮，直接通过text直接返回复印的内容
					        text: function() {
					            return $('.share-href input[name="viewUrl"]').val();
					        }
					    });

					    clipboard.on('success', function(e) {
					        console.log(e);
					        module.showMessage("复制成功");
					    });

					    clipboard.on('error', function(e) {
					        console.log(e);
					        module.showMessage("复制失败，请重试");
					    });
					
				}
			},
			error:function(res){
				module.showMessage(res)
			}
		})
	}

	function getLastUpdate(time){
		var second10 = 10 * 1000;
		var minute = 60 * 1000;
		var hour = 60 * minute;
		var day = 24 * hour;
		var day2 = 48 * hour;
		var now = new Date();
		
		var date = new Date();
		date.setTime(time);
		
		var df = now.getTime() - time;
		//跨年 ,显示：“yyyy-mm-dd hh:mm”
		if(date.getFullYear() > now.getFullYear()){
			return date.Format("yyyy/MM/dd hh:mm");
		}
		//>48小时，显示：“mm-dd hh:mm”
		if(df > day2){
			return date.Format("MM/dd hh:mm");
		}
		//24小时 ~ 48小时 ,显示：“昨天 hh:mm”
		if(date.getDate() < now.getDate()){
			return date.Format("昨天 hh:mm");
		}else{//24小时以内 显示“今天 hh:mm”
			return date.Format("今天 hh:mm");
		}
		
	}
	
	function initNameInput(){
		$(".input-wrap input").keydown(resizeInput).keyup(resizeInput).each(resizeInput);
		$(".input-wrap input").blur(function(){
			if(!checkLogin() || !checkWork()){
				return;
			}
			var value = $(this).val();
			if(!value || !value.trim() || value.trim() == works.detail.name){
				return;
			}
			value = value.trim();
			
			$.ajax({
				url:config.ctx + "/admin/modifyWorksName.do",
				dataType:"json",
				data:{
					memberId:member.memberId,
					encryptKey:member.encryptKey,
					worksId:works.id,
					worksName:value
				},
				type:"post",
				success:function(res){
					console.log(res)
					works.detail = res;
					if(res.status == 0){//成功
						setWorksName(works.detail.name);
						module.showMessage("作品集名字修改成功")
					}else{
						module.showMessage(res.msg)
					}
				},
				error:function(res){
					module.showMessage(res)
					//名称修改失败，需要恢复到原来名称
					setWorksName(works.detail.name);
				}
			})
		})
	}
	
	function setWorksName(name){
		$("#name-span").text(name);
		$("#file-name").val(name);
		resizeInput()
	}

	function resizeInput(){
		var value = $("#file-name").val();
		$("#name-span").text(utils.isNullVal(value)?'未命名':value);
		var textWidth = $("#name-span").width();
		$("#file-name").width(textWidth);
		$(".re-name").css('left',(textWidth/2)+40+'px');
	}
	
	p.modifyPermission = function(permissionCode,cbOk){
		if(isLoading == true){
			return;
		}
		isLoading = true;
		if(!checkLogin() || !checkWork()){
			isLoading = false;
			return;
		}
		
		if(utils.isNullVal(permissionCode)){
			module.showMessage('permissionCode为空')
			isLoading = false;
			return;
		}else if(permissionCode != privateCode && permissionCode != shareCode){
			module.showMessage('permissionCode不正确')
			isLoading = false;
			return;
		}
		
		$.ajax({
			url:config.ctx + "/admin/modifyWorksPermission.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				worksId:works.id,
				permissionCode:permissionCode
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					works.detail = res;
					if(res.viewPermission == 3){//私有
						$("#share-box a").removeClass('active');
						$("#share-box a[data-type='privateDoc']").addClass('active');
					}else if(res.viewPermission == 2){//公开
						$("#share-box a").removeClass('active');
						$("#share-box a[data-type='shareDoc']").addClass('active');
						
						$(".share-href input[name='viewUrl']").val(res.viewUrl).attr('data-viewUrl',res.viewUrl);;
						$(".share-href .share-ewm img").attr('src',res.viewUrlQr);
						
					}
					
					if(cbOk){
						cbOk();
					}
				}else{
					module.showMessage(res.msg)
				}
				isLoading = false;
			},
			error:function(res){
				module.showMessage(res)
				isLoading = false;
			}
		})
		
	}
	
	p.deleteWorks = function(){
		if(isLoading == true){
			return;
		}
		isLoading = true;
		if(!checkLogin() || !checkWork()){
			return;
		}
		
		$.ajax({
			url:config.ctx + "/admin/removeWorks.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				worksId:works.id
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					module.showMessage('文档删除成功',1500,function(){
						var backUrl = document.referrer;
						if(backUrl.indexOf('main.html') >= 0){
							history.back();
						}else{
							gotoPage('main.html')
						}
					});
				}else{
					module.showMessage(res.msg)
				}
				isLoading = false;
			},
			error:function(res){
				module.showMessage(res)
				isLoading = false;
			}
		})
		
	}	

	p.loadArtworks = function(){
		if(!checkLogin() || !checkWork()){
			return;
		}
		$(".doc-content .alist").empty();
		$.ajax({
			url:config.ctx + "/admin/queryAllArtWorks.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				worksId:works.id
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0 && res.result.length > 0){//成功
					$.each(res.result,function(index,artwork){
						var art = 
							'<li data-id="'+artwork.id+'">'+
								'<img src="'+artwork.imageUrl+'"/>'+
								'<h3>'+artwork.artistName+'</h3>'+
								'<p>'+artwork.name+'</p>'+
								'<p>'+artwork.materialName+'</p>'+
								'<p>'+artwork.dimension+' '+artwork.createYear+'</p>'+
								'<p class="price">'+utils.formatMoney(artwork.price/100)+'</p>'+
							'</li>';
						$(".doc-content .alist").append(art);
					})
				}else{
					$(".doc-content").append('<div class="doc-none"><img src="img/icon_empty_1.png"/><p>请添加作品</p></div>')
				}
			},
			error:function(res){
				module.showMessage(res)
			}
		})
	}
	
	p.loadSortDoc = function(cbOk){
		if(!checkLogin() || !checkWork()){
			return;
		}
		$("#edit_doc_box ul").empty();
		$.ajax({
			url:config.ctx + "/admin/queryAllArtWorks.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				worksId:works.id
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0 && res.result.length > 0){//成功
					$.each(res.result,function(index,artwork){
						var art = '<li data-id="'+artwork.id+'">'+
									'<div class="pic" style="background-image: url('+artwork.imageUrl+');"></div>'+
									'<span>'+artwork.artistName+' / '+artwork.name+' / '+artwork.materialName+' / '+artwork.dimension+' '+artwork.createYear+' / '+utils.formatMoney(artwork.price/100)+'</span>'+
									'<a href="javascript:fileOp.deleteArtwork('+artwork.id+')" class="del"></a>'+
									'<a href="javascript:void(0)" class="sort"></a>'+
								'</li>';
						$("#edit_doc_box ul").append(art);
					})
					if(cbOk){
						cbOk();
					}
				}else{
					module.showMessage("该作品集下没有作品，不能编辑")
				}
			},
			error:function(res){
				module.showMessage(res)
			}
		})
	}
	
	p.modifyArtworkSeq = function(cbOk){
		if(!checkLogin() || !checkWork()){
			return;
		}
		var artworkIds = [];
		$.each($("#edit_doc_box ul li"),function(index,obj){
			artworkIds.push(parseInt($(obj).attr("data-id")));
		})
		
		if(artworkIds.length <= 0){
			cbOk();
		}
		$.ajax({
			url:config.ctx + "/admin/modifyArtworkSeq.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				worksId:works.id,
				artworkIds:artworkIds
			},
			type:"post",
			traditional: true,  
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					if(cbOk){
						cbOk();
					}
				}else{
					module.showMessage("作品排序失败:"+res.msg)
				}
			},
			error:function(res){
				module.showMessage(res)
			}
		})
	}
	
	p.createPreviewEwm = function(cbOk){
		if(!checkLogin() || !checkWork()){
			return;
		}
		$.ajax({
			url:config.ctx + "/admin/createPreviewUrl.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				worksId:works.id
			},
			type:"post",
			traditional: true,  
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					if(cbOk){
						cbOk(res.previewUrl);
					}
				}else{
					module.showMessage("创建二维码失败："+res.msg);
				}
			},
			error:function(res){
				module.showMessage(res)
			}
		})
	}
	
	p.deleteArtwork = function(artworkId,cbOk){
		if(!checkLogin() || !checkWork()){
			return;
		}
		if(utils.isNullVal(artworkId)){
			module.showMessage('作品ID不能为空')
			return;
		}
		var data = {
			memberId:member.memberId,
			encryptKey:member.encryptKey,
			worksId:works.id,
			artworkId:artworkId
		}
		
		$.ajax({
			url:config.ctx + "/admin/removeArtwork.do",
			dataType:"json",
			data:data,
			type:"post", 
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					module.showMessage("作品删除成功")
					$(".doc-content .alist li[data-id="+artworkId+"],#edit_doc_box ul li[data-id="+artworkId+"]").remove();
					
					if($(".doc-content .alist li").length <= 0){
						layui.use('layer',function(){
								var layer = layui.layer;
								layer.closeAll();
							}
						)
						$(".doc-content").append('<div class="doc-none"><img src="img/icon_empty_1.png"/><p>请添加作品</p></div>')
					}
					if(cbOk){
						cbOk();
					}
				}else{
					module.showMessage("作品删除失败："+res.msg);
				}
			},
			error:function(res){
				module.showMessage(res)
			}
		})
	}
	
	p.addArtwork = function(cbOk){
		if(isLoading == true){
			return;
		}
		isLoading = true;
		if(!checkLogin() || !checkWork()){
			return;
		}
		var artVals = {};
		var inputs = $(".new-doc-item input");
		
		for(var i = 0; i < inputs.length; i++){
			var $input = $(inputs[i]);
			var name = $input.attr('name');
			var value = $input.val();
			if(name == 'imageUrl'){
				value = $input.attr('data-imgUrl');
			}
			
			if($input.attr('require') == 'true' && utils.isNullVal(value)){
				var attrName = $input.attr('placeholder');
				if(name == 'imageUrl'){
					module.showMessage('请上传'+attrName)
					isLoading = false;
					return;
				}
				module.showMessage('请填写'+attrName)
				isLoading = false;
				return;
			}
			artVals[name] = value;
		}
		

		if(!utils.isPrice(artVals.price)){
			module.showMessage('作品价格格式不正确,仅允许两位小数')
			isLoading = false;
			return;
		}
		
		artVals.memberId = member.memberId;
		artVals.encryptKey = member.encryptKey;
		artVals.worksId = works.id;
		
		
		$.ajax({
			url:config.ctx + "/admin/addArtwork.do",
			dataType:"json",
			data:artVals,
			type:"post", 
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					var art = 
						'<li data-id="'+res.artworkId+'">'+
							'<img src="'+artVals.imageUrl+'"/>'+
							'<h3>'+artVals.artistName+'</h3>'+
							'<p>'+artVals.artworkName+'</p>'+
							'<p>'+artVals.material+'</p>'+
							'<p>'+artVals.dimension+' '+artVals.createYear+'</p>'+
							'<p class="price">'+utils.formatMoney(artVals.price)+'</p>'+
						'</li>';
					$(".doc-content .alist").append(art);
					module.showMessage("添加作品成功")
					
					if($(".doc-content .doc-none").length > 0){
						$(".doc-content .doc-none").remove();
					}
					
					if(cbOk){
						cbOk();
					}
					isLoading = false;
				}else{
					module.showMessage("添加作品失败："+res.msg);
					isLoading = false;
				}
			},
			error:function(res){
				module.showMessage(res)
				isLoading = false;
			}
		})
	}
	
	p.feedBack = function(cbOk){
		if(!checkLogin()){
			return;
		}
		var content = $(".layer-feedback textarea").val();
		
		if(utils.isNullVal(content)){
			module.showMessage('请输入反馈信息')
			return;
		}else if(content.length > 500){
			module.showMessage('反馈信息不能超过500字')
			return;
		}		
		
		$.ajax({
			url:config.ctx + "/admin/addFeedBack.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				detail:content
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					module.showMessage('反馈信息提交成功')
					if(cbOk){
						cbOk();
					}
				}else{
					module.showMessage(res.msg)
				}
				isLoading = false;
			},
			error:function(res){
				module.showMessage(res)
				isLoading = false;
			}
		})
		
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
	
	function checkWork(){
		if(utils.isNullVal(works.id)){
			module.showMessage('作品集ID不能为空')
			return false;
		}
		return true;
	}
	
	return p;
})()

fileOp.init();