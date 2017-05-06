package com.java110.dao.intf;

import java.util.List;
import java.util.Map;

public interface CommonIntf {

	public abstract int add(String sql, Map param);

	public abstract Map findOne(String sql, Map param);

	public abstract List<Map> findList(String sql);

	public abstract List<Map> findList(String sql, Map param);

	public abstract List<Map> findList(String sql, List<Map> param);
	
	public abstract Map findListSplitPage(String sql, Map param);

	/**
	 * 返回数据格式是一个map，按照key取获取一条记录对应的map 格式： Map<String, Map<String, String>>
	 * @param sql
	 * @param param
	 * @param key  一个列名，作为map的key
	 * @return
	 */
	public abstract Map findMap(String sql, Map param, String key);

	public abstract int update(String sql, Map param);

	public abstract int delete(String sql, Map param);

}