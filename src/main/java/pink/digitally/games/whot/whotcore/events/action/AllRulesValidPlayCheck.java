package pink.digitally.games.whot.whotcore.events.action;

import pink.digitally.games.whot.whotcore.WhotCard;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.WhotShape;

import java.util.stream.Stream;

public class AllRulesValidPlayCheck {
    private AllRulesValidPlayCheck(){}

    public static boolean isValidPlay(WhotCardWithNumberAndShape playedCard, WhotCardWithNumberAndShape topOfPlayPile) {
        return isNumbersEqual(playedCard, topOfPlayPile)
                || isShapeEqual(playedCard, topOfPlayPile)
                || isAnyWhotCard(playedCard, topOfPlayPile);
    }

    private static boolean isShapeEqual(WhotCardWithNumberAndShape playedCard, WhotCardWithNumberAndShape topOfPlayPile) {
        return playedCard.getShape().equals(topOfPlayPile.getShape());
    }

    private static boolean isNumbersEqual(WhotCardWithNumberAndShape playedCard, WhotCardWithNumberAndShape topOfPlayPile) {
        return playedCard.getNumber().equals(topOfPlayPile.getNumber());
    }

    private static boolean isAnyWhotCard(WhotCardWithNumberAndShape playedCard, WhotCardWithNumberAndShape topOfPlayPile) {
        return Stream.of(playedCard, topOfPlayPile)
                .anyMatch(it -> it.equals(WhotCard.whotCard(WhotNumber.TWENTY, WhotShape.WHOT)));
    }
}
