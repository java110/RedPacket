package com.java110.service.common.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.java110.service.BaseService;
import com.java110.service.common.CommonService;

/**
 * 公共服务类
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
@Service("CommonServiceImpl")
public class CommonServiceImpl extends BaseService implements CommonService{
	/**
	 * 获取自动生成主键
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public Map getPrimaryKey(Map info){
		return dao.findOne("CommonServiceImpl.getPrimaryKey", info);
	}
}
