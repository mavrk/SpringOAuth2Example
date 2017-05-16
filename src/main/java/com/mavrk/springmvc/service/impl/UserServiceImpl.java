package com.mavrk.springmvc.service.impl;

import com.mavrk.springmvc.model.User;
import com.mavrk.springmvc.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Sanatt on 12-05-2017.
 */

@Service("userService")

public class UserServiceImpl implements UserService{

	private static final AtomicLong counter = new AtomicLong();

	private static List<User> users;

	@Override
	public User findById(long id) {
		for(User user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}

	@Override
	public User findByName(String name) {
		for(User user : users){
			if(user.getName().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}

	@Override
	public void saveUser(User user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	@Override
	public void updateUser(User user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	@Override
	public void deleteUserById(long id) {
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
			User user = iterator.next();
			if (user.getId() == id) {
				iterator.remove();
			}
		}
	}

	@Override
	public List<User> findAllUsers() {
		return users;
	}

	@Override
	public void deleteAllUsers() {
		users.clear();
	}

	@Override
	public boolean isUserExist(User user) {
		return findByName(user.getName())!=null;
	}

	private static List<User> populateDummyUsers(){
		List<User> users = new ArrayList<User>();
		users.add(new User(counter.incrementAndGet(),"Sanatt",19, 70000));
		users.add(new User(counter.incrementAndGet(),"Shreya",20, 50000));
		users.add(new User(counter.incrementAndGet(),"Upanishad",19, 30000));
		users.add(new User(counter.incrementAndGet(),"Neelohit",19, 40000));
		return users;
	}
}
