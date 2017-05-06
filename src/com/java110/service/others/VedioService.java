package com.java110.service.others;

import java.util.Map;
/**
 * 包红包接口类
 * 
 * @author wuxw
 * @date 2016-2-18
 * version 1.0
 */
public interface VedioService {
	
	/**
	 * 根据Id 查询包的红包
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public Map getVedioByVedioId(Map info);
	
	/**
	 * 保存下载视频记录
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public int saveDownloadedVedio(Map info);
	
	/**
	 * 保存下载视频记录
	 * 
	 * add by wuxw 2016-2-18
	 * @param info
	 * @return
	 */
	public Map getDownloadedVedio(Map info);

}
