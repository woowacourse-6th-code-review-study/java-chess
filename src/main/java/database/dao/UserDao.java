package database.dao;

import dto.UserDto;

import java.util.Optional;

public interface UserDao {
    Optional<UserDto> find(String username);

    void add(String username);
}
