package repository;

import database.dao.GameStateDao;
import dto.StateDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameStateMockDao implements GameStateDao {
    private final Map<Integer, StateDto> repository = new HashMap<>();

    public void add(StateDto stateDto) {
        repository.put(stateDto.gameId(), stateDto);
    }

    public Optional<StateDto> findByGameId(final int gameId) {
        return Optional.ofNullable(repository.get(gameId));
    }

    public void deleteByGameId(final int gameId) {
        repository.remove(gameId);
    }
}
