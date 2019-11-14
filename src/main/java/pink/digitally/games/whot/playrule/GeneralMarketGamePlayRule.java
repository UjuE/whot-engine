package pink.digitally.games.whot.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.WhotNumber;

import java.util.Deque;

public class GeneralMarketGamePlayRule implements GamePlayRule {
    @Override
    public String getDescription() {
        return "GENERAL MARKET all other players take a card";
    }

    @Override
    public boolean cardMatches(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        return WhotNumber.FOURTEEN.equals(whotCardWithNumberAndShape.getNumber());
    }

    @Override
    public Deque<Player> play(WhotCardWithNumberAndShape whotCard, Player currentPlayer, Deque<Player> allPlayers, Board board) {
        board.addToPlayPile(whotCard);
        currentPlayer.getCards().remove(whotCard);

        allPlayers
                .stream()
                .filter(it -> it != currentPlayer)
                .forEach(it -> it.addCard(board.takeFromDrawPile()));

        return allPlayers;
    }
}