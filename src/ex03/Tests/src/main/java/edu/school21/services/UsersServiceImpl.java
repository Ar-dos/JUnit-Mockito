package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import javax.persistence.EntityNotFoundException;

abstract public class UsersServiceImpl implements UsersRepository  {
    boolean authenticate(String login, String password) throws AlreadyAuthenticatedException {
        if(findByLogin(login).isAuthentication())
            throw new AlreadyAuthenticatedException();
        User user = findByLogin(login);
        boolean result = false;
        if(user.getPassword().equals(password)) {
            user.setAuthentication(true);
            update(user);
            result = true;
        }
        return result;
    }

}
