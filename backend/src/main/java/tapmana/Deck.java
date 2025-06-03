package tapmana;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Deck {

    private static final Scanner userInput = new Scanner(System.in);
    private static final File deckList = new File("design/deckListFile.txt");
    static final File tempDeckTxt = new File("design/tempDeck.txt");
    private static String deckName;

    public Deck() {

    }
    public static void Deck(String deckName){

    }
    public static void deckMenu() {
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
            case "5":

                break;
            case "b":
            case "back":

            default:
                System.out.println("Invalid selection, please try again.");
                deckMenu();
                break;
        }
    }

    public static void deckList(String intent) {
        List<String> listOfDecks = new ArrayList<>();
        try(BufferedReader deckFile = new BufferedReader(new FileReader(deckList))){
            String nextLine;
            int x = 1;
            while((nextLine = deckFile.readLine()) != null){
                System.out.println(x + ") " + nextLine);
                listOfDecks.add(nextLine);
                x++;
            }
            System.out.println();
        }
        catch(IOException e){
            System.out.println("Deck file not found. This is likely because you haven't created any yet." + e.getMessage());
            deckMenu();
        }
        switch (intent) {

            case "view":
                System.out.println("Please enter the number of the deck you wish to view?");
                System.out.println("'Back' to go to previous menu.");
                String viewSelection = userInput.nextLine();
                if (viewSelection.equalsIgnoreCase("b") || viewSelection.equalsIgnoreCase("back"))
                    deckMenu();
                if (viewSelection.matches(".*\\d.*")) {
                    viewSelection = listOfDecks.get(Integer.parseInt(viewSelection) - 1);
                    viewDeck(viewSelection, "view");
                }
                else {
                    System.out.println("Invalid entry, please try again");
                    deckList("view)");
                }
                break;
            case "edit":
                System.out.println("Please enter the number of the deck you would like to edit.");
                System.out.println("'Back' to go to previous menu.");
                String editSelection = userInput.nextLine();
                if (editSelection.equalsIgnoreCase("b") || editSelection.equalsIgnoreCase("back"))
                    deckMenu();
                if (editSelection.matches(".*\\d.*")) {
                    editSelection = listOfDecks.get(Integer.parseInt(editSelection) - 1);
                    boolean cardAdded = false;
                    while (!cardAdded) {
                        try {
                            editDeck(editSelection);
                            cardAdded = true;
                        } catch (TooManyCardsException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                else {
                    System.out.println("Invalid entry, please try again");
                    deckList("edit");
                }
                break;
            case "delete":
                System.out.println("Which deck would you like to delete?");
                System.out.println("'Back' to go to previous menu.");
                String delSelection = userInput.nextLine();
                if (delSelection.equalsIgnoreCase("b") || delSelection.equalsIgnoreCase("back"))
                    deckMenu();
                if (delSelection.matches(".*\\d.*")) {
                    delSelection = listOfDecks.get(Integer.parseInt(delSelection) - 1);
                }
                else {
                    System.out.println("Invalid entry, please try again");
                    deckList("delete)");
                }
                System.out.println("Are you sure you wish to delete this deck? This action can not be undone.");
                String userConfirmation = userInput.nextLine();
                if (userConfirmation.equalsIgnoreCase("y") || userConfirmation.equalsIgnoreCase("yes") )
                    deleteDeck(delSelection);
                break;
            case "b":
            case "back":
                deckMenu();
                break;
            default:
                System.out.println("Invalid entry, please try again");
                deckList(intent);
        }
        deckList(intent);

    }
    public static void createDeck() {

        try {

            if (!deckList.exists()) {
                deckList.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("No Deck File found and unable to create one" + e.getMessage());
        }
        String newDeckName = null;
        try(BufferedReader deckFile = new BufferedReader(new FileReader(deckList))) {
            int x = 1;
            System.out.println("What would you like to name this deck? 'Back' to go back.");
            boolean naming = true;
            String deckFileLine = deckFile.readLine();
            String fileName;
            while (naming) {
                newDeckName = userInput.nextLine();
                if (newDeckName.equalsIgnoreCase("b") || newDeckName.equalsIgnoreCase("back")) {
                    deckMenu();
                }
                if (newDeckName.isBlank()) {
                    System.out.println("Names can not be blank, please enter a name. 'Back' to go back.");
                }
                else {
                    if (deckFileLine == null || !deckFileLine.equalsIgnoreCase(newDeckName)) {
                        fileName = "design/" + newDeckName.toLowerCase().replaceAll("\\s+", "") + ".txt";
                        try (PrintWriter addDeck = new PrintWriter(new FileOutputStream(deckList, true)); PrintWriter deck = new PrintWriter(fileName)) {
                            addDeck.println(newDeckName);
                            System.out.println("Deck " + newDeckName + " was created successfully.");
                            System.out.println();
                        } catch (Exception e) {
                            System.out.println("Error with the deck file" + e.getMessage());
                            deckMenu();
                        }
                        naming = false;
                    } else {
                        System.out.println("Invalid deck name. This name already exists. Please try another name.");
                    }
                }
            }
        }
        catch(IOException e) {
            System.out.println("File not found or something" + e.getMessage());
        }
        System.out.println("Would you like to edit this deck at this time?(Y/N)");
            String editInputSelection = userInput.nextLine();
            if (editInputSelection.equalsIgnoreCase("Y")) {
                try {
                    editDeck(newDeckName.toLowerCase());
                }
                catch (TooManyCardsException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (editInputSelection.equalsIgnoreCase("N")) {
                deckMenu();
            }

    }
    public static void viewDeck(String deckName, String intent){
        File deckFile = new File("design/" + deckName.toLowerCase() + ".txt");
        int cardCounter = 0;
        int currentFirstCard = 1;
        int currentLastCard = 10;
        List<Card> listOfDeckObjects = new ArrayList<>();
        List<String> listOfDeckCards = new ArrayList<>();
        try(BufferedReader readDeckFile = new BufferedReader(new FileReader(deckFile))){
            String cardLine;
            while((cardLine = readDeckFile.readLine()) != null){

                System.out.println(cardLine);
                cardCounter++;
            }
        }
        catch (IOException e) {
            System.out.println("Deck file not found" + e.getMessage());
        }
        currentLastCard = listOfDeckCards.size();
        deckViewCycle(cardCounter, currentFirstCard, currentLastCard, listOfDeckCards, "view");

        boolean viewDeck = true;
        while (viewDeck) {
            String pageChoice = userInput.nextLine();

            switch (pageChoice.toLowerCase()) {
                case "previous":
                    if ((currentFirstCard - 10) < 1) {
                        currentFirstCard = 1;
                        currentLastCard = (currentFirstCard + 9);
                        deckViewCycle(cardCounter, currentFirstCard, currentLastCard, listOfDeckCards, "view");
                    }
                    else {
                        currentFirstCard -= 10;
                        currentLastCard = (currentFirstCard + 9);
                        deckViewCycle(cardCounter, currentFirstCard, currentLastCard, listOfDeckCards, "view");
                    }
                    break;
                case "next":
                    if ((currentLastCard + 10) > cardCounter) {
                        currentLastCard = cardCounter;
                        currentFirstCard += 10;
                        deckViewCycle(cardCounter, currentFirstCard, currentLastCard, listOfDeckCards, "view");
                    }
                    else {
                        currentFirstCard += 10;
                        currentLastCard += 10;
                        deckViewCycle(cardCounter, currentFirstCard, currentLastCard, listOfDeckCards, "view");
                    }
                    break;
                case "e":
                case "exit":
                    viewDeck = false;
                    deckList("view");
                    break;
            }
        }
        deckList("view");


    }

    public static void editDeck(String deckName) throws TooManyCardsException{

        // TODO: bubble sort for cards

        File deckFile = new File("design/" + deckName.toLowerCase() + ".txt");
        boolean editDeck = true;
        int cardCounter = 0;
        int currentFirstCard = 1;
        int currentLastCard = 10;
        List<String> listOfDeckCards = new ArrayList<>();
        try (BufferedReader readDeckFile = new BufferedReader(new FileReader(deckFile))) {
            String cardLine;
            while ((cardLine = readDeckFile.readLine()) != null) {
                listOfDeckCards.add(cardLine);
                cardCounter++;
            }
        } catch (IOException e) {
            System.out.println("Deck file not found" + e.getMessage());
        }

        while (editDeck) {
            currentLastCard = listOfDeckCards.size();
            deckViewCycle(cardCounter, currentFirstCard, currentLastCard, listOfDeckCards, "edit");
            String userChoice = userInput.nextLine().toLowerCase();

            if (userChoice.matches(".*\\d.*")) {
                int cardChosen = (Integer.parseInt(userChoice) - 1);
                if (cardChosen >= listOfDeckCards.size()) {
                    System.out.println("Invalid card number, please try again");
                } else {
                    String addOrRemCard = deckViewCard(cardChosen, listOfDeckCards);
                    if (addOrRemCard.equalsIgnoreCase("remove") || addOrRemCard.equalsIgnoreCase("r")) {
                        listOfDeckCards.remove(cardChosen);
                    }
                    else if (addOrRemCard.equalsIgnoreCase("add") || addOrRemCard.equalsIgnoreCase("a")) {
                        int numberOfSameCard = Collections.frequency(listOfDeckCards, listOfDeckCards.get(cardChosen));
                        if (numberOfSameCard >= 4) {
                            throw new TooManyCardsException();
                        }
                        else {
                            listOfDeckCards.add(listOfDeckCards.get(cardChosen));
                        }
                        editDeck(deckName);

                    }
                }
            } else {

                switch (userChoice) {

                    case "p":
                    case "previous":
                        currentFirstCard -= 10;
                        currentLastCard -= 10;
                        break;
                    case "n":
                    case "next":
                        currentFirstCard += 10;
                        currentLastCard += 10;
                        break;
                    case "a":
                    case "add":
                        System.out.println("Please enter the name of the card you wish to add");
                        System.out.println("'Back' to return to previous menu.");
                        String cardSearch = userInput.nextLine();
                        if (cardSearch.equalsIgnoreCase("b") || cardSearch.equalsIgnoreCase("back")) {
                            editDeck(deckName);
                        }
                        else {
                            List<Card> searchedCards = Card.cardSearch("deck", cardSearch.toLowerCase());
                            Card cardToAdd = deckCardSearchViewer(searchedCards);
                            int numberOfSameCard = Collections.frequency(listOfDeckCards, cardToAdd);
                            if (numberOfSameCard >= 4) {
                                throw new TooManyCardsException();
                            }
                            listOfDeckCards.add(cardToAdd.getCardName() + cardToAdd.getCardColor() + cardToAdd.getCardType() + cardToAdd.getDetailedManaCost()
                                    + cardToAdd.getTotalManaCost() + cardToAdd.getCardText() + cardToAdd.getFlavorText() + cardToAdd.getAttackPower()
                                    + cardToAdd.getDefensePower());
                        }
                        break;
                    case "r":
                    case "remove":
                        System.out.println("Please enter the card number of the card you wish to remove.");
                        break;
                    case "e":
                    case "exit":
                        try (PrintWriter savedDeck = new PrintWriter(new FileOutputStream(deckFile))) {

                            for (int x = 0; x < listOfDeckCards.size(); x++) {
                                savedDeck.println(listOfDeckCards.get(x));
                            }
                            System.out.println("Deck changes saved successfully.");
                        }
                        catch (FileNotFoundException e) {
                            System.out.println("Unable to save deck, file not found" + e.getMessage());
                        }
                        editDeck = false;
                        deckList("edit");
                        break;
                    case "s":
                    case "save":
                        try (PrintWriter savedDeck = new PrintWriter(new FileOutputStream(deckFile, true))) {

                            for (int x = 0; x < listOfDeckCards.size(); x++) {
                                savedDeck.println(listOfDeckCards.get(x));
                            }
                            System.out.println("Deck saved successfully.");
                        }
                        catch (FileNotFoundException e) {
                            System.out.println("Unable to save deck, file not found" + e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("That was not a valid entry, please try again");
                }
            }
        }
    }



    public static void deleteDeck(String deckName){
        File deckToDelete = new File("design/" + deckName + ".txt");
        if (deckToDelete.delete()) {
            System.out.println("Deck deleted successfully. Poof, it's gone!");
        }
        else {
            System.out.println("Unable to delete Deck. Stubborn, isn't it?");
        }
        try(BufferedReader deckFile = new BufferedReader(new FileReader(deckList));
            PrintWriter tempDeck = new PrintWriter(tempDeckTxt)){
            String nextLine;
            int x = 1;
            while((nextLine = deckFile.readLine()) != null){
                if (nextLine.equalsIgnoreCase(deckName)) {
                    continue;
                }
                else {
                    tempDeck.println(nextLine);
                }
            }
            System.out.println();
        }
        catch(IOException e){
            System.out.println("Something went wrong with the deck file. Probably should call someone about that." + e.getMessage());
        }

        try(BufferedReader tempFile = new BufferedReader(new FileReader(tempDeckTxt)); PrintWriter currentDeckList = new PrintWriter(deckList)){
            String nextLine;
            while((nextLine = tempFile.readLine()) != null){
                currentDeckList.println(nextLine);
            }
            System.out.println();
        }
        catch(IOException e){
            System.out.println("Unable to update deck file. Uh oh." + e.getMessage());
        }
        tempDeckTxt.delete();
        deckMenu();

    }
    public static void deckViewCycle(int cardCounter, int currentFirstCard, int currentLastCard, List listOfDeckCards, String intent) {
        List<Card> tempList = new ArrayList<>();
        for (int x = 0; x < tempList.size(); x++) {
        }
        switch (intent) {
            case "view":
                if (listOfDeckCards.isEmpty()) {
                    System.out.println("End of this deck.");
                    System.out.println("Type 'Exit' to return to deck list.");
                    break;
                }
                for (int x = currentFirstCard; x <= currentLastCard; x++) {
                    Card c = (Card) listOfDeckCards.get(x - 1);
                    System.out.printf(x + ") " + "%-35s\t%-15s\t%-30s\t%-3s\t%-3s%n", c.getCardName(), c.getDetailedManaCost(), c.getCardType(),
                            c.getAttackPower(), c.getDefensePower());
                }
                if (currentFirstCard == 1 && cardCounter < 10) {
                    System.out.println("End of this deck.");
                    System.out.println("Type 'Exit' to return to deck list.");
                } else if (currentFirstCard == 1 && cardCounter > 10) {
                    System.out.println("Type 'Next' to cycle through the cards in this deck. Type 'Exit' to return to deck list.");
                } else if (currentFirstCard > 10 && currentLastCard < cardCounter) {
                    System.out.println("Type 'Next' or 'Previous' to cycle through the cards in this deck. Type 'Exit' to return to deck list.");
                } else if (currentLastCard == cardCounter) {
                    System.out.println("End of this deck.");
                    System.out.println("Type 'Previous' to cycle through the cards in this deck. Type 'Exit' to return to deck list.");
                }
                break;
            case "edit":
                if (listOfDeckCards.isEmpty()) {
                    System.out.println("End of this deck.");
                    System.out.println("To add or delete a card, type it's number. To add a new card, type 'Add'");
                    System.out.println("Type 'Exit' to return to deck list.");
                } else {
                    for (int x = currentFirstCard; x <= currentLastCard; x++) {
                        System.out.println(x + ") " + listOfDeckCards.get(x - 1));
                    }

                    if (currentFirstCard == 1 && cardCounter < 10) {
                        System.out.println("End of this deck.");
                        System.out.println("To add or delete a card, type it's number. To add a new card, type 'Add'");
                        System.out.println("Type 'Save' to save changes, 'Exit' to return to deck list.");
                    } else if (currentFirstCard == 1 && cardCounter > 10) {
                        System.out.println("To add or delete a card, type it's number. To add a new card, type 'Add'");
                        System.out.println("Type 'Next' to cycle through the cards in this deck. Type 'Save' to save changes, 'Exit' to return to deck list.");
                    } else if (currentFirstCard > 10 || currentLastCard < cardCounter) {
                        System.out.println("To add or delete a card, type it's number. To add a new card, type 'Add'");
                        System.out.println("Type 'Next' or 'Previous' to cycle through the cards in this deck. Type 'Save' to save changes, 'Exit' to return to deck list.");
                    } else {
                        System.out.println("End of this deck.");
                        System.out.println("To add or delete a card, type it's number. To add a new card, type 'Add'");
                        System.out.println("Type 'Previous' to cycle through the cards in this deck. Type 'Save' to save changes, 'Exit' to return to deck list.");
                    }
                    break;
                }
        }
    }


    public static Card deckCardSearchViewer(List cardList) {
        int cardChoice = 0;
        int cardCounter = cardList.size();
        int currentFirstCard = 1;
        int currentLastCard = 10;
        boolean viewCards = true;
        while(viewCards){
            deckCardViewCycle(cardCounter, currentFirstCard, currentLastCard, cardList);

            String pageChoice = userInput.nextLine().toLowerCase();

            if (pageChoice.matches(".*\\d.*")) {
                cardChoice = Integer.parseInt(pageChoice);
                return (Card) cardList.get(cardChoice);
            }
               // String response = userInput.nextLine().toLowerCase();
            else if (pageChoice.equals("exit")) {
                    viewCards = false;
            }
            else {

                switch (pageChoice) {
                    case "p":
                    case "previous":
                        if ((currentFirstCard - 10) < 1) {
                            currentFirstCard = 1;
                            currentLastCard = (currentFirstCard + 9);
                        } else {
                            currentFirstCard -= 10;
                            currentLastCard = (currentFirstCard + 9);
                        }
                        break;
                    case "n":
                    case "next":
                        if ((currentLastCard + 10) > cardCounter) {
                            currentLastCard = cardCounter;
                            currentFirstCard += 10;
                        } else {
                            currentFirstCard += 10;
                            currentLastCard += 10;
                        }
                        break;
                    case "e":
                    case "exit":
                        viewCards = false;
                        break;
                    default:
                        if (pageChoice.equalsIgnoreCase("back")) {
                            deckCardViewCycle(cardCounter, currentFirstCard, currentLastCard, cardList);
                        }
                        else {
                            System.out.println("Invalid Entry, sending you back to the card list");
                            deckCardViewCycle(cardCounter, currentFirstCard, currentLastCard, cardList);

                        }

                }
            }
        }
       // cardMenu();
        return (Card) cardList.get(cardChoice);
    }






    public static void deckCardViewCycle(int cardCounter, int currentFirstCard, int currentLastCard, List listOfDeckCards) {
        System.out.println("   Name                                 " + "\t" + "Cost        " + "\t" + "Type                        " + "\t" + "Att" + "\t" + "Def");
        for (int x = currentFirstCard; x <= currentLastCard; x++) {
            Card c = (Card) listOfDeckCards.get(x - 1);
            System.out.printf(x + ") " + "%-35s\t%-15s\t%-30s\t%-3s\t%-3s%n", c.getCardName(), c.getDetailedManaCost(), c.getCardType(),
                    c.getAttackPower(), c.getDefensePower());

        }
        if(currentFirstCard == 1 && cardCounter < 10) {
            System.out.println("End of card list.");
            System.out.println("Enter the number of the card you wish to add.");
        }
        else if (currentFirstCard == 1 && cardCounter > 10) {
            System.out.println("Enter the number of the card you wish to add.");
            System.out.println("Type 'Next' to cycle through the card list, 'Exit' for another search.");
        }
        else if (currentFirstCard > 10 && currentLastCard < cardCounter) {
            System.out.println("Enter the number of the card you wish to view.");
            System.out.println("Type 'Next' or 'Previous' to cycle through the card list, 'Exit' for another search.");
        }
        else if (currentLastCard == cardCounter){
            System.out.println("End of card list.");
            System.out.println("Type 'Previous' to cycle back through the card list, 'Exit' for another search.");
        }
    }

    public static String deckViewCard( int cardChoice, List searchList) {
        System.out.println(searchList.get(cardChoice));
        System.out.println("Type 'Back' when ready to return to the previous list");
        System.out.println("'Add' to add this card to your deck, 'Remove' to remove it.");
        return userInput.nextLine().toLowerCase();

    }




    public static void addCard(List<Card> cardObjects, List<String> cardList, Card card){
       // cardObjects.add card;


    }

    public static void removeCard(String cardName){

    }
}
