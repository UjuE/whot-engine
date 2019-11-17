package pink.digitally.games.whot.whotcore.playrule;

public enum SpecialCardPlayedEvent {
    PICK_TWO("The next player will pick 2"),
    GENERAL_MARKET("All other players will pick a card"),
    HOLD_ON("The current player gets another turn"),
    SUSPENSION("The next Player will skip a turn"),
    PICK_THREE("The next player will pick 3"),
    PICK_CARDS("Current player picks cards");

    private final String description;

    SpecialCardPlayedEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
