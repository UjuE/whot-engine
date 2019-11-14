package pink.digitally.games.whot.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;

import java.util.Deque;

public interface GamePlayRule {
    String getDescription();
    boolean cardMatches(WhotCardWithNumberAndShape whotCardWithNumberAndShape);
    Deque<Player> play(WhotCardWithNumberAndShape whotCard,
                       Player currentPlayer,
                       Deque<Player> allPlayers,
                       Board board);
}
