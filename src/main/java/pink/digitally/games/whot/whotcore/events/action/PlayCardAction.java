package pink.digitally.games.whot.whotcore.events.action;

import io.vavr.control.Either;
import pink.digitally.games.whot.playrule.GamePlayRuleDeterminer;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;

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
                                                      Player currentPlayer,
                                                      Deque<Player> allPlayers,
                                                      Board board,
                                                      GameStateObserver gameStateObserver) {
        return whotCard
                .map(card -> processTheCard(card, currentPlayer, allPlayers, board))
                .orElseThrow(() -> new UnsupportedOperationException("This should be fixed!!"));
    }

    private Either<ErrorMessage, Deque<Player>> processTheCard(WhotCardWithNumberAndShape whotCard,
                                                               Player currentPlayer,
                                                               Deque<Player> allPlayers,
                                                               Board board) {
        WhotCardWithNumberAndShape topOfPlayPile = board.getTopOfPlayPile();
        if (isValidPlay(whotCard, topOfPlayPile)) {
            Deque<Player> players = gamePlayRuleDeterminer.determineRuleToApply(whotCard)
                    .play(whotCard, currentPlayer, allPlayers, board);
            return Either.right(players);
        }
        return Either.left(new ErrorMessage(String.format("Invalid play. Played '%s' on Play Pile with '%s'", whotCard, topOfPlayPile)));
    }
}
