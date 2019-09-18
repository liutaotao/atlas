
var wxShareData = {};

function isWeiXin() { // 是否微信打开
	var ua = window.navigator.userAgent.toLowerCase();
	if (ua.match(/MicroMessenger/i) == 'micromessenger')
		return true;
	else
		return false;
};


function initWx() {
	if (isWeiXin()) {
		var url = ctx + "/api/shop/weixin/initWxconfig.do";
		var param = {
			url : window.location.href
		};
		callInterface(url, param, function(res) {
			var result = res.data;
			wx.config({
				debug : true,
				appId : result.appId,
				timestamp : result.timestamp,
				nonceStr : result.nonceStr,
				signature : result.signature,
				jsApiList : [ 'checkJsApi', 'onMenuShareTimeline',
						'onMenuShareAppMessage', 'onMenuShareQQ',
						'onMenuShareWeibo', 'wx.onMenuShareQZone',
						'chooseImage', 'previewImage', 'hideOptionMenu',
						'showOptionMenu','downloadImage' ]
			});
			
			wx.ready(function(){
				wx.onMenuShareTimeline({
					title : wxShareData.title, // 分享标题
					desc : wxShareData.desc, // 分享描述
					link : wxShareData.currentUrl, // 分享链接
					imgUrl : wxShareData.imgUrl, // 分享图标
					type : 'link', // 分享类型,music、video或link，不填默认为link
					dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
					success : function() {
						if(wxShareData.successFunc){
							wxShareData.successFunc();
						}else{
							//Module.prototype.showMessage('分享成功');
						}
					},
					cancel : function() {
						// 用户取消分享后执行的回调函数
						//showMessage('分享失败');
					}
					
				});
				
				wx.onMenuShareAppMessage({
				    title: wxShareData.title, // 分享标题
				    desc: wxShareData.desc, // 分享描述
				    link: wxShareData.currentUrl, // 分享链接
				    imgUrl: wxShareData.imgUrl, // 分享图标
				    type: 'link', // 分享类型,music、video或link，不填默认为link
				    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
				    success: function () { 
				        // 用户确认分享后执行的回调函数
				    	if(wxShareData.successFunc){
							wxShareData.successFunc();
						}else{
							//Module.prototype.showMessage('分享成功');
						}
				    },
				    cancel: function () { 
				        // 用户取消分享后执行的回调函数
				    }
				});
			});
		}, 'post', function(result) {
			showMessage(result.msg);
		});
	}

}

function initWeixinShareConfig(currentUrl,title,desc,imgUrl,successFunc,id){
	wxShareData.currentUrl = currentUrl;
	wxShareData.title = title;
	wxShareData.desc = desc;
	wxShareData.imgUrl = imgUrl;
	wxShareData.successFunc = successFunc;
	wxShareData.id = id;
	initWx();
}