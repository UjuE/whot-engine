package pink.digitally.games.whot.whotcore.playrule;

public enum SpecialCardPlayedEvent {
    PLAYED_PICK_TWO("The next player will pick 2"),
    PLAYED_GENERAL_MARKET("All other players will pick a card"),
    PLAYED_HOLD_ON("The current player gets another turn"),
    PLAYED_SUSPENSION("The next Player will skip a turn"),
    PLAYED_PICK_THREE("The next player will pick 3"),
    PICKED_CARDS("Current player picks cards"),
    CHOSE_NEXT_SHAPE("The next play will be of chosen shape"),
    BLOCKED_PICKING_CARDS("Current player will not pick cards");

    private final String description;
    private String extraDetails;

    SpecialCardPlayedEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public String getExtraDetails(){
        return extraDetails;
    }

    public SpecialCardPlayedEvent witExtraDetail(String extraDetails){
        this.extraDetails = extraDetails;
        return this;
    }
}
