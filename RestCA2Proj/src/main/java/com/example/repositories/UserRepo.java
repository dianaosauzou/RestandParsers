package com.example.repositories;

import com.example.entities.User;

import java.util.List;

public interface UserRepo {

    List<User> findAll();
    User findById(Long id);
    Boolean login(String user, int joinYear);

    //User login is successful and only allowing a logged in user to see details, so now i need to make it that a user sees only their data, if user. yada yada = user details then parse only this users playlist,
    //if user id = 1 then file = 1 etc, but how do i get the file to the user, once a user logs in, i need to extract
    //username and year

    //login method

}
