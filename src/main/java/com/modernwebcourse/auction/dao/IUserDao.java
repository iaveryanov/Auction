package com.modernwebcourse.auction.dao;

import com.modernwebcourse.auction.domain.User;

import java.util.Optional;

public interface IUserDao {

    void insert(User user);

    Optional<User> selectByUserId(int id);
}
