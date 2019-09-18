$(function(){
	var previewCode = null;
	
	var member = {
		memberId:128,
		encryptKey:'egs2DANOXWlFGGpPi2SIzfZFzKYP63N1'
	}
	
	function init(){
		//i18n国际化
		utils.loadI18nProperties(function(){
			console.log($.i18n.prop('string_username'));
		})
		previewCode = $.getUrlParam("previewCode");
		loadWorksDetail();
	}
	
	function loadWorksDetail(){
		$.ajax({
			url:config.ctx + "/user/loadPriviewWorksDetail.do",
			dataType:"json",
			data:{
				memberId:member.memberId,
				encryptKey:member.encryptKey,
				priviewCode:previewCode
			},
			type:"post",
			success:function(res){
				console.log(res)
				if(res.status == 0){//成功
					$(".main .ctip").text(res.galleryName + " 作品导览图");
					$(".main .title").text(res.galleryName + " | " + res.name);
					fillArtwork(res.artworkDtoList)
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
			})
		}
	}
	
	init()
})

