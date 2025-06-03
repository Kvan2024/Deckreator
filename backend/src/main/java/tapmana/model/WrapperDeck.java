package tapmana.model;

import java.util.List;

public class WrapperDeck {
    String owner;
    String name;
    String description;
    int id;
    List<DeckCard> cards;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DeckCard> getCards() {
        return cards;
    }

    public void setCards(List<DeckCard> cards) {
        this.cards = cards;
    }

    public WrapperDeck(){

    }

    public WrapperDeck (List<DeckCard> cards, String owner, String name, int id) {
        this.cards = cards;
        this.owner = owner;
        this.name = name;
        this.id = id;
    }
}
