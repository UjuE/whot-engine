package pink.digitally.games.whot.whotcore.events.action;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotShape;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.playrule.GamePlayRuleDeterminer;

import java.util.Deque;
import java.util.Optional;

import static pink.digitally.games.whot.whotcore.events.action.AllRulesValidPlayCheck.isValidPlay;

class PlayCardAction implements PlayerEventAction {
    private final GamePlayRuleDeterminer gamePlayRuleDeterminer;

    PlayCardAction(GamePlayRuleDeterminer gamePlayRuleDeterminer) {
        this.gamePlayRuleDeterminer = gamePlayRuleDeterminer;
    }

    @Override
    public Either<ErrorMessage, Deque<Player>> handle(Optional<WhotCardWithNumberAndShape> whotCard,
                                                      Optional<WhotShape> whotShape, Player currentPlayer,
                                                      Deque<Player> allPlayers,
                                                      Board board,
                                                      GameStateObserver gameStateObserver, GameMediator gameMediator) {
        return whotCard
                .map(card -> processTheCard(card, currentPlayer, allPlayers, board, gameStateObserver, gameMediator))
                .orElseThrow(() -> new UnsupportedOperationException("This should be fixed!!"));
    }

    private Either<ErrorMessage, Deque<Player>> processTheCard(WhotCardWithNumberAndShape whotCard,
                                                               Player currentPlayer,
                                                               Deque<Player> allPlayers,
                                                               Board board,
                                                               GameStateObserver gameStateObserver,
                                                               GameMediator gameMediator) {
        WhotCardWithNumberAndShape topOfPlayPile = board.getTopOfPlayPile();
        if (isValidPlay(whotCard, topOfPlayPile)) {
            Deque<Player> players = gamePlayRuleDeterminer.determineRuleToApply(whotCard)
                    .play(whotCard, currentPlayer, allPlayers, board, gameStateObserver, gameMediator);
            return Either.right(players);
        }
        return Either.left(new ErrorMessage(String.format("Invalid play. Played '%s' on Play Pile with '%s'", whotCard, topOfPlayPile)));
    }
}
