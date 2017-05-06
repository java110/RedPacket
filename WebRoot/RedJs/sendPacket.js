function prepareRed(){
	//显示加载层
	$('#asynLayerId').show();
	var copies = $('#copies').val();
	var money = $('#money').val();
	var red_type = $('#red_type').val();
	//红包份数 空值判断
	if(copies == null || copies == ""){
		//关闭加载框
		$('#asynLayerId').hide();
		//展示提示分享框
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">请输入红包份数</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
		return;
	}
	//红包份数是否为数字
	var numJson = /^([1-9]{1})([0-9]*)$/;
	if(!numJson.test(copies)){
		//关闭加载框
		$('#asynLayerId').hide();
		//展示提示分享框
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">请输入红包份数必须为整数</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
		return;
	}
	//红包金额 空值判断
	if(money == null || money == ""){
		//关闭加载框
		$('#asynLayerId').hide();
		//展示提示分享框
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">请输入红包金额</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
		return;
	}
	//校验金额是否为数字
	if(!numJson.test(money)){
		//关闭加载框
		$('#asynLayerId').hide();
		//展示提示分享框
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">请输入红包金额必须为整数</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
		return;
	}
	
	//红包金额限制（个人红包金额是不做限制的，最低金额不能小于0.01元，商户红包金额最低不能低于200元，并且最低金额不能小于0.1元）
	//个人红包最低金额限制
	var c_money = money/copies;
	
	if(red_type == "red_type_01" && c_money < 0.01){
		//关闭加载框
		$('#asynLayerId').hide();
		//展示提示分享框
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">输入红包份数太大，最低金额不能低于0.01元</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
		return;
	}
	//异步请求服务器
	$.ajax({
	     type: 'POST',
	     url: $('#domain').val()+"/SendPacketController.sendPersonRedPacket" ,
	    data: {
			"copies" : copies,
			"money": money,
			"c_money":c_money,
			"red_type":red_type
		} ,
		dataType: "json",
	    success: function(data){
			if(data.resultCode == '200'){
				$('#red_main_type').hide();
				$("#red_send_success").show();
				//隐藏加载层
				$('#asynLayerId').hide();
				//展示提示分享框
				//$('#tinybox_1').show();
				
				$('#shareTitle').val(data.shareTitle);
				$('#shareIco').val(data.shareIco);
				$('#shareDesc').val(data.shareDesc);
				$('#shareUrl').val(data.shareUrl);
//				alipayShare();
				//$('#alterInfo').html("<span class=\"red_send_success_txt\">红包已经准备好，分享给朋友</span> ");
				//$('#alterActionMaskLayer').html("<button id=\"J_exampleTrigger\">分享</button>");
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

//随机红包和 平均红包
function red_type_m(obj){
	//说明是随机红包
	if("red_type_01" == obj){
		$('#moneyCount').html('总金额');
		$('#money').attr('placeholder','请输入总金额');
		$('#red_type').val('red_type_01');
	}else{
	//平均红包
		$('#moneyCount').html('单个金额');
		$('#money').attr('placeholder','请输入单个金额');
		$('#red_type').val('red_type_02');
		
	}
}

