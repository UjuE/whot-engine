package pink.digitally.games.whot.whotcore.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.validation.OrValidator;
import pink.digitally.games.whot.whotcore.validation.PlayBlockerCardEventValidator;
import pink.digitally.games.whot.whotcore.validation.TakeCardEventValidator;

import java.util.Deque;
import java.util.Optional;

import static java.util.Arrays.asList;
import static pink.digitally.games.whot.whotcore.card.WhotNumber.FIVE;

public class AdvancedPickThreeGamePlayRule implements GamePlayRule {

    @Override
    public String getDescription() {
        return "PICK THREE the next player picks 3 cards from the draw pile or blocks it";
    }

    @Override
    public boolean cardMatches(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        return FIVE.equals(whotCardWithNumberAndShape.getNumber());
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
                .ifPresent(it -> it.onSpecialCardPlayed(currentPlayer, SpecialCardPlayedEvent.PLAYED_PICK_THREE));

        allPlayers.remove(currentPlayer);
        allPlayers.addLast(currentPlayer);

        gameMediator.addTakeCardCount(3);
        gameMediator.nextPlayerValidation(
                new OrValidator<>(asList(new PlayBlockerCardEventValidator(),
                        new TakeCardEventValidator())));


        return allPlayers;
    }
}
