package pink.digitally.games.whot.whotcore;

import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.playrule.SpecialCardPlayedEvent;

import java.util.Collection;
import java.util.Objects;

public class RoboGameObserverWrapper implements GameStateObserver {
    private final GameStateObserver gameStateObserver;

    public RoboGameObserverWrapper(GameStateObserver gameStateObserver) {
        this.gameStateObserver = gameStateObserver;
    }

    @Override
    public void onGameStarted(Collection<Player> players, Board board) {
        this.gameStateObserver.onGameStarted(players, board);
    }

    @Override
    public void onGameEnded(Player winner, Collection<Player> players) {
        this.gameStateObserver.onGameEnded(winner, players);
    }

    @Override
    public void onPlayerTurn(Player player, Board board) {
        if (player.isHumanPlayer()) {
            this.gameStateObserver.onPlayerTurn(player, board);
        } else {
            player.play(board);
        }
    }

    @Override
    public GameState getCurrentGameState() {
        return gameStateObserver.getCurrentGameState();
    }

    @Override
    public void onInvalidPlay(Player player, Board board, ErrorMessage errorMessage) {
        this.gameStateObserver.onInvalidPlay(player, board, errorMessage);
    }

    @Override
    public void onSpecialCardPlayed(Player player, SpecialCardPlayedEvent specialCardPlayedEvent) {
        this.gameStateObserver.onSpecialCardPlayed(player, specialCardPlayedEvent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoboGameObserverWrapper that = (RoboGameObserverWrapper) o;
        return Objects.equals(gameStateObserver, that.gameStateObserver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameStateObserver);
    }
}
