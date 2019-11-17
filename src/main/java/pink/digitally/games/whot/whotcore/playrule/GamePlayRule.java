package pink.digitally.games.whot.whotcore.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;

import java.util.Deque;

public interface GamePlayRule {
    String getDescription();
    boolean cardMatches(WhotCardWithNumberAndShape whotCardWithNumberAndShape);
    Deque<Player> play(WhotCardWithNumberAndShape whotCard,
                       Player currentPlayer,
                       Deque<Player> allPlayers,
                       Board board,
                       GameStateObserver gameStateObserver,
                       GameMediator gameMediator);
}
