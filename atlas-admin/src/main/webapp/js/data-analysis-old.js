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
		
/*		var table = layui.table;
		table.render({
			  elem: '#user-data' //指定原始表格元素选择器（推荐id选择器）
			  ,skin:'nob'
			  ,cols: [[
			  	{field: 'avatar', title:'用户头像', width: 80},
			  	{field: 'name', title:'用户名称（备注名）', width: 150},
			  	{field: 'lastTime', title:'最后浏览时间', width: 160},
			  	{field: 'times', title:'浏览次数', width: 80},
			  	{field: 'timelong', title:'平均浏览时长', width: 100},
			  	{field: 'like', title:'喜好特征强弱', width: 200},
			  ]], //设置表头
			  data:[
			  	{avatar:'<div class="avatar" style="background-image:url(https://zhenyiart.oss-cn-beijing.aliyuncs.com/zhenyiart/ai-mall/store/1/goods/201708111626000876.png?x-oss-process=image/resize,w_+800,h_800,m_fill/auto-orient,1/quality,q_90/format,src)"></div>',
			  		name:'王淑婷（备注）',lastTime:'2017-08-18 12:09:08',times:'5次',timelong:'5min',
			  		like:'<div class="layui-progress" style="margin-top:22px;"><div class="layui-progress-bar layui-bg-red" lay-percent="30%" style="width:30%"></div></div>'}
			  	,{avatar:'<div class="avatar" style="background-image:url(https://zhenyiart.oss-cn-beijing.aliyuncs.com/zhenyiart/ai-mall/store/1/goods/201708111626000876.png?x-oss-process=image/resize,w_+800,h_800,m_fill/auto-orient,1/quality,q_90/format,src)"></div>',
			  		name:'王淑婷（备注）',lastTime:'2017-08-18 12:09:08',times:'5次',timelong:'5min',
			  		like:'<div class="layui-progress" style="margin-top:22px;"><div class="layui-progress-bar layui-bg-red" lay-percent="30%" style="width:30%"></div></div>'}
			  	,{avatar:'<div class="avatar" style="background-image:url(https://zhenyiart.oss-cn-beijing.aliyuncs.com/zhenyiart/ai-mall/store/1/goods/201708111626000876.png?x-oss-process=image/resize,w_+800,h_800,m_fill/auto-orient,1/quality,q_90/format,src)"></div>',
			  		name:'王淑婷（备注）',lastTime:'2017-08-18 12:09:08',times:'5次',timelong:'5min',
			  		like:'<div class="layui-progress" style="margin-top:22px;"><div class="layui-progress-bar layui-bg-red" lay-percent="30%" style="width:30%"></div></div>'}
			  ]
		})
		
		table.render({
			  elem: '#artwork-data' //指定原始表格元素选择器（推荐id选择器）
			  ,skin:'nob'
			  ,cols: [[
			  	{field: 'avatar', title:'作品图', width: 100},
			  	{field: 'name', title:'作品名称', width: 180},
			  	{field: 'times', title:'浏览次数', width: 100},
			  	{field: 'timelong', title:'平均浏览时长', width: 140},
			  	{field: 'like', title:'喜好特征强弱', width: 210},
			  ]], //设置表头
			  data:[
			  	{avatar:'<div class="pic" style="background-image:url(https://zhenyiart.oss-cn-beijing.aliyuncs.com/zhenyiart/ai-mall/store/1/goods/201708111626000876.png?x-oss-process=image/resize,w_+800,h_800,m_fill/auto-orient,1/quality,q_90/format,src)"></div>',
			  		name:'王淑婷（备注）',lastTime:'2017-08-18 12:09:08',times:'5次',timelong:'5min',
			  		like:'<div class="layui-progress" style="margin-top:22px;"><div class="layui-progress-bar layui-bg-red" lay-percent="30%" style="width:30%"></div></div>'}
			  	,{avatar:'<div class="pic" style="background-image:url(https://zhenyiart.oss-cn-beijing.aliyuncs.com/zhenyiart/ai-mall/store/1/goods/201708111626000876.png?x-oss-process=image/resize,w_+800,h_800,m_fill/auto-orient,1/quality,q_90/format,src)"></div>',
			  		name:'王淑婷（备注）',lastTime:'2017-08-18 12:09:08',times:'5次',timelong:'5min',
			  		like:'<div class="layui-progress" style="margin-top:22px;"><div class="layui-progress-bar layui-bg-red" lay-percent="30%" style="width:30%"></div></div>'}
			  	,{avatar:'<div class="pic" style="background-image:url(https://zhenyiart.oss-cn-beijing.aliyuncs.com/zhenyiart/ai-mall/store/1/goods/201708111626000876.png?x-oss-process=image/resize,w_+800,h_800,m_fill/auto-orient,1/quality,q_90/format,src)"></div>',
			  		name:'王淑婷（备注）',lastTime:'2017-08-18 12:09:08',times:'5次',timelong:'5min',
			  		like:'<div class="layui-progress" style="margin-top:22px;"><div class="layui-progress-bar layui-bg-red" lay-percent="30%" style="width:30%"></div></div>'}
			  ]
		})*/
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

