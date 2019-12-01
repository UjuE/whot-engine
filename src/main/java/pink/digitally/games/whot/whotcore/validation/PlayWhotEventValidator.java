package pink.digitally.games.whot.whotcore.validation;

import org.apache.commons.math3.util.Pair;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;
import pink.digitally.games.whot.whotcore.events.PlayerEventType;
import pink.digitally.games.whot.whotcore.events.action.AllRulesValidPlayCheck;

import java.util.Collections;
import java.util.function.Predicate;

public class PlayWhotEventValidator extends SimpleValidator<PlayerEvent> {
    public PlayWhotEventValidator(){
        super(Collections.singletonList(
                new Pair<Predicate<PlayerEvent>, String>(
                        playerEvent -> !(PlayerEventType.PLAY_CARD.equals(playerEvent.getPlayerEventType())
                                && playerEvent.cardToPlay()
                                .map(AllRulesValidPlayCheck::isWhotCard).orElse(false)),
                        "Must play 20 Whot card"
                )));
    }

}
