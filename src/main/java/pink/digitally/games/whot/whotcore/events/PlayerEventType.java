package pink.digitally.games.whot.whotcore.events;

import pink.digitally.games.whot.whotcore.events.action.PlayerEventAction;
import pink.digitally.games.whot.whotcore.events.handler.PlayEventHandler;

import java.util.function.Function;

public enum PlayerEventType {
    PLAY_CARD(PlayEventHandler::getPlayCardAction),
    TAKE_CARD(PlayEventHandler::getTakeCardAction),
    CHOOSE_SHAPE(PlayEventHandler::getChooseCardAction);

    private Function<PlayEventHandler, PlayerEventAction> function;

    PlayerEventType(Function<PlayEventHandler, PlayerEventAction> function) {
        this.function = function;
    }

    public Function<PlayEventHandler, PlayerEventAction> function(){
        return function;
    }
}
