package com.java110.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.java110.dal.MyBatisUtils;
import com.java110.dao.intf.CommonIntf;


@SuppressWarnings("rawtypes")
public class CommonDao implements CommonIntf{
	
	public int add(String sql, Map param) {
		SqlSession session = MyBatisUtils.openSession();
		// 添加操作，用insert方法，第一个参数必须是mapping中唯一的id的值。
		int k = session.insert(sql, param);
		// 涉及insert、update、delete的DML，要手动的commit呢，注意close方法是不会监测有木有commit，幻想close方法去commit会让你死的很惨滴。
		session.commit();
		session.close();
		return k;
	}
	
	public Map findOne(String sql, Map param) {
		SqlSession session = MyBatisUtils.openSession();
		Map map = (Map) session.selectOne(sql, param);
		session.commit();
		session.close();
		return map;
	}

	public List<Map> findList(String sql) {
		SqlSession session = MyBatisUtils.openSession();
		List<Map> list = session.selectList(sql);
		session.commit();
		session.close();
		return list;
	}
	
	public List<Map> findList(String sql, Map param) {
		SqlSession session = MyBatisUtils.openSession();
		List<Map> list = session.selectList(sql, param);
		session.commit();
		session.close();
		return list;
	}
	
	public List<Map> findList(String sql, List<Map> param) {
		SqlSession session = MyBatisUtils.openSession();
		List<Map> list = session.selectList(sql, param);
		session.commit();
		session.close();
		return list;
	}
	
	public Map findListSplitPage(String sql, Map param) {
		int pageRank = 2; // 一页几条
		int pageCount = 1; // 共几页
		int currentPage = 1; // 本次获取第几页
		int resultCount = 0; // 共多少条

		// 获取一页展示几条
		String pageRankFromFront = String.valueOf(param.get("pageRank"));
		System.out.println("----------"+pageRankFromFront);
		if(!StringUtils.isBlank(pageRankFromFront) && !"null".equals(pageRankFromFront)){
			pageRank = Integer.valueOf(pageRankFromFront);
			if(pageRank > 100){ // 最大不超过100条
				pageRank = 100;
			}
		}

		// 获取页面当前是第几页
		String currentPageFromFront = String.valueOf(param.get("currentPage"));
		if(!StringUtils.isBlank(currentPageFromFront) && !"null".equals(currentPageFromFront)){
			currentPage = Integer.valueOf(currentPageFromFront);
		}

		SqlSession session = MyBatisUtils.openSession();
		
		// 查询总共记录数
		Map<String, String> countMap = session.selectOne(sql + "_count", param);
		resultCount = Integer.valueOf(String.valueOf(countMap.get("resultcount")));
		
		if(resultCount == 0){// 如果没有数据
			Map pageInfo = new HashMap();
			pageInfo.put("pageRank", 0);
			pageInfo.put("pageCount", 0);
			pageInfo.put("currentPage", 0);
			pageInfo.put("resultCount", 0);
			pageInfo.put("resultlist", null);
			return pageInfo;
		}
		
		pageCount = resultCount % pageRank == 0 ? resultCount / pageRank : (resultCount / pageRank) + 1;
		
		if(currentPage == pageCount + 1){ // 如果页面时最后一页，则把页面往前退一页，继续查询最后一页
			currentPage--;
		}

		param.put("start_index", (currentPage - 1) * pageRank);
		param.put("page_rank", pageRank);
		System.out.println(param);
		List<Map> list = session.selectList(sql, param);
		
		Map pageInfo = new HashMap();
		pageInfo.put("pageRank", pageRank);
		pageInfo.put("pageCount", pageCount);
		pageInfo.put("currentPage", currentPage);
		pageInfo.put("resultCount", resultCount);
		pageInfo.put("resultlist", list);
		
		session.commit();
		session.close();
		return pageInfo;
	}
	
	/**
	 * 返回数据格式是一个map，按照key取获取一条记录对应的map 格式： Map<String, Map<String, String>>
	 * @param sql
	 * @param param
	 * @param key  一个列名，作为map的key
	 * @return
	 */
	public Map findMap(String sql, Map param, String key) {
		SqlSession session = MyBatisUtils.openSession();
		Map<String, Map<String, String>> map = session.selectMap(sql, param,key);
		session.commit();
		session.close();
		return map;
	}

	public int update(String sql, Map param) {
		SqlSession session = MyBatisUtils.openSession();
		int k = session.update(sql, param);
		session.commit();
		session.close();
		return k;
	}

	public int delete(String sql, Map param) {
		SqlSession session = MyBatisUtils.openSession();
		int k = session.delete(sql, param);
		session.commit();
		session.close();
		return k;
	}
	
	public static void main(String[] args) {
		SqlSession session = MyBatisUtils.openSession();
		System.out.println(session.getConfiguration().getMappedStatement("insert_goods").getBoundSql("insert_goods").getSql());
	}
}
