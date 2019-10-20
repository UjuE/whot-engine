package pink.digitally.games.whot.whotcore.events.action;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;

import java.util.Deque;
import java.util.Optional;

public class NoRulesTakeCardAction implements PlayerEventAction {
    @Override
    public Deque<Player> handle(Optional<WhotCardWithNumberAndShape> whotCard, Player currentPlayer, Deque<Player> allPlayers, Board board) {
        WhotCardWithNumberAndShape whotCardWithNumberAndShape = board.takeFromDrawPile();
        currentPlayer.addCard(whotCardWithNumberAndShape);
        allPlayers.remove(currentPlayer);
        allPlayers.addLast(currentPlayer);
        return allPlayers;
    }
}
