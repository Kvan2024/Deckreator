package tapmana.dao;

import tapmana.model.Card;

import java.util.List;

public interface CardDao {

    List<Card> getCards();

    List<Card> getCardsByName(String name);

    List<Card> getCardsByColor(String color);

    List<Card> getCardsByCost(int cost);

    List<Card> getCardsByType(String type);

    List<Card> getCardsByKeyWord(String keyWord);

    List<Card> getCardsByPower(int power);

    List<Card> getCardsByToughness(int toughness);


    Card getCardById(int id);



}
