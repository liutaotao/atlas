$(function(){
	
	layui.use(['element','laydate','table'], function(){
	    var element = layui.element;
	    var laydate = layui.laydate;
	    laydate.render({
		    elem: '#range-start' //指定元素
		});
		
		laydate.render({
		    elem: '#range-end' //指定元素
		});
		
	})
	
	// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('data-chart'));

        // 指定图表的配置项和数据
        option = {
    title: {
        text: '访问人数',
        show:false
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['访问人数'],
        bottom:'0'
    },
    grid: {
    	top:'13%',
        left: '7%',
        right: '5%',
        bottom: '13%',
        containLabel: true
    },
    backgroundColor:'#fff',
/*    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },*/
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['2017-05-01','2017-05-02','2017-05-03','2017-05-04','2017-05-05','2017-05-06','2017-05-07'],
        axisLine:{
        	lineStyle:{
        		color:'#B8B8B8',
        		width:2
        	}
        },
        axisTick:{
        	lineStyle:{
        		color:'#A9BFD5'
        	}
        }
    },
    yAxis: {
        type: 'value',
        axisLine:{
        	lineStyle:{
        		color:'#747474',
        		width:0
        	}
        },
        axisTick:{
        	show:false
        },
        splitLine:{
            lineStyle:{
                color:'#f6f6f6'
            }
        }
    },
    axisPointer:{
        lineStyle:{
            type:'dotted',
            color:'#44B449'
        },
        z:0
    },
    series: [
        {
            name:'访问人数',
            type:'line',
            data:[120, 132, 101, 134, 90, 230, 210],
            areaStyle: {
            	normal: {
            		color:'#F9FCFD'
            	}
            },
            itemStyle:{
            	normal:{
            		color:{
					    type: 'radial',
					    x: 0.5,
					    y: 0.5,
					    r: 0.5,
					    colorStops: [{
					        offset: 0, color: '#44B449' // 0% 处的颜色
					    }, {
					        offset: 0.7, color: '#44B449' // 100% 处的颜色
					    }, {
					        offset: 0.8, color: '#fff' // 100% 处的颜色
					    }, {
					        offset: 1, color: '#fff' // 100% 处的颜色
					    }],
					    globalCoord: false // 缺省为 false
					}
            	}
            },
            lineStyle:{
            	normal:{
            		color:'#44B449'
            	}
            },
            symbol:'circle',
            symbolSize:8,
            markLine:{
            	lineStyle:{
            		emphasis:{
            			color:'#44B449',
            			type:'dashed'
            		}
            	}
            }
        }
    ]
};


        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        

})


