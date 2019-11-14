package pink.digitally.games.whot.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.WhotNumber;

import java.util.Deque;

public class HoldOnGamePlayRule implements GamePlayRule {
    @Override
    public String getDescription() {
        return "HOLD ON Current player gets another turn";
    }

    @Override
    public boolean cardMatches(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        return WhotNumber.ONE.equals(whotCardWithNumberAndShape.getNumber());
    }

    @Override
    public Deque<Player> play(WhotCardWithNumberAndShape whotCard, Player currentPlayer, Deque<Player> allPlayers, Board board) {
        board.addToPlayPile(whotCard);

        currentPlayer.getCards().remove(whotCard);
        return allPlayers;
    }
}
