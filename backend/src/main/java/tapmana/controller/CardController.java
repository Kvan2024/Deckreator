package tapmana.controller;

import tapmana.dao.CardDao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tapmana.model.Card;

import java.util.List;

@RestController
@RequestMapping(path = "/cards")
public class CardController {
    private CardDao cardDao;
    public CardController(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Card> getAllCards() {
        return cardDao.getCards();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public List<Card> getCardsBySearch(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String type,
            @RequestParam(required = false, defaultValue = "") String color,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "-1") int cost,
            @RequestParam(required = false, defaultValue = "-1") int power,
            @RequestParam(required = false, defaultValue = "-1") int toughness) {
            if (!name.isEmpty()) {
                System.out.println("Searching for card by name");
                return cardDao.getCardsByName(name);
            }
            if (!type.isEmpty()) {
                System.out.println("Searching for card by type");
                return cardDao.getCardsByType(type);
            }
            if (!color.isEmpty()) {
                System.out.println("Searching for card by color");
                return cardDao.getCardsByColor(color);
            }
            if (!keyword.isEmpty()) {
                System.out.println("Searching for card by key");
                return cardDao.getCardsByKeyWord(keyword);
            }
            if (cost >= 0) {
                System.out.println("Searching for card by cost");
                return cardDao.getCardsByCost(cost);
            }
            if (power >= 0) {
                System.out.println("Searching for card by power");
                return cardDao.getCardsByPower(power);
            }
        System.out.println("Searching for card by tough");
                return cardDao.getCardsByToughness(toughness);

    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/search/{multiverseId}", method = RequestMethod.GET)
    public Card getCardsById(
            @PathVariable int multiverseId){
        Card card = cardDao.getCardById(multiverseId);
        if (card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return card;
    }

    @GetMapping("/test")
    public String testRoute() {
        System.out.println("Test endpoint hit");
        return "OK";
    }

}
