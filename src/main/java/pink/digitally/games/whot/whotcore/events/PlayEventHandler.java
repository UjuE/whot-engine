package pink.digitally.games.whot.whotcore.events;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.Player;

import java.util.Deque;

public interface PlayEventHandler {
    Deque<Player> handle(PlayerEvent playerEvent, Player currentPlayer, Deque<Player> allPlayers, Board board);
}
