function getRed(){
	//显示加载层
	$('#asynLayerId').show();
	
	//异步请求服务器
	$.ajax({
	     type: 'POST',
	     url: $('#domain').val()+"/WGetPacketController.getRedPacket",
	    data: {
			"sendRedPacketId" : $('#sendRedPacketId').val()
		} ,
		dataType: "json",
	    success: function(data){
			if(data.resultCode == '200'){
				//隐藏加载层
				$('#asynLayerId').hide();
				$('#red_main_1').hide();
				$('#red_main_2').show();
				$('#shareTitle').val(data.shareTitle);
				$('#shareIco').val(data.shareIco);
				$('#shareDesc').val(data.shareDesc);
				$('#shareUrl').val(data.shareUrl);
				$('#red_money').html(data.money);
				$('#shareFlag').val('2');
				shareFriendsLine();
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

/**
 * 分享成功够调用
 * @return
 */
function showResult(obj){
	if(obj == 0){
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">发送红包成功</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
	}else{
		$('#tinybox_1').show();
		$('#alterInfo').html("<span class=\"red_send_success_txt\">发送红包失败</span> ");
		$('#alterActionMaskLayer').html("<a href=\"javascript:void(0)\" onclick=\"closeMaskLayer()\">确定</a>");
	}
}
