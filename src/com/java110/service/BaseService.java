package com.java110.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.java110.controller.BaseController;
import com.java110.dao.impl.CommonDao;
import com.java110.dao.intf.CommonIntf;
@SuppressWarnings("unchecked")
public class BaseService {
	
	public static CommonIntf dao = new CommonDao();
	protected Logger logger = Logger.getLogger(BaseController.class);
	
	public BaseService() {
		logger = Logger.getLogger(this.getClass());
	}
	
	public String getDriPath(String pathName) {
		// TODO Auto-generated method stub
		Map param = new HashMap();
		param.put("path_name", pathName);
		Map result = dao.findOne("path_name", param);
		return (String)result.get("path");
	}
}
