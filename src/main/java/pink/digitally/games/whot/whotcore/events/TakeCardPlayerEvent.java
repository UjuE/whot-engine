package pink.digitally.games.whot.whotcore.events;

import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;

import java.util.Optional;

public class TakeCardPlayerEvent implements PlayerEvent {

    @Override
    public PlayerEventType getPlayerEventType() {
        return PlayerEventType.TAKE_CARD;
    }

    @Override
    public Optional<WhotCardWithNumberAndShape> cardToPlay() {
        return Optional.empty();
    }
}
