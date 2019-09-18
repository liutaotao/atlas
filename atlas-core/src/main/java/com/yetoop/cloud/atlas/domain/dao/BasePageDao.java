package com.yetoop.cloud.atlas.domain.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.yetoop.cloud.atlas.common.StringUtil;
import com.zhenyi.common.pager.IQueryer;
import com.zhenyi.common.pager.MybatisQueryer;
import com.zhenyi.common.pager.PagedList;

public class BasePageDao {
	@Autowired
	private SqlSession sqlSession;

	public <E> PagedList<E> executeQuery(String namespace, Map<String, String> map) {

		IQueryer<E> queryer = new MybatisQueryer<E>(sqlSession, namespace, map);
		queryer.query();
		PagedList<E> page = queryer.getPageList();
		return page;
	}
	
	protected Map<String, String> generateQueryParamMap(String currentPageNo,String pageSize){
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtil.isNullString(currentPageNo)){
			currentPageNo = "1";
		}
		if(StringUtil.isNullString(pageSize)){
			pageSize = "10";
		}
		map.put("currentPage",currentPageNo);
		map.put("pageSize", pageSize);
		return map;
	}

}
