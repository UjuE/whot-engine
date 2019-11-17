package pink.digitally.games.whot.whotcore.events.action;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.playrule.SpecialCardPlayedEvent;

import java.util.Deque;
import java.util.Optional;

import static java.lang.Math.max;

class AdvancedRuleTakeCardAction implements PlayerEventAction {
    AdvancedRuleTakeCardAction() {
    }

    @Override
    public Either<ErrorMessage, Deque<Player>> handle(Optional<WhotCardWithNumberAndShape> whotCard,
                                                      Player currentPlayer,
                                                      Deque<Player> allPlayers,
                                                      Board board,
                                                      GameStateObserver gameStateObserver,
                                                      GameMediator gameMediator) {


        for (int i = 0; i < max(gameMediator.getTotalTakeCount(), 1); i++) {
            WhotCardWithNumberAndShape whotCardWithNumberAndShape = board.takeFromDrawPile();
            currentPlayer.addCard(whotCardWithNumberAndShape);
        }

        if (gameMediator.isInSpecialPlay()) {
            gameStateObserver.onSpecialCardPlayed(currentPlayer, SpecialCardPlayedEvent.PICK_CARDS);
        }

        allPlayers.remove(currentPlayer);
        allPlayers.addLast(currentPlayer);
        gameMediator.resetTakeCount();
        gameMediator.resetNextPlayEventValidation();
        return Either.right(allPlayers);
    }
}
