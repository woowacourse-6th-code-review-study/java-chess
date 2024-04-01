package service;

import database.dao.UserDao;
import dto.UserDto;

import java.util.Optional;

public class UserService {
    private final UserDao userDao;

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDto findByUsername(final String username) {
        Optional<UserDto> userDto = userDao.find(username);
        if (userDto.isEmpty()) {
            userDao.add(username);
            userDto = userDao.find(username);
        }
        return userDto.orElseThrow(IllegalStateException::new);
    }
}