var analysis = (function(){
	var p = new Object();
	
	var member = null;
	
	var artworkTable = null;
	
	var currentPage = 0;
	var pageSize = 30;
	
	p.init = function(){
		var docId = $.getUrlParam('docId');
		
		
		loadWorksList();
		
		loadArtworkAnalysis(5,function(res){
			renderArtworkTable(res,'user');
			fillMiddleArtworkList(res)
		})
		
		loadMemberWorksAnalysisPage(5,null,function(res){
			renderUserTable(res,'artwork');
		});
	}
	
	/**
	 * 分页获取 作品集下 用户喜好分析
	 */
	function loadMemberWorksAnalysisPage(worksId,keywords,cbOk){
		if(!checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId)){
			return;
		}
		currentPage++;
		$.ajax({
			url:config.ctx + "/analysis/loadMemberWorksAnalysisPage.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				worksId:worksId,
				keywords:keywords,
				currentPageNo:currentPage,
				pageSize:pageSize
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					if(cbOk){
						cbOk(res)
					}
				}else{
					console.log(res.msg)
				}
			},
			error:function(res){
				console.log(res)
			}
		})
	}
	
	function renderUserTable(res,tableType){
		var cols = null,datas = null;
		var tableElem = '#user-data';
		if(tableType == 'work'){
			//作品集下全部作品分析
			cols = [[
					  	{field: 'memberFace', title:'用户头像', width: 100},
					  	{field: 'memberNickName', title:'用户名称', width: 100},
					  	{field: 'endDate', title:'最后访问', width: 180},
					  	{field: 'viewCount', title:'访问次数', width: 100},
					  	{field: 'averagDurationStr', title:'平均浏览时长', width: 100},
					  	{field: 'viewRateStr', title:'作品展示率', width: 100},
					  	{field: 'likeRateStr', title:'喜好特征强弱', width: 200},
					  ]]
		}else if(tableType == 'artwork'){
			//作品集下 针对单个用户的 作品分析
			cols = [[
					  	{field: 'memberFace', title:'用户头像', width: 100},
					  	{field: 'memberNickName', title:'用户名称', width: 100},
					  	{field: 'endDate', title:'最后浏览时间', width: 160},
					  	{field: 'viewCount', title:'访问次数', width: 100},
					  	{field: 'averagDurationStr', title:'平均浏览时长', width: 120},
					  	{field: 'likeRateStr', title:'喜好特征强弱', width: 200},
					  ]]
			
		}
		
		datas = getTableUserDataList(res);
		artworkTable = renderTable({
			tableElem:tableElem,
			cols:cols,
			datas:datas
		});
		

		//生成table数据，artwork
		function getTableUserDataList(res){
			var artDatas = [];
			if(res.result && res.result.length > 0){
				$.each(res.result,function(index,obj){
					var date = new Date();
					date.setTime(obj.endDate);
					artDatas.push({
						memberFace:'<div class="avatar" style="background-image:url('+obj.memberFace+')"></div>',
						memberNickName:obj.memberNickName,
						endDate:date.Format('yyyy-MM-dd hh:mm:ss'),
						viewCount:obj.viewCount,
						averagDurationStr:obj.averagDurationStr,
						viewRateStr:obj.viewRateStr,
						likeRateStr:'<div class="layui-progress" style="margin-top:22px;"><div class="layui-progress-bar layui-bg-red" lay-percent="30%" style="width:'+obj.likeRateStr+'"></div></div>'
					})
				})
			}
			return artDatas;
		}
	}
	
	/**
	 * 获取用户 对于作品集下作品 的喜好分析
	 */
	function loadMemberArtworkAnalysis(worksId,userId){
		if(!checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId) || utils.isNullVal(userId)){
			return;
		}
		$.ajax({
			url:config.ctx + "/analysis/loadMemberArtworkAnalysis.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				worksId:worksId
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					renderArtworkTable(res,'user')
				}else{
					console.log(res.msg)
				}
			},
			error:function(res){
				console.log(res)
			}
		})
	}
	
	/**
	 * 作品喜好分析List
	 * 	   作品集下所有作品
	 */
	function loadArtworkAnalysis(worksId,cbOk){
		if(!checkLogin()){
			return;
		}
		if(utils.isNullVal(worksId)){
			return;
		}
		$.ajax({
			url:config.ctx + "/analysis/loadArtworkAnalysis.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				worksId:worksId
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					if(cbOk){
						cbOk(res);
					}
				}else{
					console.log(res.msg)
				}
			},
			error:function(res){
				console.log(res)
			}
		})
	}
	
	function fillMiddleArtworkList(res){
		if(res.result && res.result.length > 0){
			$.each(res.result,function(index,obj){
				$(".middle-list #artwork").append('<li data-artworkId="'+obj.artworkId+'">'+
						'<div class="avatar" style="background-image:url('+obj.artworkImg+')"></div>'+
						'<div class="layui-progress">'+
							'<div class="layui-progress-bar layui-bg-red" lay-percent="'+obj.likeRateStr+'"></div>'+
						'</div>'+
						'<div class="content">'+
							'<p class="name">'+obj.artworkName+'</p>'+
							'<p class="author">作者：'+obj.artistName+'</p>'+
						'</div>'+
					'</li>');
			})
		}
	}
	
	function renderArtworkTable(res,tableType){
		var cols = null,datas = null;
		var tableElem = '#artwork-data';
		if(tableType == 'work'){
			//作品集下全部作品分析
			cols = [[
					  	{field: 'artworkImg', title:'作品图', width: 100},
					  	{field: 'artworkName', title:'作品名称', width: 90},
					  	{field: 'uniqueVisitor', title:'浏览人数', width: 90},
					  	{field: 'pageView', title:'浏览次数', width: 90},
					  	{field: 'averagDurationStr', title:'平均浏览时长', width: 90},
					  	{field: 'viewRateStr', title:'平均展示率', width: 90},
					  	{field: 'likeRateStr', title:'喜好特征强弱', width: 200},
					  ]]
		}else if(tableType == 'user'){
			//作品集下 针对单个用户的 作品分析
			cols = [[
					  	{field: 'artworkImg', title:'作品图', width: 100},
					  	{field: 'artworkName', title:'作品名称', width: 200},
					  	{field: 'pageView', title:'浏览次数', width: 120},
					  	{field: 'averagDurationStr', title:'平均浏览时长', width: 150},
					  	{field: 'likeRateStr', title:'喜好特征强弱', width: 200},
					  ]]
			
		}
		
		datas = getTableArtworkDataList(res);
		artworkTable = renderTable({
			tableElem:tableElem,
			cols:cols,
			datas:datas
		});
		
		//生成table数据，artwork
		function getTableArtworkDataList(res){
			var artDatas = [];
			if(res.result && res.result.length > 0){
				$.each(res.result,function(index,obj){
					artDatas.push({
						artworkImg:'<div class="pic" style="background-image:url('+obj.artworkImg+')"></div>',
						artworkName:obj.artworkName,
						uniqueVisitor:obj.uniqueVisitor,
						pageView:obj.pageView,
						averagDurationStr:obj.averagDurationStr,
						viewRateStr:obj.viewRateStr,
						likeRateStr:'<div class="layui-progress" style="margin-top:22px;"><div class="layui-progress-bar layui-bg-red" lay-percent="30%" style="width:'+obj.likeRateStr+'"></div></div>'
					})
				})
			}
			return artDatas;
		}
	}
	
	
	/**
	 * 获取所有文档列表
	 */
	function loadWorksList(cbOk){
		$("#works-list").empty();
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
					console.log(res.msg)
				}
			},
			error:function(res){
				console.log(res)
			}
		})
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
	function renderTable(param){
		if(utils.isNullVal(param.tableElem) || utils.isNullVal(param.cols) || utils.isNullVal(param.datas)){
			console.log('数据错误，无法绘制表格')
			return;
		}
		var tableIns = null;
		layui.use(['table'], function(){
			var table = layui.table;
			tableIns = table.render({
				  elem: param.tableElem //指定原始表格元素选择器（推荐id选择器）
				  ,skin:'nob'
				  ,cols: param.cols, //设置表头
				  data:param.datas
			})
		})
		
		return tableIns;
	}
	
	function checkLogin(){
		member = getMember();
		if(!member){
			gotoPage('login.html')
			return false;
		}
		return true;
	}
	
	return p;
})()

analysis.init();