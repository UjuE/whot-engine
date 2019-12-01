package pink.digitally.games.whot.whotcore.events;

import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotShape;

import java.util.Optional;

public interface PlayerEvent {
    PlayerEventType getPlayerEventType();
    Optional<WhotCardWithNumberAndShape> cardToPlay();
    Optional<WhotShape> chosenShape();
}
