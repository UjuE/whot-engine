package pink.digitally.games.whot.whotcore;

import pink.digitally.games.whot.state.GameState;

import java.util.Collection;

public interface GameStateObserver {
     void gameStarted();
     void gameEnded(Player winner, Collection<Player> players);
     void currentPlayer(Player player);
     GameState getCurrentGameState();
}
