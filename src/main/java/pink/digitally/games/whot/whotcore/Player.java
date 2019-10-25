package pink.digitally.games.whot.whotcore;

import pink.digitally.games.whot.whotcore.events.PlayerEvent;

import java.util.List;

public interface Player {
    List<WhotCardWithNumberAndShape> getCards();
    void addCard(WhotCardWithNumberAndShape whotCardWithNumberAndShape);
    String getPlayerName();
    void registerMediator(GameMediator mediator);
    GameMediator getMediator();
    default void play(PlayerEvent playerEvent){
        getMediator().play(this, playerEvent);
    }

}
