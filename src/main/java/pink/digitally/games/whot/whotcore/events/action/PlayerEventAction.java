package pink.digitally.games.whot.whotcore.events.action;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotShape;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;

import java.util.Deque;
import java.util.Optional;

public interface PlayerEventAction {
    Either<ErrorMessage, Deque<Player>> handle(Optional<WhotCardWithNumberAndShape> whotCard,
                                               Optional<WhotShape> whotShape, Player currentPlayer,
                                               Deque<Player> allPlayers,
                                               Board board,
                                               GameStateObserver gameStateObserver, GameMediator gameMediator);
}
