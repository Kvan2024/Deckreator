package tapmana;

import tapmana.model.Card;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class CardMenu {
    public static final RestClient REST_CLIENT = RestClient.create();
    private static Scanner userInput = new Scanner(System.in);
    private final String BASE_URL = "http://localhost:8080/cards/search?";

    public static void cardMenu() {

        boolean inMenu = true;
        while (inMenu) {
            String userSelection = userInput.nextLine();
            System.out.println("Please enter the number of the search you would like to perform. 'Back' to return to main menu.");
            System.out.println();
            System.out.println("1. Card Name");
            System.out.println("2. Mana Color");
            System.out.println("3. Mana Cost");

            switch (userSelection) {
                case "1":
                    cardNameSearch();
                case "2":
                    cardManaSearch();
                case "3":
                    cardCostSearch();
                case "b":
                case "back":
                    DeckBuilderApp.menu();
                default:
                    System.out.println("Invalid Entry, please try again");
            }
        }
    }

    public static void cardNameSearch() {
        System.out.println("Please enter the name of a card you wish to search for? Type 'Back' to go back");
        String cardName = userInput.nextLine();
        if (cardName.equalsIgnoreCase("Back") || cardName.equalsIgnoreCase("B")) {
            cardMenu();
        }
        String url = "http://localhost:8080/search?name=" + cardName;
        List<Card> searchResults = REST_CLIENT.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Card>>() {});

        for (int x = 0; x < searchResults.size(); x++) {
            System.out.println((x + 1) + ") " + searchResults.get(x).getCardName());
        }
        System.out.println("To view a card's details, please enter the number of the card to view.");
        int cardNumber = -1;
        try {
            cardNumber = userInput.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            userInput.nextLine();
        }

        if (cardNumber == -1) {
            cardNameSearch();
        }
        else {
            cardNumber = searchResults.get(cardNumber - 1).getMultiverseId();
            String cardurl = "http://localhost:8080/search/" + cardNumber;
            Card searchedCard = REST_CLIENT.get()
                    .uri(cardurl)
                    .retrieve()
                    .body(Card.class);
            System.out.println(searchedCard);
        }

        cardNameSearch();
    }

    public static void cardManaSearch() {

    }

    public static void cardCostSearch() {

    }

    public static void pagination(List<Card> list) {
        if (list.size() <= 10){
            for (int x = 0; x < list.size(); x++) {
                System.out.println(list.get(x).getCardName());
            }
        }
        else {


        }
    }
}
