package com.github.caoyeung.service;

import com.github.caoyeung.model.User;

public interface UserService {
	User updateDefaultUser(String name);
	User getDefaultUser();
}
