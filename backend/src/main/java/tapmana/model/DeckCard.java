package tapmana.model;

public class DeckCard {
    private int deckCardId;
    private Card card;

    public int getDeckCardId() {
        return deckCardId;
    }

    public void setDeckCardId(int deckCardId) {
        this.deckCardId = deckCardId;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}