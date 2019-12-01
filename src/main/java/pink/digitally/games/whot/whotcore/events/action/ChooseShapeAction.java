package pink.digitally.games.whot.whotcore.events.action;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotShape;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.playrule.SpecialCardPlayedEvent;
import pink.digitally.games.whot.whotcore.validation.OrValidator;
import pink.digitally.games.whot.whotcore.validation.PlayShapeEventValidator;
import pink.digitally.games.whot.whotcore.validation.PlayWhotEventValidator;
import pink.digitally.games.whot.whotcore.validation.TakeCardEventValidator;

import java.util.Deque;
import java.util.Optional;

import static java.util.Arrays.asList;
import static pink.digitally.games.whot.whotcore.events.action.AllRulesValidPlayCheck.isWhotCard;

public class ChooseShapeAction implements PlayerEventAction {
    ChooseShapeAction(){}

    @Override
    public Either<ErrorMessage, Deque<Player>> handle(Optional<WhotCardWithNumberAndShape> whotCard,
                                                      Optional<WhotShape> whotShape,
                                                      Player currentPlayer,
                                                      Deque<Player> allPlayers,
                                                      Board board,
                                                      GameStateObserver gameStateObserver,
                                                      GameMediator gameMediator) {
        if(isWhotCard(board.getTopOfPlayPile()) && whotShape.isPresent()){
            WhotShape chosenShape = whotShape.get();
            gameMediator.nextPlayerValidation(new OrValidator<>(asList(
                    new PlayShapeEventValidator(chosenShape),
                    new PlayWhotEventValidator(),
                    new TakeCardEventValidator())));

            gameStateObserver.onSpecialCardPlayed(currentPlayer,
                    SpecialCardPlayedEvent.CHOSE_NEXT_SHAPE.witExtraDetail(chosenShape.name()));

            allPlayers.remove(currentPlayer);
            allPlayers.addLast(currentPlayer);

            return Either.right(allPlayers);
        }
        return Either.left(new ErrorMessage("Top of play card must be 20 Whot card"));
    }
}
