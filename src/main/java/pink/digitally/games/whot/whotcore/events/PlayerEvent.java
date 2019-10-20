package pink.digitally.games.whot.whotcore.events;

import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;

import java.util.Optional;

public interface PlayerEvent {
    PlayerEventType getPlayerEventType();
    Optional<WhotCardWithNumberAndShape> cardToPlay();
}
