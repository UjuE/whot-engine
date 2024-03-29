package pink.digitally.games.whot.whotcore.events;

import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotShape;

import java.util.Optional;

public class PlayCardPlayerEvent implements PlayerEvent {
    private final WhotCardWithNumberAndShape cardToPlay;

    public PlayCardPlayerEvent(WhotCardWithNumberAndShape cardToPlay) {
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

    @Override
    public Optional<WhotShape> chosenShape() {
        return Optional.empty();
    }


}
