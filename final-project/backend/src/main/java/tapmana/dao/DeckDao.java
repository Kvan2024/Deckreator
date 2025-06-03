package tapmana.dao;

import tapmana.model.Deck;

import java.util.List;

public interface DeckDao {

    List<Deck> getDecks(String owner);

    Deck getDeckById(String owner, int id);

    Deck getDeckByName(String name, String owner);

    Deck createDeck(String owner, String name, String description);

    Deck editDeck(String owner, String name, String description, int id);

    String deleteDeck(int id);

    Deck addCardToDeck(int cardid, int deck, String owner);

    Deck deleteCardFromDeck(int cardid, int deck, String owner);
}
