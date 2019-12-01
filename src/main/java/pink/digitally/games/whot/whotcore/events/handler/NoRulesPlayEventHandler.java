package pink.digitally.games.whot.whotcore.events.handler;

import pink.digitally.games.whot.whotcore.events.action.PlayerEventAction;

import static pink.digitally.games.whot.whotcore.events.action.PlayEventActionFactory.playActionWithBasicRules;
import static pink.digitally.games.whot.whotcore.events.action.PlayEventActionFactory.takeCardAction;

public class NoRulesPlayEventHandler implements PlayEventHandler {

    @Override
    public PlayerEventAction getPlayCardAction() {
        return playActionWithBasicRules();
    }

    @Override
    public PlayerEventAction getTakeCardAction() {
        return takeCardAction();
    }

    @Override
    public PlayerEventAction getChooseCardAction() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
