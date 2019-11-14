package pink.digitally.games.whot.whotcore.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.WhotNumber;

import java.util.Deque;
import java.util.Optional;

public class PickTwoGamePlayRule implements GamePlayRule {
    @Override
    public String getDescription() {
        return "PICK TWO the next player picks 2 cards from the draw pile";
    }

    @Override
    public boolean cardMatches(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        return WhotNumber.TWO.equals(whotCardWithNumberAndShape.getNumber());
    }

    @Override
    public Deque<Player> play(WhotCardWithNumberAndShape whotCard, Player currentPlayer, Deque<Player> allPlayers, Board board, GameStateObserver gameStateObserver) {
        board.addToPlayPile(whotCard);
        currentPlayer.getCards().remove(whotCard);

        Optional.ofNullable(gameStateObserver)
                .ifPresent(it -> it.onSpecialCardPlayed(currentPlayer, SpecialCardPlayedEvent.PICK_TWO));

        allPlayers.remove(currentPlayer);
        allPlayers.addLast(currentPlayer);
        Player nextPlayerToPickTwo = allPlayers.removeFirst();

        nextPlayerToPickTwo.addCard(board.takeFromDrawPile());
        nextPlayerToPickTwo.addCard(board.takeFromDrawPile());

        allPlayers.addLast(nextPlayerToPickTwo);

        return allPlayers;
    }
}
