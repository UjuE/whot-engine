package pink.digitally.games.whot.whotcore.events.handler;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayerEventType;
import pink.digitally.games.whot.whotcore.events.action.PlayerEventAction;

import java.util.Deque;

import static pink.digitally.games.whot.whotcore.events.PlayerEventType.PLAY_CARD;

public interface PlayEventHandler {

    PlayerEventAction getPlayCardAction();

    PlayerEventAction getTakeCardAction();

    default Either<ErrorMessage, Deque<Player>> handle(PlayerEvent playerEvent,
                                                       Player currentPlayer,
                                                       Deque<Player> allPlayers,
                                                       Board board,
                                                       GameStateObserver gameStateObserver, GameMediator gameMediator) {
        return playerActionFor(playerEvent.getPlayerEventType())
                .handle(playerEvent.cardToPlay(), currentPlayer, allPlayers, board, gameStateObserver, gameMediator);
    }

    default PlayerEventAction playerActionFor(PlayerEventType playerEventType) {
        if (playerEventType == PLAY_CARD) {
            return getPlayCardAction();
        } else if (playerEventType == PlayerEventType.TAKE_CARD) {
            return getTakeCardAction();
        }
        throw new UnsupportedOperationException("This should not happen");
    }
}
