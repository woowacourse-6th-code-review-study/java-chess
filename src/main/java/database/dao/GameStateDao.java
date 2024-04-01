package database.dao;

import dto.StateDto;

import java.util.Optional;

public interface GameStateDao {
    void add(StateDto stateDto);

    Optional<StateDto> findByGameId(int gameId);

    void deleteByGameId(int gameId);
}
