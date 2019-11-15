package pink.digitally.games.whot.whotcore.validation;

import org.apache.commons.math3.util.Pair;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayerEventType;

import java.util.Collections;
import java.util.function.Predicate;

public class TakeCardEventValidator extends SimpleValidator<PlayerEvent> {
    public TakeCardEventValidator(){
        super(Collections.singletonList(
                new Pair<Predicate<PlayerEvent>, String>(
                        playerEvent -> !PlayerEventType.TAKE_CARD.equals(playerEvent.getPlayerEventType()), "Must Draw Card")
        ));
    }
}
