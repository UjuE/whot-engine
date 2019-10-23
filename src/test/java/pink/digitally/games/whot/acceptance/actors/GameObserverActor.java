package pink.digitally.games.whot.acceptance.actors;

import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.GameStateObserver;

public class GameObserverActor implements GameStateObserver {

    private GameState gameState = GameState.NOT_STARTED;

    @Override
    public void updateState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public GameState getCurrentGameState() {
        return gameState;
    }
}