function renderAnalysis(){
	this.member = null;
	
	this.render = function(){
		console.log('render');
	}
	this.getTableUserDataList = function(res){
		console.log('getUserDataList:')
		console.log(res)
		var artDatas = [];
		if(res.result && res.result.length > 0){
			$.each(res.result,function(index,obj){
				var date = new Date();
				date.setTime(obj.endDate);
				artDatas.push({
					memberFace:'<div data-id="'+obj.memberId+'" class="avatar" style="background-image:url('+obj.memberFace+')"></div>',
					memberNickName:obj.memberNickname,
					endDate:date.Format('yyyy-MM-dd hh:mm:ss'),
					viewCount:obj.viewCount,
					averagDurationStr:obj.averagDurationStr,
					viewRateStr:obj.viewRateStr,
					likeRateStr:'<div class="layui-progress" style="margin-top:22px;"><div class="layui-progress-bar layui-bg-red" lay-percent="30%" style="width:'+obj.likeRateStr+'"></div></div>'
				})
			})
		}
		console.log(artDatas)
		return artDatas;
	}
	this.getTableArtworkDataList = function(res){
		var artDatas = [];
		if(res.result && res.result.length > 0){
			$.each(res.result,function(index,obj){
				artDatas.push({
					artworkImg:'<div data-id="'+obj.artworkId+'" class="pic" style="background-image:url('+obj.artworkImg+')"></div>',
					artworkName:obj.artworkName,
					uniqueVisitor:obj.uniqueVisitor,
					pageView:obj.pageView || obj.pageView == 0?obj.pageView:obj.viewCount,
					averagDurationStr:obj.averagDurationStr,
					viewRateStr:obj.viewRateStr,
					likeRateStr:'<div class="layui-progress" style="margin-top:22px;"><div class="layui-progress-bar layui-bg-red" lay-percent="30%" style="width:'+obj.likeRateStr+'"></div></div>'
				})
			})
		}
		return artDatas;
	}
	/**
	 * param:{
		tableElem:"#id",
		cols:[[{field: 'avatar', title:'用户头像', width: 80},
			  	{field: 'name', title:'用户名称（备注名）', width: 150}]],
		datas:[{avatar:'<div class="avatar" style="background-image:url(https://zhenyiart.oss-cn-beijing.aliyuncs.com/zhenyiart/ai-mall/store/1/goods/201708111626000876.png?x-oss-process=image/resize,w_+800,h_800,m_fill/auto-orient,1/quality,q_90/format,src)"></div>',
			  		name:'王淑婷（备注）'},
			  	{avatar:'<div class="avatar" style="background-image:url(https://zhenyiart.oss-cn-beijing.aliyuncs.com/zhenyiart/ai-mall/store/1/goods/201708111626000876.png?x-oss-process=image/resize,w_+800,h_800,m_fill/auto-orient,1/quality,q_90/format,src)"></div>',
			  		name:'王淑婷（备注）'}
			  	]
	   }
	 */
	this.renderTable = function(param){
		if(utils.isNullVal(param.tableElem) || utils.isNullVal(param.cols) || utils.isNullVal(param.datas)){
			module.showMessage('数据错误，无法绘制表格')
			return;
		}
		var tableIns = null;
		layui.use(['table'], function(){
			var table = layui.table;
			tableIns = table.render({
				  elem: param.tableElem //指定原始表格元素选择器（推荐id选择器）
				  ,skin:'nob'
				  ,cols: param.cols //设置表头
				  ,data:param.datas
				  ,done:function(res, curr, count){
					  if(param.done){
						  param.done(table);
					  }	
				  }
			})
		})
		return tableIns;
	}
	
	this.callInterface = function(url,data,succFunc,errFunc){
		$.ajax({
			type:"post",
			url:url,
			dataType:"json",
			data:data,
			success:function(res){
				if(res.status == 0){
					if(succFunc){
						succFunc(res);
					}
				}else{
					if(errFunc){
						errFunc(res);
					}else{
						module.showMessage(res.msg);
					}
				}
			},
			error:function(e){
				module.showMessage(e)
			}
		});
	}
	
	this.checkLogin = function(){
		this.member = getMember();
		if(!this.member){
			gotoPage('login.html')
			return false;
		}
		return true;
	}
		
}



