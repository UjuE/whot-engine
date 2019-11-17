package pink.digitally.games.whot.whotcore.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotNumber;
import pink.digitally.games.whot.whotcore.card.WhotShape;

import java.util.Deque;

import static java.lang.String.format;

public class WhotGamePlayRule implements GamePlayRule {
    @Override
    public String getDescription() {
        return "WHOT the current player gets another turn";
    }

    @Override
    public boolean cardMatches(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        return WhotNumber.TWENTY.equals(whotCardWithNumberAndShape.getNumber())
                && WhotShape.WHOT.equals(whotCardWithNumberAndShape.getShape());
    }

    @Override
    public Deque<Player> play(WhotCardWithNumberAndShape whotCard,
                              Player currentPlayer,
                              Deque<Player> allPlayers, Board board,
                              GameStateObserver gameStateObserver,
                              GameMediator gameMediator) {
        board.addToPlayPile(whotCard);
        currentPlayer.getCards().remove(whotCard);

        if (gameMediator.isInSpecialPlay()) {
            gameStateObserver.onSpecialCardPlayed(currentPlayer,
                    SpecialCardPlayedEvent.BLOCKED_PICKING_CARDS
                            .witExtraDetail(
                                    format("Blocked picking %d cards",
                                            gameMediator.getTotalTakeCount())
                            ));
        }
        gameMediator.resetTakeCount();
        gameMediator.resetNextPlayEventValidation();
        return allPlayers;
    }
}
