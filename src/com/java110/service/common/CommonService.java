package com.java110.service.common;

import java.util.Map;
/**
 * 公共服务接口
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
public interface CommonService {

	/**
	 * 获取自动生成主见
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public Map getPrimaryKey(Map info);
}
