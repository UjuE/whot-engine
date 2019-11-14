package pink.digitally.games.whot.whotcore;

import pink.digitally.games.whot.state.GameState;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.playrule.SpecialCardPlayedEvent;

import java.util.Collection;

public interface GameStateObserver {
     void onGameStarted(Collection<Player> players, Board board);
     void onGameEnded(Player winner, Collection<Player> players);
     void onPlayerTurn(Player player, Board board);
     GameState getCurrentGameState();
     void onInvalidPlay(Player player, Board board, ErrorMessage errorMessage);
     void onSpecialCardPlayed(Player player, SpecialCardPlayedEvent specialCardPlayedEvent);
}
