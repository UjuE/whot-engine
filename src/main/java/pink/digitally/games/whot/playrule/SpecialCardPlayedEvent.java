package pink.digitally.games.whot.playrule;

public enum SpecialCardPlayedEvent {
    PICK_TWO("The next player will pick 2"),
    GENERAL_MARKET("All other players will pick a card"),
    HOLD_ON("The current player gets another turn"),
    SUSPENSION("The next Player will skip a turn");

    private final String description;

    SpecialCardPlayedEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}