package com.github.caoyeung.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.caoyeung.common.mybatis.dao.GenericDAO;
import com.github.caoyeung.dao.UserDao;
import com.github.caoyeung.model.User;
import com.github.caoyeung.service.UserService;
import com.github.caoyeung.util.DateUtil;
@Service("UserService")
//@RestController
public class UserServiceImpl implements UserService {
	
	private static Logger logger =  LoggerFactory.getLogger(UserServiceImpl.class);
//	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserDao userMapper;
	@Resource(name = "GenericDAO")
	private GenericDAO GenericDAO;
	
//	@RequestMapping(value="/UserServiceImpl/handUser")
//	@Transactional(value = "txManager")
	@Override
	public User updateDefaultUser(String name) {
//		String querySql = "select * from tb_user where id = 12345678";
//		String querySql = "select count(*) from TB_USER ";
//		Map<String, Object> selectFirstOne = GenericDAO.selectFirstOne(querySql);
		String time = DateUtil.getNowTimeToDefaultString();
		int N= userMapper.updateUser(12345678, name+"@"+time);
		logger.info("更新数据"+N+"条");
		User user = userMapper.selectUser(12345678);
//		List<Map<String, Object>> querySql2 = DBUtil.querySql("select * from tb_user where id = 12345678");
//		String updateSql = "update tb_user set name = '"+name+"' where id = 12345678";
//		int updateNum = DBUtil.updateSql(updateSql);
		String f = "true";
		if(StringUtils.equals("true", f)){
//			throw new RuntimeException("roll back test!");
		}
		return user;
	}

	@Override
	public User getDefaultUser() {
		User user = userMapper.selectUser(12345678);
		String time = DateUtil.getNowTimeToDefaultString();
		int N= userMapper.updateUser(12345678, "Provider2"+"@"+time);
		logger.info("更新数据"+N+"条");
		return user;
	}
}
