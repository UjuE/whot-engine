package pink.digitally.games.whot.whotcore;

import pink.digitally.games.whot.whotcore.events.PlayerEvent;

import java.util.List;

public interface Player {
    List<WhotCardWithNumberAndShape> getCards();
    void addCard(WhotCardWithNumberAndShape whotCardWithNumberAndShape);
    String getPlayerName();
    void registerMediator(GameMediator mediator);
    GameMediator getMediator();
    default void playCard(PlayerEvent playerEvent){
        //TODO Throw exception when Mediator is not set. Not Null Pointer
        getMediator().play(this, playerEvent);
    }

}
