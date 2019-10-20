package pink.digitally.games.whot.whotcore.events.handler;

import pink.digitally.games.whot.whotcore.Board;
import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayerEventType;
import pink.digitally.games.whot.whotcore.events.action.NoRulesPlayCardAction;
import pink.digitally.games.whot.whotcore.events.action.NoRulesTakeCardAction;
import pink.digitally.games.whot.whotcore.events.action.PlayerEventAction;

import java.util.Deque;
import java.util.stream.Stream;

import static pink.digitally.games.whot.whotcore.events.PlayerEventType.PLAY_CARD;
import static pink.digitally.games.whot.whotcore.events.PlayerEventType.TAKE_CARD;

public class NoRulesPlayEventHandler implements PlayEventHandler {
    @Override
    public Deque<Player> handle(PlayerEvent playerEvent, Player currentPlayer, Deque<Player> allPlayers, Board board) {
        return PlayEventHandlerAction
                .actionFor(playerEvent.getPlayerEventType())
                .handle(playerEvent.cardToPlay(), currentPlayer, allPlayers, board);
    }

    private enum PlayEventHandlerAction {
        PLAY_CARD_EVENT_HANDLER(PLAY_CARD, new NoRulesPlayCardAction()),
        TAKE_CARD_EVENT_HANDLER(TAKE_CARD, new NoRulesTakeCardAction());

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
