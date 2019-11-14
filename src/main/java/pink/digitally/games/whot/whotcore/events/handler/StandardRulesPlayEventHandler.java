package pink.digitally.games.whot.whotcore.events.handler;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.GameStateObserver;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayerEventType;
import pink.digitally.games.whot.whotcore.events.action.PlayerEventAction;

import java.util.Deque;
import java.util.stream.Stream;

import static pink.digitally.games.whot.whotcore.events.PlayerEventType.PLAY_CARD;
import static pink.digitally.games.whot.whotcore.events.PlayerEventType.TAKE_CARD;
import static pink.digitally.games.whot.whotcore.events.action.PlayEventActionFactory.playActionWithStandardRules;
import static pink.digitally.games.whot.whotcore.events.action.PlayEventActionFactory.takeCardAction;

public class StandardRulesPlayEventHandler implements PlayEventHandler {

    @Override
    public Either<ErrorMessage, Deque<Player>> handle(PlayerEvent playerEvent,
                                                      Player currentPlayer,
                                                      Deque<Player> allPlayers,
                                                      Board board,
                                                      GameStateObserver gameStateObserver) {
        return PlayEventHandlerAction
                .actionFor(playerEvent.getPlayerEventType())
                .handle(playerEvent.cardToPlay(), currentPlayer, allPlayers, board, gameStateObserver);
    }

    private enum PlayEventHandlerAction {
        PLAY_CARD_EVENT_HANDLER(PLAY_CARD, playActionWithStandardRules()),
        TAKE_CARD_EVENT_HANDLER(TAKE_CARD, takeCardAction());

        private final PlayerEventType eventType;
        private final PlayerEventAction playEventAction;

        PlayEventHandlerAction(PlayerEventType eventType, PlayerEventAction playEventAction) {
            this.eventType = eventType;
            this.playEventAction = playEventAction;
        }

        public static PlayerEventAction actionFor(PlayerEventType playerEventType){
            return Stream.of(values())
                    .filter(it -> it.eventType.equals(playerEventType))
                    .map(it -> it.playEventAction)
                    .findFirst()
                    .orElseThrow(() -> new UnsupportedOperationException("Get rid of this"));
        }
    }

}
