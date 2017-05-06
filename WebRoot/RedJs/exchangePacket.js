function exchangeRedPacket(){
	//显示加载层
	$('#asynLayerId').show();
	var alipayAccount = $('#alipayAccount').val();
	var money = $('#money').val();
	var amount = $('#amount').val();
	//支付宝账号和金钱是否为数字
	var numJson = /^([1-9]{1})([0-9]*)$/;
	if(!numJson.test(alipayAccount)){
		//关闭加载框
		$('#asynLayerId').hide();
		//展示提示分享框
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">请输入合法的支付宝账号</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
		return;
	}
	if(!numJson.test(money)){
		//关闭加载框
		$('#asynLayerId').hide();
		//展示提示分享框
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">请输入整数金额</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
		return;
	}
	if(eval(money) > eval(amount)){
		//关闭加载框
		$('#asynLayerId').hide();
		//展示提示分享框
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">输入金额太大，账户金额不足</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
		return;
	}
	//异步请求服务器，提现
	$.ajax({
	     type: 'POST',
	     url: $('#domain').val()+"/ExchangePacketController.exchangeMoney",
	    data: {
			"alipayAccount" : alipayAccount,
			"money": money
		} ,
		dataType: "json",
	    success: function(data){
			if(data.resultCode == '200'){
				$('#red_main_type').hide();
				$("#red_send_success").show();
				//隐藏加载层
				$('#asynLayerId').hide();
				//展示提示分享框
				$('#tinybox_1').show();
				$('#alterInfo').html("<span class=\"red_send_success_txt\">"+data.resultInfo+"</span> ");
				$('#red_send_success_txt').html(data.resultInfo);
				$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
			}else{
				//关闭加载框
				$('#asynLayerId').hide();
				//展示提示分享框
				$('#tinybox_1').show();
				$('#alterInfo').html("<span class=\"red_send_success_txt\">"+data.resultInfo+"</span> ");
				$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
			}
		},
		error : function(e){
			//关闭加载框
			$('#asynLayerId').hide();
			//展示提示分享框
			$('#tinybox_1').show();
			$('#alterInfo').html("<span class=\"red_send_success_txt\">网络繁忙，请稍后重试</span> ");
			$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
		}
	});
	
}
