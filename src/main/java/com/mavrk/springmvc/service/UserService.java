package com.mavrk.springmvc.service;

import com.mavrk.springmvc.model.User;

import java.util.List;

/**
 * Created by Sanatt on 12-05-2017.
 */
public interface UserService {

	User findById(long id);

	User findByName(String name);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserById(long id);

	List<User> findAllUsers();

	void deleteAllUsers();

	public boolean isUserExist(User user);
}
