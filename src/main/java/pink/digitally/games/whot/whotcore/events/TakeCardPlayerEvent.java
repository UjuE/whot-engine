package pink.digitally.games.whot.whotcore.events;

import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;

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
