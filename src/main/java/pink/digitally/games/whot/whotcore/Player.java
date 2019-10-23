package pink.digitally.games.whot.whotcore;

import io.vavr.control.Either;
import pink.digitally.games.whot.whotcore.error.ErrorMessage;
import pink.digitally.games.whot.whotcore.error.PlayErrorHandler;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;

import java.util.List;

public interface Player {
    List<WhotCardWithNumberAndShape> getCards();
    void addCard(WhotCardWithNumberAndShape whotCardWithNumberAndShape);
    String getPlayerName();
    void registerMediator(GameMediator mediator);
    GameMediator getMediator();
    PlayErrorHandler getPlayErrorHandler();
    default void play(PlayerEvent playerEvent){
        Either<ErrorMessage, Void> play = getMediator().play(this, playerEvent);
        if(play.isLeft()){
            getPlayErrorHandler().handleError(play.getLeft());
        }
    }

}
