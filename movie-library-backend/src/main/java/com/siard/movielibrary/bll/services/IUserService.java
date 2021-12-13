package com.siard.movielibrary.bll.services;

import com.siard.movielibrary.bll.dtos.UserAuthDetails;
import com.siard.movielibrary.bll.dtos.UserCreationDto;
import com.siard.movielibrary.bll.exceptions.AlreadyExistsException;
import com.siard.movielibrary.bll.exceptions.NotFoundException;

import java.util.Collection;

public interface IUserService {
    UserAuthDetails createUser(UserCreationDto userCreationDto) throws AlreadyExistsException;
    Collection<String> getUserRoles(String username) throws NotFoundException;
    void updateUserRoles(String username, Collection<String> roles) throws NotFoundException;
}