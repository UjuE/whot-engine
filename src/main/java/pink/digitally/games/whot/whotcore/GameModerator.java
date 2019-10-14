package pink.digitally.games.whot.whotcore;

import java.util.Collections;
import java.util.List;

public class GameModerator {
    public List<WhotCard> shuffle(List<WhotCard> cards) {
        Collections.shuffle(cards);
        return cards;
    }
}
