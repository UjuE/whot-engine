package pink.digitally.games.whot.whotcore.events.action;

import pink.digitally.games.whot.whotcore.playrule.AdvancedPickThreeGamePlayRule;
import pink.digitally.games.whot.whotcore.playrule.AdvancedPickTwoGamePlayRule;
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

    public static PlayerEventAction advancedTakeCardAction() {
        return new AdvancedRuleTakeCardAction();
    }

    public static PlayerEventAction playActionWithBasicRules() {
        return new PlayCardAction(new GamePlayRuleDeterminer());
    }

    public static PlayerEventAction playActionWithStandardRules() {
        return new PlayCardAction(standardRulesGamePlayDeterminer());
    }

    public static PlayerEventAction playActionWithAdvancedRules() {
        return new PlayCardAction(new GamePlayRuleDeterminer(advancedRules()));
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

    private static Collection<GamePlayRule> advancedRules() {
        return asList(
                new HoldOnGamePlayRule(),
                new AdvancedPickTwoGamePlayRule(),
                new AdvancedPickThreeGamePlayRule(),
                new SuspensionGamePlayRule(),
                new GeneralMarketGamePlayRule(),
                new WhotGamePlayRule()
        );
    }

    private static GamePlayRuleDeterminer standardRulesGamePlayDeterminer() {
        return new GamePlayRuleDeterminer(standardRules());
    }
}
