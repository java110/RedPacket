$(document).ready(function(){

	//获取errorMsg 的值
	var errorMsg = $('#errorMsg').val();
	if(errorMsg == 200){
		$('#red_main_type').hide();
		$('#red_send_success').show();
	}else if(errorMsg == 199){
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">充值失败，请稍后重试</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
	}else{
		//什么都不做
	}

});
//form 提交前数据校验
function check(){
	//显示加载层
	$('#asynLayerId').show();
	var numJson = /^([1-9]{1})([0-9]*)$/;
	var money = $('#money').val();
	//校验金额是否为数字
	if(!numJson.test(money)){
		//关闭加载框
		$('#asynLayerId').hide();
		//展示提示分享框
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">充值金额必须是整数</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
		return false;
	}
	return true;
}