package tapmana.model;

import java.util.List;

public class Deck {
    String owner;
    String name;
    String description;
    int id;
    List<Card> cards;

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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Deck(){

    }

    public Deck (List<Card> cards, String owner, String name, int id) {
        this.cards = cards;
        this.owner = owner;
        this.name = name;
        this.id = id;
    }
}
