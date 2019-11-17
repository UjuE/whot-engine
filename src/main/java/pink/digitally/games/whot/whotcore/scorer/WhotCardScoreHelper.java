package pink.digitally.games.whot.whotcore.scorer;

import pink.digitally.games.whot.whotcore.Player;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotShape;

import java.util.Collection;
import java.util.Comparator;

public class WhotCardScoreHelper {
    private WhotCardScoreHelper() {
    }

    public static int scoreFor(WhotCardWithNumberAndShape whotCard) {
        if (WhotShape.STAR.equals(whotCard.getShape())) {
            return 2 * whotCard.getNumber().getNumericValue();
        }
        return whotCard.getNumber().getNumericValue();
    }

    public static Integer totalScore(Collection<WhotCardWithNumberAndShape> whotCard){
        return whotCard
                .stream()
                .mapToInt(WhotCardScoreHelper::scoreFor)
                .sum();
    }

    public static Player lowestScore(Collection<Player> players){
        return players
                .stream()
                .min(Comparator.comparing(p -> totalScore(p.getCards())))
                .orElseThrow(() -> new UnsupportedOperationException("This should never happen."));
    }
}
