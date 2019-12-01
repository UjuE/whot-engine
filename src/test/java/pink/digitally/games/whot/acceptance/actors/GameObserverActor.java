package pink.digitally.games.whot.acceptance.actors;

import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.playrule.SpecialCardPlayedEvent;

import java.util.Collection;

public class GameObserverActor implements GameStateObserver {

    private GameState gameState = GameState.NOT_STARTED;
    private Player winner;

    private void updateState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void onGameStarted(Collection<Player> players, Board board) {
        updateState(GameState.STARTED);
    }

    @Override
    public void onGameEnded(Player winner, Collection<Player> players) {
        this.winner = winner;
        updateState(GameState.ENDED);
    }

    @Override
    public void onPlayerTurn(Player player, Board board) {

    }

    @Override
    public void onPlayerChooseShape(Player player) {
    }

    @Override
    public GameState getCurrentGameState() {
        return gameState;
    }

    @Override
    public void onInvalidPlay(Player player, Board board, ErrorMessage errorMessage) {
    }

    @Override
    public void onSpecialCardPlayed(Player player, SpecialCardPlayedEvent specialCardPlayedEvent) {
    }

    public Player getWinner() {
        return winner;
    }
}
