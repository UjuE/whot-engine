package pink.digitally.games.whot.whotcore.validation;

import org.apache.commons.math3.util.Pair;
import pink.digitally.games.whot.whotcore.WhotNumber;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayerEventType;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

import static java.util.Arrays.asList;

public class PlayBlockerCardEventValidator extends SimpleValidator<PlayerEvent> {
    private static final Collection<WhotNumber> BLOCKER_CARDS = asList(
            WhotNumber.TWO,
            WhotNumber.FIVE,
            WhotNumber.TWENTY
    );
    public PlayBlockerCardEventValidator(){
        super(Collections.singletonList(
                new Pair<Predicate<PlayerEvent>, String>(
                        playerEvent -> !(PlayerEventType.PLAY_CARD.equals(playerEvent.getPlayerEventType())
                                && playerEvent.cardToPlay().filter(it -> BLOCKER_CARDS.contains(it.getNumber())).isPresent()),
                        String.format("Must play one of the following cards %s", BLOCKER_CARDS)
                )));
    }

}
