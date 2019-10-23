package pink.digitally.games.whot.whotcore;

import pink.digitally.games.whot.state.GameState;

public interface GameStateObserver {
     void updateState(GameState gameState);
     GameState getCurrentGameState();
}
