package com.github.caoyeung.dao;

import com.github.caoyeung.model.User;

public interface UserDao {
	public int  updateUser(int id,String name);
	public User selectUser(int id);
}
	
