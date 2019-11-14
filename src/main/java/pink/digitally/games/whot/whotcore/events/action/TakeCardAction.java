package pink.digitally.games.whot.whotcore.events.action;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;

import java.util.Deque;
import java.util.Optional;

class TakeCardAction implements PlayerEventAction {
    TakeCardAction(){}
    @Override
    public Either<ErrorMessage, Deque<Player>> handle(Optional<WhotCardWithNumberAndShape> whotCard, Player currentPlayer, Deque<Player> allPlayers, Board board, GameStateObserver gameStateObserver) {
        WhotCardWithNumberAndShape whotCardWithNumberAndShape = board.takeFromDrawPile();
        currentPlayer.addCard(whotCardWithNumberAndShape);
        allPlayers.remove(currentPlayer);
        allPlayers.addLast(currentPlayer);
        return Either.right(allPlayers);
    }
}
