$(function() {

	$(document).not($('.file-list li > a.edit')).on('click', function() {
		$(".file-list li .file-edit-box").hide();
		$(".file-list li").removeClass("active");
	})

	var isLoading = false;
	
	var pageCode = null;
	var pageArray = ['desktop','recently','recycle','analysis'];
	
	layui.use('layer', function() {
		var $ = layui.jquery,
			layer = layui.layer;

		var active = {
			layerIndex: {
				feedBack: null,
				myDesktop: null,
				delDoc: null
			},
			feedBack: function() {
				active.layerIndex.feedBack = layer.open({
					title: '',
					type: 1,
					skin: 'layui-layer-tipB layer-tips-box layer-feedback position_t-10_l50 lc',
					area: ['430px', '220px'],
					shade: [0.01, '#fff'],
					shadeClose: true,
					offset: 'lb',
					move: false,
					resize: false,
					content: '<div style="padding:15px 20px 0;"><textarea placeholder="请提出您的宝贵意见，以帮助改进我们的产品" style="width:370px;height:110px;"></textarea></div>',
					btn: ['发布'],
					btnAlign: 'c',
					yes: function() {
						feedBack(function(){
							layer.close(active.layerIndex.feedBack);
						})
					},
					anim: -1,
					isOutAnim: false
				});
			},
			desktop: function() {
				pageCode = 'desktop';
				history.replaceState(null, null, '?page='+pageCode);
				loadDesktopList();
			},
			recently: function() {
				pageCode = 'recently';
				history.replaceState(null, null, '?page='+pageCode);
				loadRecentlyList()
			},
			recycle: function(){
				pageCode = 'recycle';
				history.replaceState(null, null, '?page='+pageCode);
				loadTrashWorksList();
			},
			analysis: function(){
				pageCode = 'analysis';
				var docId = $.getUrlParam('docId');
				history.replaceState(null, null, '?page='+pageCode+(utils.isNullVal(docId)?'':'&docId='+docId));
				$(".layui-body").load("data-analysis.html");
			},
			showFileEdit: function() {
				var that = this;
				$(".file-list li .file-edit-box").hide();
				$(".file-list li").removeClass("active");

				$(that).parent("li").addClass("active");
				$(that).siblings(".file-edit-box").show();

			},
			toDocPage: function() {
				var that = this;
				var docId = $(that).data('docid');
				window.location.href = 'document.html' + (docId ? '?docId=' + docId : '');
			},
			newDoc: function(){
				newDoc()
			},
			delDoc: function(){
				var that = this;
				
				layer.closeAll();
				active.layerIndex.delDoc = layer.open({
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
					  deleteWorks(docId,function(){
			    		  layer.close(active.layerIndex.delDoc);
			    	  })
			      },
			      no:function(){
			      	layer.close(active.layerIndex.delDoc);
			      },
				  anim :-1,
				  isOutAnim: false
			    });
			},
			clone: function(){
				var that = this;
				
				layer.closeAll();
				active.layerIndex.clone = layer.open({
				  title:'创建副本',
			      type: 1,
			      skin: 'layer-normal-center btn_position_t-30', 
			      area: ['330px', '230px'], 
			      shade: [0.3, '#000'],
			      shadeClose:true,
			      move:false,
			      resize:false,
			      content: '<div style="line-height:90px;text-align:center;color:#50565A;">您确定要创建此文档的副本么？</div>',
			      btn:['确定','取消'],
			      btnAlign:'c',
			      yes:function(){
					  var docId = $(that).data('docid');
					  cloneDoc(docId,function(){
						  active[pageCode].call();
					      layer.close(active.layerIndex.clone);
			    	  })
			      },
			      no:function(){
			      	layer.close(active.layerIndex.clone);
			      },
				  anim :-1,
				  isOutAnim: false
			    });
			},
			recoverDoc:function(){
				var that = this;
				var docId = $(that).data('docid');
				recoverDoc(docId)
			},
			delEntirely: function(){
				var that = this;
				
				layer.closeAll();
				active.layerIndex.delEntirely = layer.open({
				  title:'删除文档',
			      type: 1,
			      skin: 'layer-normal-center btn_position_t-30', 
			      area: ['330px', '230px'], 
			      shade: [0.3, '#000'],
			      shadeClose:true,
			      move:false,
			      resize:false,
			      content: '<div style="line-height:90px;text-align:center;color:#50565A;">彻底删除后无法恢复，确定删除？</div>',
			      btn:['确定','再想想'],
			      btnAlign:'c',
			      yes:function(){
					  var docId = $(that).data('docid');
					  deleteEntirely(docId,function(){
			    		  layer.close(active.layerIndex.delEntirely);
			    	  })
			      },
			      no:function(){
			      	layer.close(active.layerIndex.delEntirely);
			      },
				  anim :-1,
				  isOutAnim: false
			    });
			},
			analysisDoc:function(){
				var that = this;
				pageCode = 'analysis';
				var docId = $(that).data('docid');
				history.replaceState(null, null, '?page='+pageCode+(utils.isNullVal(docId)?'':'&docId='+docId));
				$('.nav-event.analysis').click();
			}
		}
		
		var member = null;
		
		function gotoAnalysis(docId){
			
		}
		
		function deleteWorks(docid,cbOk){
			if(isLoading == true){
				return;
			}
			isLoading = true;
			if(!checkLogin()){
				return;
			}
			if(utils.isNullVal(docid)){
				module.showMessage('文档ID不能为空')
				isLoading = false;
				return;
			}
			
			$.ajax({
				url:config.ctx + "/admin/removeWorks.do",
				dataType:"json",
				data:{
					memberId:member.memberId,
					encryptKey:member.encryptKey,
					worksId:docid
				},
				type:"post",
				success:function(res){
					console.log(res)
					if(res.status == 0){//成功
						module.showMessage('文档删除成功');
						var docItem = $(".file-list li[data-docid='"+docid+"']");
						if(docItem.siblings().length > 0){
							docItem.remove();
						}else{
							var dataBox = docItem.parents('.file-date-list');
							console.log(dataBox)
							if(dataBox && dataBox.length > 0){
								if(dataBox.siblings().length <= 0){
									$(".ypage-body").append('<div class="ypage-none"><img src="img/icon_empty_1.png"/><p>您还没有创建作品集，赶快新建一个吧~</p></div>')
								}
								dataBox.remove()
							}else{
								docItem.remove();
								$(".ypage-body").append('<div class="ypage-none"><img src="img/icon_empty_1.png"/><p>您还没有创建作品集，赶快新建一个吧~</p></div>')
							}
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
		
		function newDoc(){
			if(isLoading == true){
				return;
			}
			isLoading = true;
			if(!checkLogin()){
				return;
			}
			
			$.ajax({
				url:config.ctx + "/admin/addWorks.do",
				dataType:"json",
				data:{
					memberId:member.memberId,
					encryptKey:member.encryptKey
				},
				type:"post",
				success:function(res){
					console.log(res)
					if(res.status == 0){//成功
						var worksId = res.worksId;
						gotoPage('document.html?docId='+worksId);
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
		
		function loadDesktopList(){
			if(isLoading == true){
				return;
			}
			isLoading = true;
			if(!checkLogin()){
				module.showMessage('未登录')
				return;
			}
			$.ajax({
				url:config.ctx + "/admin/loadWorksList.do",
				dataType:"json",
				data:{
					memberId:member.memberId,
					encryptKey:member.encryptKey,
					isOnlyDesk:1
				},
				type:"post",
				success:function(res){
					console.log(res)
					if(!$("#desktop") || $("#desktop").length <= 0){
						$(".layui-body").empty().append('<div class="ypage-layout" id="desktop"></div>')
					}
					if(res.status == 0){//成功
						var content = '<div class="ypage-header">' +
							'<div class="ypage-header-title">我的桌面</div>' +
							'</div>';
						content = content + '<div class="ypage-body"><ul class="file-list"></ul></div>';
						$("#desktop").empty().append(content);

						if(res.result.length <= 0){
							isLoading = false;
							$(".ypage-body").append('<div class="ypage-none"><img src="img/icon_empty_1.png"/><p>您还没有创建作品集，赶快新建一个吧~</p></div>')
							return;
						}
						
						if(res.result && res.result.length > 0){
							$.each(res.result,function(index,obj){
								var file = '<li class="main-event doc-elem" data-type="toDocPage" data-docid="'+obj.id+'" style="z-index:'+(9999999-index)+'">' +
										'<img src="img/icon_file.png"/>' +
										'<p class="workname">'+obj.name+'</p>' +
										'<a href="javascript:void(0)" data-type="showFileEdit" class="edit main-event"></a>' +
										'<div class="main-event layui-layer layui-layer-tips layer-tips-box layer-tips-white file-edit-box layer-anim" type="tips" times="6" showtime="0" contype="object" style="display:none;height: 210px; ">'+
										'<div id="" class="layui-layer-content" style="height: 190px;">' +
										'<ul>' +
										'<li><div class="layui-form-item"><div class="layui-input-block" style="margin-left:0;">' +
										'<input type="text" name="workname" lay-verify="phone" autocomplete="off" class="layui-input"  data-docid="'+obj.id+'" value="'+obj.name+'">' +
										'</div></div></li>' +
										'<li><a href="javascript:void(0)" class="clone main-event" data-type="clone" data-docid="'+obj.id+'">创建副本</a></li>' +
										'<li><a href="javascript:void(0)" class="analysis main-event" data-type="analysisDoc" data-docid="'+obj.id+'">数据分析</a></li>' +
										'<li><a href="javascript:void(0)" class="del main-event" data-type="delDoc"  data-docid="'+obj.id+'">删除</a></li>' +
										'</ul>' +
										'<i class="layui-layer-TipsG layui-layer-TipsB"></i></div><span class="layui-layer-setwin"></span>' +
										'</div></li>';
								$(".file-list").append(file);
							})
						}
						
						$(".doc-elem input[name='workname']").blur(function(){
							modifyWorksName($(this))
						})
						bindMainEvent()
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
		
		function loadRecentlyList(){
			if(isLoading == true){
				return;
			}
			isLoading = true;
			if(!checkLogin()){
				return;
			}
			$.ajax({
				url:config.ctx + "/admin/loadWorksList.do",
				dataType:"json",
				data:{
					memberId:member.memberId,
					encryptKey:member.encryptKey,
					isOrderLastDay:1
				},
				type:"post",
				success:function(res){
					console.log(res)
					if(!$("#desktop") || $("#desktop").length <= 0){
						$(".layui-body").empty().append('<div class="ypage-layout" id="desktop"></div>')
					}
					if(res.status == 0){//成功
						var content = '<div class="ypage-header"><div class="ypage-header-title">最近使用</div></div><div class="ypage-body"></div>';
						$("#desktop").empty().append(content);

						if(res.result.length <= 0){
							isLoading = false;
							$(".ypage-body").append('<div class="ypage-none"><img src="img/icon_empty_1.png"/><p>您还没有创建作品集，赶快新建一个吧~</p></div>')
							return;
						}
						
						fillRecentlyList(res.result) 
						
						$(".doc-elem input[name='workname']").blur(function(){
							modifyWorksName($(this))
						})
						bindMainEvent()
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
		
		function loadTrashWorksList(){
			if(isLoading == true){
				return;
			}
			isLoading = true;
			if(!checkLogin()){
				return;
			}
			$.ajax({
				url:config.ctx + "/admin/loadTrashWorksList.do",
				dataType:"json",
				data:{
					memberId:member.memberId,
					encryptKey:member.encryptKey
				},
				type:"post",
				success:function(res){
					console.log(res)
					if(!$("#desktop") || $("#desktop").length <= 0){
						$(".layui-body").empty().append('<div class="ypage-layout" id="desktop"></div>')
					}
					if(res.status == 0){//成功
						var content = '<div class="ypage-header"><div class="ypage-header-title">回收站</div></div><div class="ypage-body"></div>';
						$("#desktop").empty().append(content);
						
						if(res.result.length <= 0){
							isLoading = false;
							$(".ypage-body").append('<div class="ypage-none"><img src="img/icon_empty_1.png"/><p>您还没有创建作品集，赶快新建一个吧~</p></div>')
						}
						
						fillTransList(res.result) 
						
						bindMainEvent()

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
		
		function fillRecentlyList(result) {
			var timeStr = null;
			var fileDataList = '';
			var dateInfo = null;
			var lastUpdate = null;
			if(result && result.length > 0){
				$.each(result,function(index,obj){
					var date = new Date(obj.lastUpdate);
					if(timeStr == null){
						timeStr = date.Format('yyyy/M/d');
					}else if(timeStr != date.Format('yyyy/M/d')){
						dateInfo = '<div class="file-date-line"><span>'+getTimeStr(lastUpdate)+'</span></div>';
						$("#desktop .ypage-body").append('<div class="file-date-list">' + dateInfo +'<ul class="file-list">'+ fileDataList +'</ul>'+ '</div>');
						fileDataList = '';
						timeStr = date.Format('yyyy/M/d');
					}
					lastUpdate = obj.lastUpdate;
					fileDataList += '<li class="main-event doc-elem" data-type="toDocPage" data-docid="'+obj.id+'" style="z-index:'+obj.lastUpdate/1000+';">' +
								'<img src="img/icon_file.png"/>' +
								'<p class="workname">'+obj.name+'</p>' +
								'<a href="javascript:void(0)" data-type="showFileEdit" class="edit main-event"></a>' +
								'<div class="main-event layui-layer layui-layer-tips layer-tips-box layer-tips-white file-edit-box layer-anim" type="tips" times="6" showtime="0" contype="object" style="display:none;height:210px;">'+
								'<div id="" class="layui-layer-content" style="height: 190px;">' +
								'<ul>' +
								'<li><div class="layui-form-item"><div class="layui-input-block" style="margin-left:0;">' +
								'<input type="text" name="workname" lay-verify="phone" autocomplete="off" class="layui-input" data-docid="'+obj.id+'" value="'+obj.name+'">' +
								'</div></div></li>' +
								'<li><a href="javascript:void(0)" class="clone main-event" data-type="clone" data-docid="'+obj.id+'">创建副本</a></li>' +
								'<li><a href="javascript:void(0)" class="analysis main-event" data-type="analysisDoc" data-docid="'+obj.id+'">数据分析</a></li>' +
								'<li><a href="javascript:void(0)" class="del main-event" data-type="delDoc" data-docid="'+obj.id+'">删除</a></li>' +
								'</ul>' +
								'<i class="layui-layer-TipsG layui-layer-TipsB"></i></div><span class="layui-layer-setwin"></span>' +
								'</div></li>'
					
				})
				
				dateInfo = '<div class="file-date-line"><span>'+getTimeStr(lastUpdate)+'</span></div>';
				$("#desktop .ypage-body").append('<div class="file-date-list">' + dateInfo +'<ul class="file-list">'+ fileDataList +'</ul>'+ '</div>');
			}
		}
		
		function fillTransList(result) {
			var timeStr = null;
			var fileDataList = '';
			var dateInfo = null;
			var lastUpdate = null;
			if(result && result.length > 0){
				$.each(result,function(index,obj){
					var date = new Date(obj.lastUpdate);
					if(timeStr == null){
						timeStr = date.Format('yyyy/M/d');
					}else if(timeStr != date.Format('yyyy/M/d')){
						dateInfo = '<div class="file-date-line"><span>'+getTimeStr(lastUpdate)+'</span></div>';
						$("#desktop .ypage-body").append('<div class="file-date-list">' + dateInfo +'<ul class="file-list">'+ fileDataList +'</ul>'+ '</div>');
						fileDataList = '';
						timeStr = date.Format('yyyy/M/d');
					}
					lastUpdate = obj.lastUpdate;
					fileDataList += '<li class="doc-elem" data-docid="'+obj.id+'" style="z-index:'+obj.lastUpdate/1000+';">' +
								'<img src="img/icon_file.png"/>' +
								'<p class="workname">'+obj.name+'</p>' +
								'<a href="javascript:void(0)" data-type="showFileEdit" class="edit main-event"></a>' +
								'<div class="main-event layui-layer layui-layer-tips layer-tips-box layer-tips-white file-edit-box layer-anim" type="tips" times="6" showtime="0" contype="object" style="display:none;height:105px;">'+
								'<div id="" class="layui-layer-content" style="height: 95px;padding:5px 0">' +
								'<ul>' +
								'<li><a href="javascript:void(0)" class="recover main-event" data-type="recoverDoc" data-docid="'+obj.id+'">恢复</a></li>' +
								'<li><a href="javascript:void(0)" class="del main-event" data-type="delEntirely" data-docid="'+obj.id+'" style="color:#FF1036;">彻底删除</a></li>' +
								'</ul>' +
								'<i class="layui-layer-TipsG layui-layer-TipsB"></i></div><span class="layui-layer-setwin"></span>' +
								'</div></li>'
					
				})
				
				dateInfo = '<div class="file-date-line"><span>'+getTimeStr(lastUpdate)+'</span></div>';
				$("#desktop .ypage-body").append('<div class="file-date-list">' + dateInfo +'<ul class="file-list">'+ fileDataList +'</ul>'+ '</div>');
			}
		}
		
		function getTimeStr(time){

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
				return date.Format("yyyy / M/ d");
			}
			//>48小时，显示：“mm-dd hh:mm”
			if(df > day2){
				return date.Format("M / d");
			}
			//24小时 ~ 48小时 ,显示：“昨天 hh:mm”
			if(date.getDate() < now.getDate()){
				return date.Format("昨天");
			}else{//24小时以内 显示“今天 hh:mm”
				return date.Format("今天");
			}
			
		}
		
		function deleteEntirely(docid,cbOk){
			if(isLoading == true){
				return;
			}
			isLoading = true;
			if(!checkLogin()){
				return;
			}
			if(utils.isNullVal(docid)){
				module.showMessage('文档ID不能为空')
				isLoading = false;
				return;
			}
			
			$.ajax({
				url:config.ctx + "/admin/deleteWorks.do",
				dataType:"json",
				data:{
					memberId:member.memberId,
					encryptKey:member.encryptKey,
					worksId:docid
				},
				type:"post",
				success:function(res){
					console.log(res)
					if(res.status == 0){//成功
						var docItem = $(".file-list li[data-docid='"+docid+"']");
						if(docItem.siblings().length > 0){
							docItem.remove();
						}else{
							var dataBox = docItem.parents('.file-date-list');
							console.log(dataBox)
							if(dataBox && dataBox.length > 0){
								if(dataBox.siblings().length <= 0){
									$(".ypage-body").append('<div class="ypage-none"><img src="img/icon_empty_1.png"/><p>您还没有创建作品集，赶快新建一个吧~</p></div>')
								}
								dataBox.remove()
							}else{
								docItem.remove();
								$(".ypage-body").append('<div class="ypage-none"><img src="img/icon_empty_1.png"/><p>您还没有创建作品集，赶快新建一个吧~</p></div>')
							}
						}
						if(cbOk){
							cbOk();
						}
						module.showMessage('文档删除成功');
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
		
		function recoverDoc(docid,cbOk){
			if(isLoading == true){
				return;
			}
			isLoading = true;
			if(!checkLogin()){
				return;
			}
			if(utils.isNullVal(docid)){
				module.showMessage('文档ID不能为空')
				isLoading = false;
				return;
			}
			$.ajax({
				url:config.ctx + "/admin/restoreWorks.do",
				dataType:"json",
				data:{
					memberId:member.memberId,
					encryptKey:member.encryptKey,
					worksId:docid,
				},
				type:"post",
				success:function(res){
					console.log(res)
					if(res.status == 0){//成功
						module.showMessage("作品集恢复成功")
						var docItem = $(".file-list li[data-docid='"+docid+"']");
						if(docItem.siblings().length > 0){
							docItem.remove();
						}else{
							var dataBox = docItem.parents('.file-date-list');
							console.log(dataBox)
							if(dataBox && dataBox.length > 0){
								if(dataBox.siblings().length <= 0){
									$(".ypage-body").append('<div class="ypage-none"><img src="img/icon_empty_1.png"/><p>您还没有创建作品集，赶快新建一个吧~</p></div>')
								}
								dataBox.remove()
							}else{
								docItem.remove();
								$(".ypage-body").append('<div class="ypage-none"><img src="img/icon_empty_1.png"/><p>您还没有创建作品集，赶快新建一个吧~</p></div>')
							}
						}
						if(cbOk){
							cbOk();
						}
						isLoading = false;
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
		
		function cloneDoc(docid,cbOk){
			if(isLoading == true){
				return;
			}
			isLoading = true;
			if(!checkLogin()){
				return;
			}
			if(utils.isNullVal(docid)){
				module.showMessage('文档ID不能为空')
				isLoading = false;
				return;
			}
			$.ajax({
				url:config.ctx + "/admin/createReplicaWorks.do",
				dataType:"json",
				data:{
					memberId:member.memberId,
					encryptKey:member.encryptKey,
					worksId:docid,
				},
				type:"post",
				success:function(res){
					console.log(res)
					if(res.status == 0){//成功
						module.showMessage("副本创建成功")
						isLoading = false;
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
		
		function modifyWorksName($input){
			if(isLoading == true){
				return;
			}
			isLoading = true;
			if(!checkLogin()){
				return;
			}
			var docid = $input.data('docid');
			if(utils.isNullVal(docid)){
				module.showMessage('文档ID不能为空')
				isLoading = false;
				return;
			}
			var worksName = $input.val();
			if(utils.isNullVal(worksName)){
				module.showMessage('文档名称不能为空')
				isLoading = false;
				return;
			}
			
			$.ajax({
				url:config.ctx + "/admin/modifyWorksName.do",
				dataType:"json",
				data:{
					memberId:member.memberId,
					encryptKey:member.encryptKey,
					worksId:docid,
					worksName:worksName
				},
				type:"post",
				success:function(res){
					console.log(res)
					if(res.status == 0){//成功
						module.showMessage("作品集名字修改成功")
						$input.parents(".doc-elem").find('.workname').text(worksName);
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

		function feedBack(cbOk){
			if(isLoading == true){
				return;
			}
			isLoading = true;
			if(!checkLogin()){
				return;
			}
			var content = $(".layer-feedback textarea").val();
			
			if(utils.isNullVal(content)){
				module.showMessage('请输入反馈信息')
				isLoading = false;
				return;
			}else if(content.length > 500){
				module.showMessage('反馈信息不能超过500字')
				isLoading = false;
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
				module.showMessage('您未登录',1500,function(){
					gotoPage('login.html');
				})
				return false;
			}
			return true;
		}
		
		function bindEvent() {
			$('.nav-event').unbind("click").on('click', function() {
				var $aParent = $(this).parent();

				if($aParent.hasClass('layui-this')) {
					return;
				}
				if($aParent.hasClass('layui-nav-item')) {
					$(".layui-nav .layui-nav-item").removeClass('layui-this');
					$aParent.addClass('layui-this');
				}
				var type = $(this).data('type');
				console.log(type)
				active[type] ? active[type].call(this) : '';
			});
		}

		function bindMainEvent() {
			$('.main-event').unbind('click').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
				event.stopPropagation();
			});
		}
		
		bindEvent();
		
		var page = $.getUrlParam('page')
		if(!utils.isNullVal(page) && pageArray.indexOf(page) >= 0){
			$('.nav-event.'+page).click();
		}else{
			$('.nav-event.desktop').click();
		}
		
	})
})
