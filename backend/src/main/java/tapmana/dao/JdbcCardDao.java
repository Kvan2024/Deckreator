package tapmana.dao;

import tapmana.exception.DaoException;
import tapmana.model.Card;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
@Repository
public class JdbcCardDao implements CardDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcCardDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<Card> getCards() {
        List<Card> cardList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM cards";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
            while (results.next()) {
                Card card = mapDataRowToCard(results);
                cardList.add(card);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return cardList;
    }
    @Override
    public List<Card> getCardsByName(String name) {
        List<Card> cardList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM cards WHERE name ILIKE ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, "%" + name + "%");
            while (results.next()) {
                Card card = mapDataRowToCard(results);
                cardList.add(card);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return cardList;
    }

    @Override
    public List<Card> getCardsByColor(String color) {
        List<Card> cardList = new ArrayList<>();
        String search = "";

        switch (color.toLowerCase()) {
            case "w":
            case "white":  search = "W";
                break;
            case "b":
            case "blue":  search = "U";
                break;
            case "r":
            case "red":  search = "R";
                break;
            case "bl":
            case "black":  search = "B";
                break;
            case "g":
            case "green":  search = "G";
                break;
            default:
                throw new IllegalArgumentException(color + "Is not a valid option. Please select Green, Red, Blue, Black, White or None.");
        }
        String sqlQuery = "SELECT * FROM cards WHERE color_identity ILIKE ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, "%" + search + "%");
            while (results.next()) {
                Card card = mapDataRowToCard(results);
                cardList.add(card);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException(color + "Is not a valid option. Please select Green, Red, Blue, Black, White or None.", e);
        }
        return cardList;
    }

    @Override
    public List<Card> getCardsByCost(int cost) {
        List<Card> cardList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM cards WHERE mana_value = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, cost);
            while (results.next()) {
                Card card = mapDataRowToCard(results);
                cardList.add(card);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return cardList;
    }

    @Override
    public List<Card> getCardsByType(String type) {
        List<Card> cardList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM cards WHERE type ILIKE ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, "%" + type + "%");
            while (results.next()) {
                Card card = mapDataRowToCard(results);
                cardList.add(card);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return cardList;
    }


    @Override
    public List<Card> getCardsByKeyWord(String keyWords) {
        List<Card> cardList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM cards WHERE key_words ILIKE ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, "%" + keyWords + "%");
            while (results.next()) {
                Card card = mapDataRowToCard(results);
                cardList.add(card);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return cardList;
    }

    @Override
    public List<Card> getCardsByPower(int power) {
        List<Card> cardList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM cards WHERE power = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, power);
            while (results.next()) {
                Card card = mapDataRowToCard(results);
                cardList.add(card);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return cardList;
    }

    @Override
    public List<Card> getCardsByToughness(int toughness) {
        List<Card> cardList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM cards WHERE toughness = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, toughness);
            while (results.next()) {
                Card card = mapDataRowToCard(results);
                cardList.add(card);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return cardList;
    }
    @Override
    public Card getCardById(int multiverseid) {
        Card card = new Card();
        String sqlQuery = "SELECT * FROM cards WHERE multiverseid = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, multiverseid);

            if (results.next()) { // Move the cursor to the first row
                return mapDataRowToCard(results); // Map the result to a Card object
            } else {
                throw new DaoException("No card found with multiverseId: " + multiverseid);
            }
        }
         catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    public static Card mapDataRowToCard(SqlRowSet results) {
        Card card = new Card();

        card.setColorIdent(results.getString("color_identity"));
        card.setKeyWords(results.getString("key_words"));
        card.setDetailedManaCost(results.getString("mana_value"));
        card.setCardName(results.getString("name"));
        card.setPower(results.getInt("power"));
        card.setToughness(results.getInt("toughness"));
        card.setType(results.getString("type"));
        card.setMultiverseId(results.getInt("multiverseid"));

        return card;
    }
}
