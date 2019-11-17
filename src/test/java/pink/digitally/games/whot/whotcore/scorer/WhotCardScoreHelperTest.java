package pink.digitally.games.whot.whotcore.scorer;

import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.whotcore.card.WhotCard;
import pink.digitally.games.whot.whotcore.card.WhotNumber;
import pink.digitally.games.whot.whotcore.card.WhotShape;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WhotCardScoreHelperTest {

    @Test
    void standardCardScore() {
        assertEquals(10, WhotCardScoreHelper
                .scoreFor(WhotCard.whotCard(WhotNumber.TEN, WhotShape.CIRCLE)));
    }

    @Test
    void starCardScore() {
        assertEquals(16, WhotCardScoreHelper
                .scoreFor(WhotCard.whotCard(WhotNumber.EIGHT, WhotShape.STAR)));
    }
}