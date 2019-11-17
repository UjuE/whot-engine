package pink.digitally.games.whot.whotcore.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;

import java.util.Deque;
import java.util.Optional;

import static pink.digitally.games.whot.whotcore.card.WhotNumber.TWO;

public class PickTwoGamePlayRule implements GamePlayRule {

    @Override
    public String getDescription() {
        return "PICK TWO the next player picks 2 cards from the draw pile";
    }

    @Override
    public boolean cardMatches(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        return TWO.equals(whotCardWithNumberAndShape.getNumber());
    }

    @Override
    public Deque<Player> play(WhotCardWithNumberAndShape whotCard,
                              Player currentPlayer,
                              Deque<Player> allPlayers,
                              Board board,
                              GameStateObserver gameStateObserver,
                              GameMediator gameMediator) {
        board.addToPlayPile(whotCard);
        currentPlayer.getCards().remove(whotCard);

        Optional.ofNullable(gameStateObserver)
                .ifPresent(it -> it.onSpecialCardPlayed(currentPlayer, SpecialCardPlayedEvent.PLAYED_PICK_TWO));

        allPlayers.remove(currentPlayer);
        allPlayers.addLast(currentPlayer);

        Player nextPlayerToPickTwo = allPlayers.removeFirst();
        nextPlayerToPickTwo.addCard(board.takeFromDrawPile());
        nextPlayerToPickTwo.addCard(board.takeFromDrawPile());
        allPlayers.addLast(nextPlayerToPickTwo);


        return allPlayers;
    }
}
