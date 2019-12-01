package pink.digitally.games.whot.whotcore.events;

import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotShape;

import java.util.Optional;

import static pink.digitally.games.whot.whotcore.events.PlayerEventType.CHOOSE_SHAPE;

public class ChooseShapePlayerEvent implements PlayerEvent {
    private final WhotShape whotShape;

    public ChooseShapePlayerEvent(WhotShape whotShape) {
        this.whotShape = whotShape;
    }

    @Override
    public PlayerEventType getPlayerEventType() {
        return CHOOSE_SHAPE;
    }

    @Override
    public Optional<WhotCardWithNumberAndShape> cardToPlay() {
        return Optional.empty();
    }

    @Override
    public Optional<WhotShape> chosenShape() {
        return Optional.of(whotShape);
    }
}
