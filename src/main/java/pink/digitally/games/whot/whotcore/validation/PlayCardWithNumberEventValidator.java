package pink.digitally.games.whot.whotcore.validation;

import org.apache.commons.math3.util.Pair;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayerEventType;

import java.util.Collections;
import java.util.function.Predicate;

public class PlayCardWithNumberEventValidator extends SimpleValidator<PlayerEvent> {
    public PlayCardWithNumberEventValidator(WhotNumber whotNumber){
        super(Collections.singletonList(
                new Pair<Predicate<PlayerEvent>, String>(
                        playerEvent -> !(PlayerEventType.PLAY_CARD.equals(playerEvent.getPlayerEventType())
                                && playerEvent.cardToPlay().filter(it -> whotNumber.equals(it.getNumber())).isPresent()),
                        String.format("Must play a %s card", whotNumber)
                )));
    }

}
