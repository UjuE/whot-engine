package pink.digitally.games.whot.whotcore;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String playerName;
    private List<WhotCardWithNumberAndShape> whotCards;

    public Player(String playerName) {
        this.playerName = playerName;
        this.whotCards = new ArrayList<>();
    }

    public List<WhotCardWithNumberAndShape> getCards() {
        return whotCards;
    }

    public void addCard(WhotCardWithNumberAndShape whotCardWithNumberAndShape){
        whotCards.add(whotCardWithNumberAndShape);
    }

    public String getPlayerName() {
        return playerName;
    }
}
