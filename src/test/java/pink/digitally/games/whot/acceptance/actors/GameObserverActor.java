package pink.digitally.games.whot.acceptance.actors;

import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;

import java.util.Collection;

public class GameObserverActor implements GameStateObserver {

    private GameState gameState = GameState.NOT_STARTED;
    private Player winner;

    private void updateState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void gameStarted() {
        updateState(GameState.STARTED);
    }

    @Override
    public void gameEnded(Player winner, Collection<Player> players) {
        this.winner = winner;
        updateState(GameState.ENDED);
    }

    @Override
    public void currentPlayer(Player player) {
        //LOG Player
    }

    @Override
    public GameState getCurrentGameState() {
        return gameState;
    }

    public Player getWinner() {
        return winner;
    }
}
