package pink.digitally.games.whot.whotcore;

import pink.digitally.games.whot.whotcore.card.WhotCardWithNumberAndShape;
import pink.digitally.games.whot.whotcore.events.PlayerEvent;

import java.util.List;

public interface Player {

    /**
     *
     * @return players card
     */
    List<WhotCardWithNumberAndShape> getCards();

    /**
     *
     * @param whotCardWithNumberAndShape add additional card
     */
    void addCard(WhotCardWithNumberAndShape whotCardWithNumberAndShape);

    /**
     *
     * @return player name
     */
    String getPlayerName();

    /**
     *
     * @param mediator GameMediator
     */
    void registerMediator(GameMediator mediator);

    /**
     *
     * @return GameMediator
     */
    GameMediator getMediator();

    PlayerEvent playEventFunction(Board board);

    /**
     * play is to indicate to a robo-player to player
     * @param board Board
     */
    default void play(Board board){
        play(playEventFunction(board));
    }

    /**
     *
     * @param playerEvent The event the player makes. The player either takes a card or plays a card
     */
    default void play(PlayerEvent playerEvent){
        getMediator().play(this, playerEvent);
    }

    default boolean isHumanPlayer(){
        return true;
    }

}
