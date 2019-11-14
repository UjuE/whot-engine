package pink.digitally.games.whot.playrule;

import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;

import java.util.Collection;
import java.util.HashSet;

public class GamePlayRuleDeterminer {
    private final Collection<GamePlayRule> gamePlayRules;

    public GamePlayRuleDeterminer(){
        gamePlayRules = new HashSet<>();
    }
    public GamePlayRuleDeterminer(Collection<GamePlayRule> gamePlayRules){
        this.gamePlayRules = gamePlayRules;
    }

    public GamePlayRule determineRuleToApply(WhotCardWithNumberAndShape whotCardWithNumberAndShape){
        return gamePlayRules
                .stream()
                .filter(it -> it.cardMatches(whotCardWithNumberAndShape))
                .findFirst()
                .orElse(new DefaultGamePlayRule());
    }
}
