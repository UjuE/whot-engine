package pink.digitally.games.whot.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.WhotShape;
import pink.digitally.games.whot.whotcore.events.handler.AdvancedRulesPlayEventHandler;
import pink.digitally.games.whot.whotcore.events.handler.PlayEventHandler;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pink.digitally.games.whot.whotcore.WhotCard.whotCard;

@DisplayName("Whot game with standard rules should")
class AdvancedRulesPlayGameTest extends AcceptanceTestBase {

    @Test
    void continousPickTwo() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Arrays.asList(whotCard(WhotNumber.ONE, WhotShape.SQUARE),
                whotCard(WhotNumber.TWO, WhotShape.SQUARE)), ngozi);

        andTheGameMediatorWillDeal(Arrays.asList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE),
                whotCard(WhotNumber.TWO, WhotShape.STAR)), emeka);

        andTheGameMediatorWillDeal(Collections.singletonList(whotCard(WhotNumber.TEN, WhotShape.CROSS)), ada);

        andTheTopOfPlayPileIs(whotCard(WhotNumber.EIGHT, WhotShape.SQUARE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.TWO, WhotShape.SQUARE));
        whenPlayerPlays(emeka, whotCard(WhotNumber.TWO, WhotShape.SQUARE));
        whenPlayerTakesCard(ada);

        assertEquals(5, ada.getCards().size());
        assertEquals(onyinye, whotGamePlay.nextPlayer());
    }

    @Test
    void takeACardWorksNormally() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Arrays.asList(whotCard(WhotNumber.ONE, WhotShape.SQUARE),
                whotCard(WhotNumber.TWO, WhotShape.SQUARE)), ngozi);
        andTheTopOfPlayPileIs(whotCard(WhotNumber.EIGHT, WhotShape.SQUARE));

        andTheGameHasStarted();

        whenPlayerTakesCard(ngozi);

        assertEquals(3, ngozi.getCards().size());
        assertEquals(emeka.getPlayerName(), whotGamePlay.nextPlayer().getPlayerName());
    }

    @Test
    void fiveIsTakeThree() {
        givenThereIsAWhotGame();
        andTheGameMediatorWillDeal(Arrays.asList(whotCard(WhotNumber.ONE, WhotShape.SQUARE),
                whotCard(WhotNumber.FIVE, WhotShape.SQUARE)), ngozi);

        andTheGameMediatorWillDeal(Arrays.asList(whotCard(WhotNumber.EIGHT, WhotShape.TRIANGLE),
                whotCard(WhotNumber.TWO, WhotShape.STAR)), emeka);

        andTheTopOfPlayPileIs(whotCard(WhotNumber.EIGHT, WhotShape.SQUARE));

        andTheGameHasStarted();

        whenPlayerPlays(ngozi, whotCard(WhotNumber.FIVE, WhotShape.SQUARE));
        whenPlayerTakesCard(emeka);

        assertEquals(5, emeka.getCards().size());
        assertEquals(ada.getPlayerName(), whotGamePlay.nextPlayer().getPlayerName());
    }

    @Override
    PlayEventHandler playEventHandler() {
        return new AdvancedRulesPlayEventHandler();
    }
}
