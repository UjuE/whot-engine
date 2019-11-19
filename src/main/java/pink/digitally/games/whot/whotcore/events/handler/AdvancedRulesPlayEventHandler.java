package pink.digitally.games.whot.whotcore.events.handler;

import pink.digitally.games.whot.whotcore.events.action.PlayerEventAction;

import static pink.digitally.games.whot.whotcore.events.action.PlayEventActionFactory.advancedTakeCardAction;
import static pink.digitally.games.whot.whotcore.events.action.PlayEventActionFactory.playActionWithAdvancedRules;

public class AdvancedRulesPlayEventHandler implements PlayEventHandler {
    @Override
    public PlayerEventAction getPlayCardAction() {
        return playActionWithAdvancedRules();
    }

    @Override
    public PlayerEventAction getTakeCardAction() {
        return advancedTakeCardAction();
    }
}
