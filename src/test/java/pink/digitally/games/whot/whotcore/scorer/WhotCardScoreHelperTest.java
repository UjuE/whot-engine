package pink.digitally.games.whot.whotcore.scorer;

import org.junit.jupiter.api.Test;
import pink.digitally.games.whot.whotcore.WhotCard;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.WhotShape;

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