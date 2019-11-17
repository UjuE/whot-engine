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
import static java.lang.String.format;

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


        int numberOfCardsToPick = max(gameMediator.getTotalTakeCount(), 1);

        takeCards(currentPlayer, board, numberOfCardsToPick);

        if (gameMediator.isInSpecialPlay()) {
            gameStateObserver.onSpecialCardPlayed(currentPlayer,
                    SpecialCardPlayedEvent.PICKED_CARDS
                            .witExtraDetail(
                                    format("Picked %d cards", numberOfCardsToPick)
                            ));
        }

        allPlayers.remove(currentPlayer);
        allPlayers.addLast(currentPlayer);
        gameMediator.resetTakeCount();
        gameMediator.resetNextPlayEventValidation();
        return Either.right(allPlayers);
    }

    private void takeCards(Player currentPlayer, Board board, int numberOfCardsToPick) {
        for (int i = 0; i < numberOfCardsToPick; i++) {
            takeIfBoardDrawPileHasCards(currentPlayer, board);
        }
    }

    private void takeIfBoardDrawPileHasCards(Player currentPlayer, Board board) {
        if(!board.getDrawPile().isEmpty()){
            WhotCardWithNumberAndShape whotCardWithNumberAndShape = board.takeFromDrawPile();
            currentPlayer.addCard(whotCardWithNumberAndShape);
        }
    }
}
