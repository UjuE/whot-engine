package pink.digitally.games.whot.whotcore.validation;

import org.apache.commons.math3.util.Pair;
import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.card.WhotShape;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayerEventType;

import java.util.Collections;
import java.util.function.Predicate;

public class PlayShapeEventValidator extends SimpleValidator<PlayerEvent> {
    public PlayShapeEventValidator(WhotShape whotShape){
        super(Collections.singletonList(
                new Pair<Predicate<PlayerEvent>, String>(
                        playerEvent -> !(PlayerEventType.PLAY_CARD.equals(playerEvent.getPlayerEventType())
                                && playerEvent.cardToPlay()
                                .map(WhotCardWithNumberAndShape::getShape)
                                .map(it -> it.equals(whotShape)).orElse(false)),
                        String.format("Must play card with shape'%s'", whotShape)
                )));
    }

}
