package pink.digitally.games.whot.whotcore.events.handler;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;

import java.util.Deque;

public interface PlayEventHandler {
    Either<ErrorMessage, Deque<Player>> handle(PlayerEvent playerEvent, Player currentPlayer, Deque<Player> allPlayers, Board board);
}
