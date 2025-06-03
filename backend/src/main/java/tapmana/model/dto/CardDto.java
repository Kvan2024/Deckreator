package tapmana.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CardDto {
    @NotEmpty
    String cardName;

    @NotNull
    int multiverseId;

    @NotNull
     int deckCardId;



    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getMultiverseId() {
        return multiverseId;
    }

    public void setMultiverseId(int multiverseId) {
        this.multiverseId = multiverseId;
    }

    public int getDeckCardId() {
        return deckCardId;
    }

    public void setDeckCardId(int deckCardId) {
        this.deckCardId = deckCardId;
    }
}
