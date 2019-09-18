/**
 * Created by ws_ti on 2016/11/24.
 */
var verifybar = (function(){
    var p = new Object();
  
    p.imageObj = ".verifybar .code > img";
    p.inputObj = ".verifybar .code > input";
    p.verifybar = null;
    p.cssUrl = "css/verifybar.css";
    p.inputCode = null;
    
    var submit = null;
    var cancel = null;

    p.init = function(cbOk,cbErr){
        p.initBarAndStyle();
        $(p.imageObj).on("click",function(){
        	p.loadVerifyCode();
        })
        
        submit = cbOk;
        cancel = cbErr;
    }

    p.initBarAndStyle = function(){
        p.verifybar = '<div class="verifybar">'+
            '<div class="code">'+
            '<img src="" />'+
            '<input type="text" placeholder="请输入运算结果" />'+
            '</div>'+
            '<div class="btns">'+
            '<a class="cel" href="javascript:verifybar.cancel()">取消</a>'+
            '<a class="ok" href="javascript:verifybar.submit()">确定</a>'+
            '</div>'+
            '</div>';
        /*+'<div class="verifysbg"></div>'*/
        $("body").append(p.verifybar);

        var head = document.getElementsByTagName('head')[0];
        var link = document.createElement('link');
        link.href =  p.cssUrl;
        link.rel = 'stylesheet';
        link.type = 'text/css';
        head.appendChild(link);
    }

    p.submit = function(){
    	var code = $(p.inputObj).val().trim();
    	if(!code){
    		module.showMessage("请输入验证码")
    		return;
    	}
    	p.inputCode = code;
    	if(submit){
    		submit(code);
    	}
    	$(".verifybar").hide();
    }
    
    p.cancel = function(){
    	p.inputCode = null;
    	$(".verifybar").hide();
    	if(cancel){
    		cancel();
    	}
    }
    
    p.loadVerifyCode = function(){
    	p.inputCode = null;
    	$(p.inputObj).val("");
    	$(p.imageObj).attr("src",config.ctx + '/validcodeFront.do?r='+new Date().getTime());
        $(".verifybar").show().css('z-index',$("#layui-layer1").css('z-index'));
    }
    return p;
})();

