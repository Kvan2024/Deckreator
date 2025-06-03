package tapmana;
import java.util.Scanner;

public class DeckBuilderApp {
    private static final Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        DeckBuilderApp.run();
    }

    public static void run() {
        System.out.println("Welcome to Millhouse Manastorm's Splendiferous Most Magnificent Totally Completely Finished Deck Creator App For Magic The Gathering.");

        DeckBuilderApp.menu();

    }

    public static void menu(){
        System.out.println("Please select the number for the action you'd like to take. 'Exit' to close program.");

        System.out.println("1. Login to work with decks");
        System.out.println("2. Card Search");

        String selection = userInput.nextLine().toLowerCase();
        switch (selection.toLowerCase()) {
            case "1":
                DeckMenu.deckMenu();
                break;
            case "2":
                CardMenu.cardMenu();
                break;
            case "e":
            case "exit":
                System.exit(0);
                break;
            default:
                DeckBuilderApp.menu();
        }
    }

}