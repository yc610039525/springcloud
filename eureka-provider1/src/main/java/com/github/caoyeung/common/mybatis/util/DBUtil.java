package com.github.caoyeung.common.mybatis.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DBUtil {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostConstruct
	public void init(){
		logger.info("数据库工具初始化");
	}
	@PreDestroy
	public void destory(){
		logger.info("数据库工具销毁");
	}
	private static SqlSessionTemplate sqlSessionTemplate;
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		DBUtil.sqlSessionTemplate = sqlSessionTemplate;
	}
	public static void update(String statement, Map<String,Object> parameter){
		sqlSessionTemplate.update(statement, parameter);
	}
	public static List<Map<String,Object>> querySql(String statement){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("statement", statement);
		List<Map<String,Object>> list = sqlSessionTemplate.selectList("GenericDaoMapper.querySql",param);
		return list;
	}
	public static int updateSql(String statement){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("statement", statement);
		int num = sqlSessionTemplate.update("GenericDaoMapper.updateSql",param);
		return num;
	}
}
