package pink.digitally.games.whot.whotcore.scorer;

import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotShape;

public class WhotCardScoreHelper {
    private WhotCardScoreHelper() {
    }

    public static int scoreFor(WhotCardWithNumberAndShape whotCard) {
        if (WhotShape.STAR.equals(whotCard.getShape())) {
            return 2 * whotCard.getNumber().getNumericValue();
        }
        return whotCard.getNumber().getNumericValue();
    }
}
