package pink.digitally.games.whot.whotcore.validation;

import org.apache.commons.math3.util.Pair;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayerEventType;

import java.util.Collections;
import java.util.function.Predicate;

public class ChooseShapeValidation  extends SimpleValidator<PlayerEvent> {
    public ChooseShapeValidation() {
        super(Collections.singletonList(
                new Pair<Predicate<PlayerEvent>, String>(
                        playerEvent -> !(PlayerEventType.CHOOSE_SHAPE.equals(playerEvent.getPlayerEventType())),
                        "Player must choose next shape to be played"
                )));
    }
}
