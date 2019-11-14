package pink.digitally.games.whot.whotcore.events.action;

import pink.digitally.games.whot.whotcore.playrule.GamePlayRule;
import pink.digitally.games.whot.whotcore.playrule.GamePlayRuleDeterminer;
import pink.digitally.games.whot.whotcore.playrule.GeneralMarketGamePlayRule;
import pink.digitally.games.whot.whotcore.playrule.HoldOnGamePlayRule;
import pink.digitally.games.whot.whotcore.playrule.PickTwoGamePlayRule;
import pink.digitally.games.whot.whotcore.playrule.SuspensionGamePlayRule;
import pink.digitally.games.whot.whotcore.playrule.WhotGamePlayRule;

import java.util.Collection;

import static java.util.Arrays.asList;

public class PlayEventActionFactory {

    private PlayEventActionFactory() {
    }

    public static PlayerEventAction takeCardAction() {
        return new TakeCardAction();
    }

    public static PlayerEventAction playActionWithBasicRules() {
        return new PlayCardAction(new GamePlayRuleDeterminer());
    }

    public static PlayerEventAction playActionWithStandardRules() {
        return new PlayCardAction(standardRulesGamePlayDeterminer());
    }

    private static Collection<GamePlayRule> standardRules() {
        return asList(
                new HoldOnGamePlayRule(),
                new PickTwoGamePlayRule(),
                new SuspensionGamePlayRule(),
                new GeneralMarketGamePlayRule(),
                new WhotGamePlayRule()
        );
    }

    private static GamePlayRuleDeterminer standardRulesGamePlayDeterminer() {
        return new GamePlayRuleDeterminer(standardRules());
    }
}