var renderWorks = (function(){
	var p = new renderAnalysis();
	
	var isInit = false;
	var worksId = null;
	
	var currentPage = 0;
	var pageSize = 30;
	
	var artworkTable = null;
	var artworkTableElem = '#artwork-data';
	var artworkTableCols = [[
	 					  	{field: 'artworkImg', title:'作品图', width: 100},
						  	{field: 'artworkName', title:'作品名称', width: 90},
						  	{field: 'uniqueVisitor', title:'浏览人数', width: 90},
						  	{field: 'pageView', title:'浏览次数', width: 100},
						  	{field: 'averagDurationStr', title:'平均浏览时长', width: 110},
						  	{field: 'viewRateStr', title:'平均展示率', width: 100},
						  	{field: 'likeRateStr', title:'喜好特征强弱', width: 190},
						  ]];

	var userTableElem = '#user-data';
	var userTableCols = [[
						  	{field: 'memberFace', title:'用户头像', width: 100},
						  	{field: 'memberNickName', title:'用户名称', width: 70},
						  	{field: 'endDate', title:'最后访问', width: 160},
						  	{field: 'viewCount', title:'访问次数', width: 90},
						  	{field: 'averagDurationStr', title:'平均浏览时长', width: 80},
						  	{field: 'viewRateStr', title:'作品展示率', width: 90},
						  	{field: 'likeRateStr', title:'喜好特征强弱', width: 190},
						  ]];
	
	p.init = function(){
		var docId = $.getUrlParam('docId');
		console.log('.....+'+docId)
		if(isInit == false){
			loadWorks(function(res){
				isInit = true;
				var workIndex = 0;
				for(var i = 0 ; i < res.result.length;i++){
					var doc = res.result[i];
					if(i == 0){
						worksId = doc.id;
					}
					if(!utils.isNullVal(docId) && doc.id == docId){
						worksId = docId;
						workIndex = i;
						break;
					}
				}

				render()
				
				var workLiH = $("#works-list li").height();
				$("#works-list").scrollTop(workIndex*workLiH);
				
				
				$("#works-list li[data-docid="+worksId+"]").addClass('on');
				$(".works-list").show();
				
				$("#works-list li").click(function(){
					if($(this).hasClass('on')){
						return;
					}
					worksId = $(this).data('docid');
					resetSituation()
					render();
					$(this).addClass('on').siblings().removeClass('on');
					
					history.replaceState(null, null, '?page=analysis'+(utils.isNullVal(worksId)?'':'&docId='+worksId));
				})
			})
		}else{
			render();
		}
	}
	
	function render(){
		$(".middle-list").hide().find('#artwork').empty().hide();
		$(".data-content").show().find('a.back').hide();
		$(".layui-tab .layui-tab-title li").show();
		$(".layui-tab .layui-tab-title li:first-child").click();
		$(".works-list").show();
		$("#user-info-box").hide();
		$("#user-box .search input[name='keyword']").val('');
		loadSituation();
		loadArtworkTable();
		loadUserTable();
	}
	
	function resetSituation(){
		$(".time-btns a,.siton-btns a").removeClass('on').unbind('click');
		$(".time-btns a:first-child").addClass('on');
	}
	
	function loadSituation(){
		console.log('Situation')
		var type = $(".time-btns a.on").data('type');
		
		if(!p.checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId)){
			return;
		}
		p.callInterface(
				config.ctx + "/analysis/loadWorkOverview.do", 
				{
					memberId:p.member.memberId,
					encryptKey:p.member.encryptKey,
					worksId:worksId,
					type:type
				}, 
				function(res){
					console.log(res)
					$("#sbtn-1").html('<p><span>'+res.uniqueVisitorCount+'</span>人</p><p>访问人数</p>');
					$("#sbtn-2").html('<p><span>'+res.pageViewCount+'</span>次</p><p>访问次数</p>')
					$("#sbtn-3").html('<p><span>'+res.averagDuration+'</span>'+res.averagDurationStr.replace(res.averagDuration,'')+'</p><p>平均浏览时长</p>')
					$("#sbtn-4").html('<p><span>'+res.viewRateStr.replace('%','')+'</span>%</p><p>作品展示率</p>')
					
					$(".time-btns a").unbind('click').click(function(){
						if($(this).hasClass('on')){
							return;
						}
						$(this).addClass('on').siblings().removeClass('on');
						loadSituation();
					})
				})
	}
	
	function loadArtworkTable(){
		console.log('ArtworkTable')
		if(!p.checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId)){
			return;
		}
		
		p.callInterface(
				config.ctx + "/analysis/loadArtworkAnalysis.do", 
				{
					memberId:p.member.memberId,
					encryptKey:p.member.encryptKey,
					worksId:worksId
				}, 
				function(res){
					console.log(res)
					artworkTable = p.renderTable({
						tableElem:artworkTableElem,
						cols:artworkTableCols,
						datas:p.getTableArtworkDataList(res),
						page:true,
						done:function(table){
							console.log('bind artwork table')
							$("#artwork-box tr").unbind('click').click(function(){
								var artworkId = $(this).find('td:first-child .pic').data('id');
								console.log(artworkId)
								renderArtwork.init(worksId,artworkId);
							})
							
							if($('#artwork-box .layui-none').length > 0){
								$('#artwork-box .layui-none').html('<img src="img/icon_empty_2.png" /><p>还没有用户查看此作品集</p>')
							}
						}
					})
					
					$("#artwork-box .content-box-title p").text('共'+res.result.length+'件作品')
				
				})
	}
	
	function loadUserTable(){
		console.log('userTable')
		if(!p.checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId)){
			return;
		}
		var keywords = $("#user-box .search input[name='keyword']").val();
		
		layui.use(['table'], function(){
			var table = layui.table;
			tableIns = table.render({
				  elem: userTableElem //指定原始表格元素选择器（推荐id选择器）
				  ,skin:'nob'
				  ,height:390
				  ,cols: userTableCols //设置表头
				  ,page:true
				  ,url:config.ctx + "/analysis/loadMemberWorksAnalysisPage.do"
				  ,method:'post'
				  ,where:{
					  memberId:p.member.memberId,
					  encryptKey:p.member.encryptKey,
						worksId:worksId,
						keywords:keywords
				  }
				  ,request:{
					  pageName:'currentPageNo'
					  ,limitName:'pageSize'
				  }
				  ,limit:pageSize
				  ,limits:null
				  ,response:{
					  statusName: 'status' //数据状态的字段名称，默认：code
						  ,statusCode: 0 //成功的状态码，默认：0
						  ,msgName: 'msg' //状态信息的字段名称，默认：msg
						  ,countName: 'totalCount' //数据总数的字段名称，默认：count
						  ,dataName: 'result' //数据列表的字段名称，默认：data
					  ,conbineData:p.getTableUserDataList
				  }
				  ,done:function(res, curr, count){
					  console.log('bind user table')
					  console.log(res)
						$("#user-box tr").unbind('click').click(function(){
							var userId = $(this).find('td:first-child .avatar').data('id');
							renderUser.init(worksId,userId);
						})
						
						if($('#user-box .layui-none').length > 0){
							$('#user-box .layui-none').html('<img src="img/icon_empty_2.png" /><p>还没有用户查看此作品集</p>')
						}
						  $("#user-box .content-box-title p").text('共'+res.totalCount+'位用户')
							
							$("#user-box input[name='keyword']").unbind('keyup').keyup(function(e){
								if(e.keyCode == 13){
									loadUserTable()
								}
							})
				  }
			})
		})
	}
	
	function loadWorks(cbOk){
		console.log('加载作品集列表')
		$("#works-list").empty();
		if(!p.checkLogin()){
			return;
		}
		$.ajax({
			url:config.ctx + "/admin/loadWorksList.do",
			dataType:"json",
			data:{
				memberId:p.member.memberId,
				encryptKey:p.member.encryptKey,
				isOrderLastDay:1
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					if(res.result && res.result.length > 0){
						$.each(res.result,function(index,obj){
							$("#works-list").append('<li data-docid="'+obj.id+'"><p>'+obj.name+'</p></li>');
						})
					}
					if(cbOk){
						cbOk(res);
					}
				}else{
					module.showMessage(res.msg)
				}
			},
			error:function(res){
				module.showMessage(res)
			}
		})
	}
	
	return p;
})()


