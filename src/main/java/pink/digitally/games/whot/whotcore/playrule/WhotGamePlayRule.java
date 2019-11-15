package pink.digitally.games.whot.whotcore.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.WhotShape;

import java.util.Deque;

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
    public Deque<Player> play(WhotCardWithNumberAndShape whotCard, Player currentPlayer, Deque<Player> allPlayers, Board board, GameStateObserver gameStateObserver) {
        board.addToPlayPile(whotCard);
        currentPlayer.getCards().remove(whotCard);
        return allPlayers;
    }
}