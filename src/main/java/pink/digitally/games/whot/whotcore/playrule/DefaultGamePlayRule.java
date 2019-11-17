package pink.digitally.games.whot.whotcore.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;

import java.util.Deque;

public class DefaultGamePlayRule implements GamePlayRule {
    @Override
    public String getDescription() {
        return "Default Rules";
    }

    @Override
    public boolean cardMatches(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        return true;
    }

    @Override
    public Deque<Player> play(WhotCardWithNumberAndShape whotCard, Player currentPlayer, Deque<Player> allPlayers,
                              Board board, GameStateObserver gameStateObserver, GameMediator gameMediator) {
        board.addToPlayPile(whotCard);

        currentPlayer.getCards().remove(whotCard);

        allPlayers.remove(currentPlayer);
        allPlayers.addLast(currentPlayer);

        return allPlayers;
    }
}
