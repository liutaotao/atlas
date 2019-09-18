var uploader = (function(){
	
	var p = new Object();
	
	p.uploadImage = function(obj,folder,cbOk){
		if(!obj.value){
			console.log("未选择文件");
			return;
		}
		var photoExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
		if(!(photoExt == '.jpg' || photoExt == '.jpeg' || photoExt == '.png')){
			console.log("请选择jpg 或者 png 格式图片上传");
			return;
		}
		
		var fileSize = obj.files[0].size;
		fileSize=Math.round(fileSize/1024/1024*100)/100; //单位为M
		if(fileSize>=10){
			console.log("图片最大尺寸为10M，请重新上传!");
	        return false;
	    }
		
		var formData = new FormData();
		formData.append('file',obj.files[0]);
		formData.append('subFolder',folder);
		$.ajax({
			url:config.ctx + '/upload-image/upload-image.do',
			type:'POST',
			dataType:"json",
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(res) {
			console.log(res)
			if(cbOk){
				cbOk(res.img);
			}
		}).fail(function(res) {
			console.log(res)
		});
	}
	
	return p;
})()