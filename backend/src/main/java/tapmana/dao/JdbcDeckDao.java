package tapmana.dao;

import tapmana.exception.DaoException;
import tapmana.model.Card;
import tapmana.model.Deck;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class JdbcDeckDao implements DeckDao {
    private static JdbcTemplate jdbcTemplate;

    public JdbcDeckDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Deck> getDecks(String owner) {
        List<Deck> decks = new ArrayList<>();
        String sql = "SELECT * FROM decks WHERE owner LIKE ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, owner);
            while (results.next()) {
                decks.add(mapDataRowToDeck(results));
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return decks;
    }

    @Override
    public  Deck getDeckByName(String name, String owner) {
        List<Card> deckCards = new ArrayList<>();
        Deck tempDeck = new Deck();
        tempDeck.setName(name);
        tempDeck.setName(owner);
        String sql = "SELECT * FROM decks WHERE name = ? AND owner = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name, owner);
            while (results.next()) {
                deckCards.add(JdbcCardDao.mapDataRowToCard(results));
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        tempDeck.setCards(deckCards);

        return tempDeck;
    }

    @Override
    public Deck getDeckById(String owner, int id) {
        Deck deck = new Deck();
        List<Card> cardList = new ArrayList<>();
        String sql = "SELECT * FROM decks d JOIN deck_cards dc ON dc.deck_number = d.deck_number WHERE owner = ? AND dc.deck_number = ?;";
        String cardsql = "SELECT * FROM cards c JOIN deck_cards dc ON c.multiverseid = dc.card  JOIN decks d ON d.deck_number = dc.deck_number WHERE d.owner = ? AND dc.deck_number = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, owner, id);
            while (results.next()) {
                deck = JdbcDeckDao.mapDataRowToDeck(results);
            }
            SqlRowSet cardResults = jdbcTemplate.queryForRowSet(cardsql, owner, id);
            while (cardResults.next()) {
                cardList.add(JdbcCardDao.mapDataRowToCard(cardResults));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        deck.setCards(cardList);

        return deck;
    }

    @Override
    public Deck createDeck(String owner, String name, String description) {
        int deckId;
        String sql = "INSERT INTO decks (deck_number, owner, name, description)" +
                "VALUES (DEFAULT, ?, ?, ?) RETURNING deck_number;";

        try {
            deckId = jdbcTemplate.queryForObject(sql, int.class, owner, name, description);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getDeckById(owner, deckId);
    }

    @Override
    public Deck editDeck(String owner, String name, String description, int id) {
        Deck updateddeck = null;
        String sql = "UPDATE decks " +
                "SET name = ?, description = ? WHERE deck_number = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, name, description, id);

            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updateddeck = getDeckById(owner, id);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updateddeck;
    }

    @Override
    public String deleteDeck(int id) {
        int rowsAffected;
        String sql = "DELETE FROM decks WHERE deck_number = ?;";
        String joinSql = "DELETE FROM deck_cards WHERE deck_number = ?;";
        try {
           rowsAffected =  jdbcTemplate.update(sql, id);
            jdbcTemplate.update(joinSql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        if (rowsAffected == 1) {
            return "Deck deleted successfully!";
        }
        if (rowsAffected == 0) {
            return "Deck not deleted, perhaps you messed up?";
        }
        return "Nothing was done";
    }

    @Override
    public Deck addCardToDeck(int cardid, int deck, String owner) {

        String sql = "INSERT INTO deck_cards (card, deck_number) " +
                "VALUES (?, ?);";
        try {
            jdbcTemplate.update(sql, cardid, deck);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getDeckById(owner, deck);
    }

    @Override
    public Deck deleteCardFromDeck(int cardid, int deck, String owner) {

        String sql = "DELETE FROM deck_cards WHERE card = ?;";
        try {
            jdbcTemplate.update(sql, cardid);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getDeckById(owner, deck);
    }

    public static Deck mapDataRowToDeck(SqlRowSet results) {
        Deck deck = new Deck();

        deck.setDescription(results.getString("description"));
        deck.setId(results.getInt("deck_number"));
        deck.setOwner(results.getString("owner"));
        deck.setName(results.getString("name"));

        return deck;
    }
}
