package pink.digitally.games.whot.whotcore.events.action;

import pink.digitally.games.whot.whotcore.card.WhotCard;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotNumber;
import pink.digitally.games.whot.whotcore.card.WhotShape;

import java.util.stream.Stream;

public class AllRulesValidPlayCheck {
    private AllRulesValidPlayCheck(){}

    public static boolean isValidPlay(WhotCardWithNumberAndShape playedCard, WhotCardWithNumberAndShape topOfPlayPile) {
        return isNumbersEqual(playedCard, topOfPlayPile)
                || isShapeEqual(playedCard, topOfPlayPile)
                || isAnyWhotCard(playedCard, topOfPlayPile);
    }

    public static boolean isWhotCard(WhotCardWithNumberAndShape whotCardWithNumberAndShape){
        return WhotShape.WHOT.equals(whotCardWithNumberAndShape.getShape())
                && WhotNumber.TWENTY.equals(whotCardWithNumberAndShape.getNumber());
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
