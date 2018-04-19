package com.github.caoyeung.common.mybatis.dao;

import java.util.List;
import java.util.Map;

/**
 * 公共数据库操作层
 * @param <T> 实体PO类型
 * @param <PK> PO主键类型
 */
public interface GenericDAO {

	Map<String, Object> selectFirstOne(String statement);
	Map<String, Object> selectJustOne(String statement);
	List<Map<String,Object>> querySql(String statement);
	int updateSql(String statement);
}