var renderArtwork = (function(){
	var p = new renderAnalysis();
	
	var artworkId = null;
	var worksId = null;
	
	var currentPage = 0;
	var pageSize = 30;
	
	var userTableElem = '#user-data';
	var userTableCols = [[
						  	{field: 'memberFace', title:'用户头像', width: 100},
						  	{field: 'memberNickName', title:'用户名称', width: 100},
						  	{field: 'endDate', title:'最后浏览时间', width: 160},
						  	{field: 'viewCount', title:'访问次数', width: 100},
						  	{field: 'averagDurationStr', title:'平均浏览时长', width: 120},
						  	{field: 'likeRateStr', title:'喜好特征强弱', width: 200},
						  ]];
	
	p.init = function(docId,artId){
		if(utils.isNullVal(docId)){
			module.showMessage('文档id不能为空')
			return;
		}
		if(utils.isNullVal(artId)){
			module.showMessage('作品id不能为空')
			return;
		}
		
		worksId = docId;
		
		loadArtworkAnalysis(function(res){
			var artIndex = 0;
			for(var i = 0; i < res.result.length;i++){
				var artwork = res.result[i];
				if(i == 0){
					artworkId = artwork.artworkId;
				}
				if(artwork.artworkId == artId){
					artworkId = artId;
					artIndex = i;
					break;
				}
			}
			
			var artworkLiH = $("#artwork li").height();
			$(".middle-list .box").scrollTop(artIndex*artworkLiH);
			
			$(".middle-list .work-info").text($("#works-list li.on p").text())
			
			render();
			
			$('#artwork li[data-artworkid='+artworkId+']').addClass('on');
			
			$('#artwork li').unbind('click').click(function(){
				if($(this).hasClass('on')){
					return;
				}
				artworkId = $(this).data('artworkid');
				$(this).addClass('on').siblings().removeClass('on');
				render();					
			})
			
			$(".data-content a.back").css('display','inline-block').unbind('click').click(function(){
				renderWorks.init();
			})
			
		})
	}
	
	function render(){
		$("#user-box").hide();
		$("#user-info-box").hide();
		$(".layui-tab .layui-tab-title li:nth-child(2)").hide().siblings().show();
		$(".layui-tab .layui-tab-title li:first-child").click();
		$("#user").hide();
		$("#user-box .search input[name='keyword']").val('');
		resetSituation()
		loadSituation();
		loadUserTable();
	}

	function resetSituation(){
		$(".time-btns a,.siton-btns a").removeClass('on').unbind('click');
		$(".time-btns a:first-child").addClass('on');
	}
	function loadArtworkAnalysis(cbOk){
		if(!p.checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId)){
			module.showMessage('作品集ID不能为空')
			return;
		}
		
		p.callInterface(
				config.ctx + "/analysis/loadArtworkAnalysis.do", 
				{
					memberId:p.member.memberId,
					encryptKey:p.member.encryptKey,
					worksId:worksId
				}, 
				function(res){
					console.log(res)	
					$("#artwork").empty();
					if(res.result && res.result.length > 0){
						$.each(res.result,function(index,obj){
							var author = utils.isNullVal(obj.artistName)?'':'('+obj.artistName+')';
							$(".middle-list #artwork").append('<li data-artworkid="'+obj.artworkId+'">'+
									'<div class="avatar" style="background-image:url('+obj.artworkImg+')"></div>'+
									'<div class="layui-progress">'+
										'<div class="layui-progress-bar layui-bg-red" lay-percent="'+obj.likeRateStr+'" style="width:'+obj.likeRateStr+'"></div>'+
									'</div>'+
									'<div class="content">'+
										'<p class="name">'+obj.artworkName+'<span>'+author+'</span></p>'+
										/*'<p class="author">作者：'+obj.artistName+'</p>'+*/
									'</div>'+
								'</li>');
						})
					}
					
					$(".middle-list .box .title").text('共'+res.result.length+'件作品')
					$(".middle-list,.middle-list #artwork").show();
					$(".works-list").hide();
					
					if(cbOk){
						cbOk(res);
					}
				})
	}
	
	function loadSituation(){
		console.log('Situation')
		var type = $(".time-btns a.on").data('type');
		
		if(!p.checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId)){
			module.showMessage('作品集ID不能为空')
			return;
		}
		if(utils.isNullVal(artworkId)){
			module.showMessage('作品ID不能为空')
			return;
		}
		p.callInterface(
				config.ctx + "/analysis/loadArtworkOverview.do", 
				{
					memberId:p.member.memberId,
					encryptKey:p.member.encryptKey,
					worksId:worksId,
					artworkId:artworkId,
					type:type
				}, 
				function(res){
					console.log(res)
					$("#sbtn-1").html('<p><span>'+res.uniqueVisitorCount+'</span>人</p><p>访问人数</p>');
					$("#sbtn-2").html('<p><span>'+res.pageViewCount+'</span>次</p><p>访问次数</p>')
					$("#sbtn-3").html('<p><span>'+res.averagDuration+'</span>'+res.averagDurationStr.replace(res.averagDuration,'')+'</p><p>平均浏览时长</p>')
					$("#sbtn-4").html('<p><span>'+res.viewRateStr.replace('%','')+'</span>%</p><p>作品展示率</p>')
					
					
					$(".time-btns a").unbind('click').click(function(){
						if($(this).hasClass('on')){
							return;
						}
						$(this).addClass('on').siblings().removeClass('on');
						loadSituation();
					})
				})
	}
	
	function loadUserTable(){
		if(!p.checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId)){
			module.showMessage('作品集ID不能为空')
			return;
		}
		if(utils.isNullVal(artworkId)){
			module.showMessage('作品ID不能为空')
			return;
		}
		
		var keywords = $("#user-box .search input[name='keyword']").val();
		
		layui.use(['table'], function(){
			var table = layui.table;
			tableIns = table.render({
				  elem: userTableElem //指定原始表格元素选择器（推荐id选择器）
				  ,skin:'nob'
				  ,height:390
				  ,cols: userTableCols //设置表头
				  ,page:true
				  ,url:config.ctx + "/analysis/loadArtworkMemberAnalysisPage.do"
				  ,method:'post'
				  ,where:{
					  memberId:p.member.memberId,
					  encryptKey:p.member.encryptKey,
					  worksId:worksId,
					  keywords:keywords,
					  artworkId:artworkId
				  }
				  ,request:{
					  pageName:'currentPageNo'
					  ,limitName:'pageSize'
				  }
				  ,limit:pageSize
				  ,limits:null
				  ,response:{
					  statusName: 'status' //数据状态的字段名称，默认：code
						  ,statusCode: 0 //成功的状态码，默认：0
						  ,msgName: 'msg' //状态信息的字段名称，默认：msg
						  ,countName: 'totalCount' //数据总数的字段名称，默认：count
						  ,dataName: 'result' //数据列表的字段名称，默认：data
					  ,conbineData:p.getTableUserDataList
				  }
				  ,done:function(res, curr, count){
					  if($('#user-box .layui-none').length > 0){
						$('#user-box .layui-none').html('<img src="img/icon_empty_2.png" /><p>还没有用户查看此作品</p>')
					  }
				  }
			})
		})
	}
	
	return p;
})()

