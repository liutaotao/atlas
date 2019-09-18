var utils = {};

utils.loadI18nProperties = function(cbOk){
	jQuery.i18n.properties({// 加载资浏览器语言对应的资源文件
		name:'strings', // 资源文件名称
		path:'/atalas-web/i18n/', // 资源文件路径
		mode:'map', // 用 Map 的方式使用资源文件中的值
		//language:'zh', //默认是浏览器语言
		callback: function() {// 加载成功后设置显示内容
			// 显示“用户名”
			//console.log($.i18n.prop('string_username'));
			if(cbOk){
				cbOk();
			}
		} 
	}); 
}

/**
 * 判断是否为空
 * @param param
 * @returns {Boolean}
 */
utils.isNullVal = function(param){
	if ((param === "" ||
			param === undefined ||
			param === "undefined" || //未登录取到的cookie值为"undefined"
			param === null ||
			param === "null") 
			&& param !== 0 && param !== false) {
		return true;
	} else {
		return false;
	}
}

/**
 * 正则表达式
 * @type {{mobile: RegExp, email: RegExp, password: RegExp, integer: RegExp, price: RegExp}}
 */
utils.regExp = {
    //  手机号
    mobile  : /^0?(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])[0-9]{8}$/,
    //  电子邮箱
    email   : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
    //  密码【6-20位】
    password: /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~\,]{6,20}$/,
    //  正整数【不包含0】
    integer : /^[0-9]+$/,
    //  price
    price   : /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/
}

utils.isMobileNum = function(str){
	if(str.match(utils.regExp.mobile)){
		return true;
	}
	return false;
}
utils.checkPassword = function(str){
	if(str.match(utils.regExp.password)){
		return true;
	}
	return false;
}
/**
 * 格式化时间
 * eg 200.9 -> ￥200.90
 * 
 */

utils.formatMoney = function(s) {
    if (/[^0-9\.]/.test(s)) return "invalid value";
    s = s+'';
    s = s.replace(/^(\d*)$/, "$1.");
    s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
    s = s.replace(".", ",");
    var re = /(\d)(\d{3},)/;
    while (re.test(s))
        s = s.replace(re, "$1,$2");
    s = s.replace(/,(\d\d)$/, ".$1");
    return "￥" + s.replace(/^\./, "0.")
}

/**
 * 全部替换
 * @param value
 * @param oldStr
 * @param newStr
 * @returns
 */
utils.replaceAll = function(value,oldStr,newStr){
	if(value){
		while(value.indexOf(oldStr) >= 0){
			value = value.replace(oldStr,newStr);
		}
	}
	return value;
}
