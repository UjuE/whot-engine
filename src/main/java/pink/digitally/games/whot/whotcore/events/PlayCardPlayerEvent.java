package pink.digitally.games.whot.whotcore.events;

import pink.digitally.games.whot.whotcore.WhotCard;
import pink.digitally.games.whot.whotcore.WhotCardWithNumberAndShape;

import java.util.Optional;

public class PlayCardPlayerEvent implements PlayerEvent {
    private final WhotCard cardToPlay;

    public PlayCardPlayerEvent(WhotCard cardToPlay) {
        this.cardToPlay = cardToPlay;
    }

    @Override
    public PlayerEventType getPlayerEventType() {
        return PlayerEventType.PLAY_CARD;
    }

    @Override
    public Optional<WhotCardWithNumberAndShape> cardToPlay() {
        return Optional.of(cardToPlay);
    }

}
