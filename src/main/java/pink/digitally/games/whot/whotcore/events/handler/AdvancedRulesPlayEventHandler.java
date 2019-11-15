package pink.digitally.games.whot.whotcore.events.handler;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameMediator;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayerEventType;
import pink.digitally.games.whot.whotcore.events.action.PlayerEventAction;

import java.util.Deque;
import java.util.stream.Stream;

import static pink.digitally.games.whot.whotcore.events.action.PlayEventActionFactory.advancedTakeCardAction;
import static pink.digitally.games.whot.whotcore.events.action.PlayEventActionFactory.playActionWithAdvancedRules;

public class AdvancedRulesPlayEventHandler implements PlayEventHandler {
    @Override
    public Either<ErrorMessage, Deque<Player>> handle(PlayerEvent playerEvent,
                                                      Player currentPlayer,
                                                      Deque<Player> allPlayers,
                                                      Board board,
                                                      GameStateObserver gameStateObserver,
                                                      GameMediator gameMediator) {
        return PlayEventHandlerAction
                .actionFor(playerEvent.getPlayerEventType())
                .handle(playerEvent.cardToPlay(), currentPlayer, allPlayers, board, gameStateObserver, gameMediator);
    }

    private enum PlayEventHandlerAction{
        TAKE_CARD(PlayerEventType.TAKE_CARD, advancedTakeCardAction()),
        PLAY_CARD(PlayerEventType.PLAY_CARD, playActionWithAdvancedRules());

        private final PlayerEventType playerEventType;
        private final PlayerEventAction takeCardAction;

        PlayEventHandlerAction(PlayerEventType playerEventType, PlayerEventAction takeCardAction) {

            this.playerEventType = playerEventType;
            this.takeCardAction = takeCardAction;
        }


        public static PlayerEventAction actionFor(PlayerEventType playerEventType){
            return Stream.of(values())
                    .filter(it -> playerEventType.equals(it.playerEventType))
                    .findFirst()
                    .map(it -> it.takeCardAction)
                    .orElseThrow(() -> new UnsupportedOperationException("How did this happen"));
        }
    }
}
