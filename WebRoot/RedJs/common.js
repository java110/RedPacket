function backIndex(){
	location.href="indexPacket.html";
}

function closeMaskLayer(){
	$('#tinybox_1').hide();
}

/**
 * 5秒钟倒计时方法
 * @param o
 * @return
 */
var time = 5;
function time() {
    if (wait == 0) {
    	$('#red_main_3').show();
        wait = 5;
    } else { 
        //写 时间倒退的特效
        wait--;
        setTimeout(function() {
            time();
        },
        1000)
    }
}
