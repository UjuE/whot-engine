package pink.digitally.games.whot.whotcore.events.handler;

import pink.digitally.games.whot.whotcore.events.action.PlayerEventAction;

import static pink.digitally.games.whot.whotcore.events.action.PlayEventActionFactory.playActionWithStandardRules;
import static pink.digitally.games.whot.whotcore.events.action.PlayEventActionFactory.takeCardAction;

public class StandardRulesPlayEventHandler implements PlayEventHandler {

    @Override
    public PlayerEventAction getPlayCardAction() {
        return playActionWithStandardRules();
    }

    @Override
    public PlayerEventAction getTakeCardAction() {
        return takeCardAction();
    }
}
