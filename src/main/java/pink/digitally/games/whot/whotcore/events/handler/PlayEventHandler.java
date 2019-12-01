package pink.digitally.games.whot.whotcore.events.handler;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.action.PlayerEventAction;

import java.util.Deque;

public interface PlayEventHandler {

    PlayerEventAction getPlayCardAction();

    PlayerEventAction getTakeCardAction();

    PlayerEventAction getChooseCardAction();

    default Either<ErrorMessage, Deque<Player>> handle(PlayerEvent playerEvent,
                                                       Player currentPlayer,
                                                       Deque<Player> allPlayers,
                                                       Board board,
                                                       GameStateObserver gameStateObserver,
                                                       GameMediator gameMediator) {
        return playerEvent.getPlayerEventType()
                .function()
                .apply(this)
                .handle(playerEvent.cardToPlay(), playerEvent.chosenShape(),
                        currentPlayer, allPlayers,
                        board, gameStateObserver,
                        gameMediator);
    }

}
