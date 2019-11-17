package pink.digitally.games.whot.whotcore.events;

import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;

import java.util.Optional;

public interface PlayerEvent {
    PlayerEventType getPlayerEventType();
    Optional<WhotCardWithNumberAndShape> cardToPlay();
}
