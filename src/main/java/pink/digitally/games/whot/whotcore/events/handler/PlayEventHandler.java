package pink.digitally.games.whot.whotcore.events.handler;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;

import java.util.Deque;

public interface PlayEventHandler {
    Deque<Player> handle(PlayerEvent playerEvent, Player currentPlayer, Deque<Player> allPlayers, Board board);
}