var renderUser = (function(){
	var p = new renderAnalysis();
	
	var userId = null;
	var worksId = null;
	
	var currentPage = 0;
	var pageSize = 30;
	var canLoadNextPage = true;
	
	var artworkTableElem = '#artwork-data';
	var artworkTableCols = [[
		 					  	{field: 'artworkImg', title:'作品图', width: 100},
							  	{field: 'artworkName', title:'作品名称', width: 160},
							  	{field: 'pageView', title:'浏览次数', width: 160},
							  	{field: 'averagDurationStr', title:'平均浏览时长', width: 160},
							  	{field: 'likeRateStr', title:'喜好特征强弱', width: 210},
							  ]];
	
	p.init = function(docId,uId){
		if(utils.isNullVal(docId)){
			module.showMessage('文档id不能为空')
			return;
		}
		if(utils.isNullVal(uId)){
			module.showMessage('用户id不能为空')
			return;
		}
		
		worksId = docId;
		currentPage = 0;
		loadUserAnalysis(function(res){
			for(var i = 0; i < res.result.length;i++){
				var user = res.result[i];
				if(i == 0){
					userId = user.memberId;
				}
				if(user.memberId == uId){
					userId = uId;
				}
				
			}
			
			$(".middle-list .work-info").text($("#works-list li.on p").text())
			
			render();
			
			$('#user li').unbind('click').click(function(){
				if($(this).hasClass('on')){
					return;
				}
				userId = $(this).data('userid');
				$(this).addClass('on').siblings().removeClass('on');
				
				var avatar = $(this).data('avatar');
				var nickname = $(this).data('nickname');
				var endDate = $(this).data('enddate');
				var date = new Date();
				date.setTime(endDate);
				
				
				$("#user-info-box").html('<div class="avatar" style="background-image:url('+avatar+')"></div><p>'+nickname+'</p><p>最后访问：'+date.Format('yyyy-MM-dd hh:mm:ss')+'</p>')
				
				render();					
			})
			
			$('#user li[data-userid='+userId+']').click();
			
			$(".data-content a.back").css('display','inline-block').unbind('click').click(function(){
				renderWorks.init();
			})
			
			//滚动到底部刷新
			$(".middle-list .box").scroll(function(){
				if($(".middle-list .box").scrollTop() + $('.middle-list .box').height() + 60 > $('#user').height()){
					loadNextPage()
				}
			})
			
		})
	}
	
	function render(){
		$("#artwork").hide();
		$("#user-info-box").show();
		$(".layui-tab .layui-tab-title li:nth-child(3)").hide().siblings().show();
		$(".layui-tab .layui-tab-title li:first-child").click();
		$(".middle-list,.middle-list #user").show();
		$(".works-list").hide();
		resetSituation()
		loadSituation();
		loadArtworkTable();
	}

	function resetSituation(){
		$(".time-btns a,.siton-btns a").removeClass('on').unbind('click');
		$(".time-btns a:first-child").addClass('on');
	}
	function loadUserAnalysis(cbOk){
		if(!canLoadNextPage){
			return;
		}else{
			canLoadNextPage = false;
		}
		if(!p.checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId)){
			module.showMessage('作品集ID不能为空')
			return;
		}
		currentPage++;
		p.callInterface(
				config.ctx + "/analysis/loadMemberWorksAnalysisPage.do", 
				{
					memberId:p.member.memberId,
					encryptKey:p.member.encryptKey,
					worksId:worksId,
					currentPageNo:currentPage,
					pageSize:pageSize,
					keyword:null
				}, 
				function(res){
					console.log(res)
					if(res.currentPage == 1){
						$("#user").empty();
						$(".middle-list .box .title").text('共'+res.totalCount+'位用户');
					}
					$(".middle-list #user .load-more").remove()
					if(res.result && res.result.length > 0){
						$.each(res.result,function(index,obj){
							$(".middle-list #user").append('<li data-userid="'+obj.memberId+'" data-avatar="'+obj.memberFace+'" data-nickname="'+obj.memberNickname+'" data-enddate="'+obj.endDate+'">'+
									'<div class="avatar" style="background-image:url('+obj.memberFace+')"></div>'+
									'<div class="content">'+
										'<p class="name">'+obj.memberNickname+'</p>'+
										//'<p class="author">'+obj.memberNickname+'</p>'+
									'</div>'+
									'<div class="layui-progress">'+
										'<div class="layui-progress-bar layui-bg-red" lay-percent="'+obj.likeRateStr+'" style="width:'+obj.likeRateStr+';"></div>'+
									'</div>'+
								'</li>');
						})
					}
					if(res.currentPage < res.totalPage){
						canLoadNextPage = true;
						$(".middle-list #user").append('<a class="load-more">加载更多</a>').find('.load-more').click(function(){
							loadNextPage();
						})
					}
					
					if(cbOk){
						cbOk(res);
					}
				})
	}
	
	function loadNextPage(){
		loadUserAnalysis(function(res){
			$('#user li').unbind('click').click(function(){
				if($(this).hasClass('on')){
					return;
				}
				userId = $(this).data('userid');
				$(this).addClass('on').siblings().removeClass('on');
				
				var avatar = $(this).data('avatar');
				var nickname = $(this).data('nickname');
				var endDate = $(this).data('enddate');
				var date = new Date();
				date.setTime(endDate);
				
				
				$("#user-info-box").html('<div class="avatar" style="background-image:url('+avatar+')"></div><p>'+nickname+'</p><p>最后访问：'+date.Format('yyyy-MM-dd hh:mm:ss')+'</p>')
				
				render();					
			})
		})
	}
	
	function loadSituation(){
		console.log('Situation')
		var type = $(".time-btns a.on").data('type');
		
		if(!p.checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId)){
			module.showMessage('作品集ID不能为空')
			return;
		}
		if(utils.isNullVal(userId)){
			module.showMessage('用户ID不能为空')
			return;
		}
		p.callInterface(
				config.ctx + "/analysis/loadMemberOverview.do", 
				{
					memberId:p.member.memberId,
					encryptKey:p.member.encryptKey,
					worksId:worksId,
					userId:userId,
					type:type
				}, 
				function(res){
					console.log(res)
					$("#sbtn-1").html('<p><span>'+res.totalViewCount+'</span>次</p><p>访问次数</p>');
					$("#sbtn-2").html('<p><span>'+res.averagDuration+'</span>'+res.averagDurationStr.replace(res.averagDuration,'')+'</p><p>平均浏览时长</p>')
					$("#sbtn-3").html('<p><span>'+res.totalViewArtworkCount+'</span>幅</p><p>浏览作品数量</p>')
					$("#sbtn-4").html('<p><span>'+res.viewRateStr.replace('%','')+'</span>%</p><p>作品展示率</p>')
					
					$(".time-btns a").unbind('click').click(function(){
						if($(this).hasClass('on')){
							return;
						}
						$(this).addClass('on').siblings().removeClass('on');
						loadSituation();
					})
				})
	}
	
	function loadArtworkTable(){
		if(!p.checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId)){
			module.showMessage('作品集ID不能为空')
			return;
		}
		if(utils.isNullVal(userId)){
			module.showMessage('用户ID不能为空')
			return;
		}
		p.callInterface(
				config.ctx + "/analysis/loadMemberArtworkAnalysis.do", 
				{
					memberId:p.member.memberId,
					encryptKey:p.member.encryptKey,
					worksId:worksId,
					userId:userId
				}, 
				function(res){
					console.log(res)
					p.renderTable({
						tableElem:artworkTableElem,
						cols:artworkTableCols,
						datas:p.getTableArtworkDataList(res),
						done:function(){
							if($('#artwork-box .layui-none').length > 0){
								$('#artwork-box .layui-none').html('<img src="img/icon_empty_2.png" /><p>此用户未查看任何作品</p>')
							}
						}
					})
					
					$("#artwork-box .content-box-title p").text('共'+res.result.length+'件作品');
				})
	}
	
	return p;
})()

renderWorks.init();
