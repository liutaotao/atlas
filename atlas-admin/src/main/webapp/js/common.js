var config = {
		ctx:'/atalas-admin',
		member:'com.atlas.member'
};

(function($) {

    $.getUrlParam = function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var params = encodeURI(window.location.search);
        var r = params.substr(1).match(reg);
        if (r != null)
            return unescape(r[2]);
        return null;
    }
	
})(jQuery)


//重新alert,相当于使alert失效
window.alert = function(str){}


function checkIsSupportStorage(type){
	 var testKey = 'test',storage;
	 if(type == "local"){
	      storage = window.sessionStorage;
	 }else{
	      storage = window.sessionStorage;
	 }
	 if(storage){
		   try {
		       storage.setItem(testKey, 'testValue');
		       storage.removeItem(testKey);
		       return true;
		   } catch (error) {
			   if(type != "local")
		   		showMessage("您处于无痕浏览模式，不能为您保存数据!");
		       return false;
		   }
	 }
}
//本地存储
function setLocalData(skey, svalue) {
	if (checkIsSupportStorage("local")) {
		window.localStorage.setItem(skey, svalue);
	} else {
		var exdate = new Date();
		exdate.setYear(exdate.getYear() + 6);
//		document.cookie = skey + "=" + escape(svalue) + ";expires="
//				+ exdate.toGMTString();
		setCookie(skey, svalue, exdate);
	}
	;
};
function setSessionData(skey, svalue) {
	if (window.sessionStorage) {
		window.sessionStorage.setItem(skey, svalue);
	}
};

//本地读取
function getLocalData(skey) {
	var result_val = "";
	if (checkIsSupportStorage("local")) {
		result_val = window.localStorage.getItem(skey);
	} 
	if(!result_val){
		var re = new RegExp(skey + "=([^;$]*)", "i");
		result_val = getCookie(skey);
	}
	return result_val;
};
function getSessionData(skey) {
	var result_val = "";
	if (window.sessionStorage) {
		result_val = window.sessionStorage.getItem(skey);
	}
	;
	return result_val;
};
//本地清除
function clearLocalData(skey) {
	if (window.localStorage) {
		window.localStorage.setItem(skey, "");
		window.localStorage.removeItem(skey);
	} else {
		document.cookie = skey + "=" + escape("") + ";expires=1";
	}
	;
};
function clearSessionData(skey) {
	if (window.sessionStorage) {
		window.sessionStorage.setItem(skey, "");
		window.sessionStorage.removeItem(skey);
	} else {
		document.cookie = skey + "=" + escape("") + ";expires=1";
	}
	;
};

function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
};

function setCookie(c_name, value, expiredays) {
	var exdate = new Date()
	exdate.setDate(exdate.getDate() + expiredays)
	document.cookie = c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())+";path=/"
};
function clearCookie(skey) {
	document.cookie = skey + "=" + escape("") + ";expires=1;path=/";
};


function isWeiXin() { // 是否微信打开
	var ua = window.navigator.userAgent.toLowerCase();
	if (ua.match(/MicroMessenger/i) == 'micromessenger')
		return true;
	else
		return false;
};

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
//例子：
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt)
{ //author: meizz
 var o = {
     "M+" : this.getMonth()+1,                 //月份
     "d+" : this.getDate(),                    //日
     "h+" : this.getHours(),                   //小时
     "m+" : this.getMinutes(),                 //分
     "s+" : this.getSeconds(),                 //秒
     "q+" : Math.floor((this.getMonth()+3)/3), //季度
     "S"  : this.getMilliseconds()             //毫秒
 };
 if(/(y+)/.test(fmt))
     fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
 for(var k in o)
     if(new RegExp("("+ k +")").test(fmt))
         fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
 return fmt;
}

function gotoPage(url,type){
	if(type == 'r'){
		window.location.replace(url);
	}else{
		window.location.href=url;
	}
}

function getMember(){
	var memberStr = getLocalData(config.member);
	if(memberStr){
		var member = JSON.parse(memberStr);
		if(member.memberId && member.encryptKey){
			return member;
		}
	}
	return null;
}
