package com.modernwebcourse.auction.emulator.dao;

import com.modernwebcourse.auction.dao.IUserDao;
import com.modernwebcourse.auction.domain.User;

import java.util.Optional;

public class UserDao implements IUserDao {

    public UserDao() {
    }

    @Override
    public void insert(User user) {
        DB.USERS.put(user.id, user);
        System.out.println("inserted " + user);
    }

    @Override
    public Optional<User> selectByUserId(int id) {
        return Optional.ofNullable(DB.USERS.get(id));
    }
}
