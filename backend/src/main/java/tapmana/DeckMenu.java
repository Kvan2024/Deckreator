package tapmana;

import tapmana.model.User;

import java.util.Scanner;

public class DeckMenu {

    private static final Scanner userInput = new Scanner(System.in);

    public static User deckLogin(){
        User newUser = new User();
        boolean notLoggedIn = true;
        while (notLoggedIn) {
            System.out.println("Please enter your credentials to access the Decks menu.");
            System.out.println("User ID: ");
            String userID = userInput.nextLine();
            System.out.println("Password: ");
            String password = userInput.next();
        }
        return newUser;
    }
    public static void deckMenu() {

        boolean inMenu = true;
        while (inMenu) {
            System.out.println("What would you like to do with decks today? 'Back' to return to main menu.");
            System.out.println("1. Create Deck");
            System.out.println("2. View Deck");
            System.out.println("3. Edit Deck");
            System.out.println("4. Delete Deck");

            String selection = userInput.nextLine();

            switch (selection.toLowerCase()) {
                case "1":
                    createDeck();
                    break;
                case "2":
                    deckList("view");
                    break;
                case "3":
                    deckList("edit");
                    break;
                case "4":
                    deckList("delete");
                    break;
                case "b":
                case "back":
                    DeckBuilderApp.menu();
                default:
                    System.out.println("Invalid selection, please try again.");
                    deckMenu();
                    break;
            }
        }
    }

    public static void createDeck(){

    }

    public static void deckList(String intent){

    }
}