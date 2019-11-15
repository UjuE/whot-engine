package pink.digitally.games.whot.whotcore.playrule;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.validation.OrValidator;
import pink.digitally.games.whot.whotcore.validation.PlayCardWithNumberEventValidator;
import pink.digitally.games.whot.whotcore.validation.TakeCardEventValidator;

import java.util.Deque;
import java.util.Optional;

import static java.util.Arrays.asList;
import static pink.digitally.games.whot.whotcore.WhotNumber.TWO;

public class PickTwoGamePlayRule implements GamePlayRule {
    private boolean isAdvancedGamePlayRule;

    public PickTwoGamePlayRule(){
        this(false);
    }

    public PickTwoGamePlayRule(boolean isAdvancedGamePlayRule) {
        this.isAdvancedGamePlayRule = isAdvancedGamePlayRule;
    }

    @Override
    public String getDescription() {
        return "PICK TWO the next player picks 2 cards from the draw pile";
    }

    @Override
    public boolean cardMatches(WhotCardWithNumberAndShape whotCardWithNumberAndShape) {
        return TWO.equals(whotCardWithNumberAndShape.getNumber());
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
                .ifPresent(it -> it.onSpecialCardPlayed(currentPlayer, SpecialCardPlayedEvent.PICK_TWO));

        allPlayers.remove(currentPlayer);
        allPlayers.addLast(currentPlayer);

        if(isAdvancedGamePlayRule){
            gameMediator.addTakeCardCount(2);
            gameMediator.nextPlayerValidation(
                    new OrValidator<>(asList(new PlayCardWithNumberEventValidator(TWO),
                            new TakeCardEventValidator())));
        } else {
            Player nextPlayerToPickTwo = allPlayers.removeFirst();
            nextPlayerToPickTwo.addCard(board.takeFromDrawPile());
            nextPlayerToPickTwo.addCard(board.takeFromDrawPile());
            allPlayers.addLast(nextPlayerToPickTwo);
        }


        return allPlayers;
    }
}
