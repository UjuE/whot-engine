package pink.digitally.games.whot.whotcore.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotNumber;

import java.util.Deque;
import java.util.Optional;

public class SuspensionGamePlayRule implements GamePlayRule {
    @Override
    public String getDescription() {
        return "SUSPENSION Next player skips a turn";
    }

    @Override
    public boolean cardMatches(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        return WhotNumber.EIGHT.equals(whotCardWithNumberAndShape.getNumber());
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
                .ifPresent(it -> it.onSpecialCardPlayed(currentPlayer, SpecialCardPlayedEvent.PLAYED_SUSPENSION));

        Player theCurrentPlayer = allPlayers.removeFirst();
        Player theSkippedNexPlayer = allPlayers.removeFirst();

        allPlayers.addLast(theCurrentPlayer);
        allPlayers.addLast(theSkippedNexPlayer);

        return allPlayers;
    }
}
