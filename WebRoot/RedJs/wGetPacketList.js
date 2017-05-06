function prepareRed(){
	//显示加载层
	$('#asynLayerId').show();
	$('#red_main_type').hide();
	$("#red_send_success").show();
	//隐藏加载层
	$('#asynLayerId').hide();
	//展示提示分享框
	$('#tinybox_1').show();
	
	$('#alterInfo').html("<span class=\"red_send_success_txt\">红包已经准备好</br>请点击右上角<img src=\"img/wShare.png\"/>分享给朋友</span> ");
	
	$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
}
