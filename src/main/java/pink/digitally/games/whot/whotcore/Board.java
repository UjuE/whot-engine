package pink.digitally.games.whot.whotcore;

import java.util.Deque;

public interface Board {
    Deque<WhotCardWithNumberAndShape> getPlayPile();

    Deque<WhotCardWithNumberAndShape> getDrawPile();

    void addToPlayPile(WhotCardWithNumberAndShape whotCards);

    void setDrawPile(Deque<WhotCardWithNumberAndShape> whotCards);

    WhotCardWithNumberAndShape takeFromDrawPile();

    WhotCardWithNumberAndShape getTopOfPlayPile();
}
