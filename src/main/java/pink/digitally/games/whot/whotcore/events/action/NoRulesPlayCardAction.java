package pink.digitally.games.whot.whotcore.events.action;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCard;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.WhotShape;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;

import java.util.Deque;
import java.util.Optional;
import java.util.stream.Stream;

public class NoRulesPlayCardAction implements PlayerEventAction {

    @Override
    public Either<ErrorMessage, Deque<Player>> handle(Optional<WhotCardWithNumberAndShape> whotCard, Player currentPlayer, Deque<Player> allPlayers, Board board) {
        return whotCard
                .map(card -> processTheCard(card, currentPlayer, allPlayers, board))
                .orElseThrow(() -> new UnsupportedOperationException("This should be fixed!!"));
    }

    private Either<ErrorMessage, Deque<Player>> processTheCard(WhotCardWithNumberAndShape whotCard, Player currentPlayer, Deque<Player> allPlayers, Board board) {
        WhotCardWithNumberAndShape topOfPlayPile = board.getTopOfPlayPile();
        if (isValidPlay(whotCard, topOfPlayPile)) {
            board.addToPlayPile(whotCard);

            currentPlayer.getCards().remove(whotCard);

            allPlayers.remove(currentPlayer);
            allPlayers.addLast(currentPlayer);
            return Either.right(allPlayers);
        }
        return Either.left(new ErrorMessage(String.format("Invalid play. Played '%s' on Play Pile with '%s'", whotCard, topOfPlayPile)));
    }

    private boolean isValidPlay(WhotCardWithNumberAndShape playedCard, WhotCardWithNumberAndShape topOfPlayPile) {
        return isNumbersEqual(playedCard, topOfPlayPile)
                || isShapeEqual(playedCard, topOfPlayPile)
                || isAnyWhotCard(playedCard, topOfPlayPile);
    }

    private boolean isShapeEqual(WhotCardWithNumberAndShape playedCard, WhotCardWithNumberAndShape topOfPlayPile) {
        return playedCard.getShape().equals(topOfPlayPile.getShape());
    }

    private boolean isNumbersEqual(WhotCardWithNumberAndShape playedCard, WhotCardWithNumberAndShape topOfPlayPile) {
        return playedCard.getNumber().equals(topOfPlayPile.getNumber());
    }

    private boolean isAnyWhotCard(WhotCardWithNumberAndShape playedCard, WhotCardWithNumberAndShape topOfPlayPile) {
        return Stream.of(playedCard, topOfPlayPile)
                .anyMatch(it -> it.equals(WhotCard.whotCard(WhotNumber.TWENTY, WhotShape.WHOT)));
    }
}
