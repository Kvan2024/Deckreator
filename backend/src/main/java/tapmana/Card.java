package tapmana;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Card implements ManaSource{

    private String cardName;
    private String cardColor;
    private String detailedManaCost;
    private int totalManaCost;
    private String cardType;
    private String keyWord;
    private String cardText;
    private String flavorText;
    private int attackPower;
    private int defensePower;
    private boolean needsMultiManaColor;
    private static final File cardList = new File("design/cards.csv");
    private static final Scanner userInput = new Scanner(System.in);
    private List card = new ArrayList();

    public String getCardName() {
        return cardName;
    }

    public String getCardColor() {
        return cardColor;
    }

    public String getDetailedManaCost() {
        return detailedManaCost;
    }

    public int getTotalManaCost() {
        return totalManaCost;
    }

    public String getCardType() {
        return cardType;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public String getCardText() {
        return cardText;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }

    public boolean isNeedsMultiManaColor() {
        return needsMultiManaColor;
    }

    public Card() {
    }
    public Card(String cardName, String cardColor, String cardType, String detManaCost, int manaCost, String cardText, String flavorText, int attack, int defense) {
        this.cardName = cardName;
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.detailedManaCost = detManaCost;
        this.totalManaCost = manaCost;
        this.cardText = cardText;
        this.flavorText = flavorText;
        this.attackPower = attack;
        this.defensePower = defense;
    }
    public static List<Card> cardSearch(String intent, String searchTerm) {
         List<Card> tempCardList = new ArrayList<>();
         List<Card> deckCards = new ArrayList<>();
            try (BufferedReader cardListInput = new BufferedReader(new FileReader(cardList)); PrintWriter testCard = new PrintWriter("design/Test.txt")) {
                int csvLineCount = 0;
                StringBuilder quotedField = new StringBuilder();
                boolean quotesUsed = false;
                String cardLine = cardListInput.readLine();
                String[] columnLabels = cardLine.split(",");

                while (cardLine != null) {
                    if (csvLineCount == 0) {
                        csvLineCount++;
                        cardLine = cardListInput.readLine();
                        continue;

                    }
                    if (cardLine.contains("\"")) {

                    }
                    String[] nameCardArray = cardLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Stackoverflow saves the day!
                    if (quotesUsed) {
                        quotedField.append("\n").append(cardLine);
                        if (cardLine.endsWith("\"")) {
                            quotesUsed = false;
                            cardLine = quotedField.toString();
                            quotedField.setLength(0);
                        } else {
                            cardLine = cardListInput.readLine();
                            continue;
                        }
                        continue;
                    }

                    for (int i = 0; i < nameCardArray.length; i++) {
                        String tempField = nameCardArray[i].trim();

                        if (tempField.startsWith("\"") && !tempField.endsWith("\"")) {
                            quotedField = new StringBuilder(tempField);
                            quotesUsed = true;
                        } else if (quotesUsed) {
                            quotedField.append(",").append(tempField);
                            if (tempField.endsWith("\"")) {
                                quotesUsed = false;
                                nameCardArray[i] = quotedField.toString();
                                quotedField.setLength(0);
                            }
                        } else {
                            nameCardArray[i] = tempField;
                        }
                    }

                    switch (intent) {
                        case "deck":
                        case "name":

                            if (cardLine.toLowerCase().contains(searchTerm.toLowerCase())) {
                                if (nameCardArray[50].toLowerCase().contains(searchTerm.toLowerCase())) {

                                    // To print out data to test.txt for review
                                    // for (int x = 0; x < columnLabels.length; x++) {
                                    // testCard.println("Cell #" + x + ") " + "(  " + columnLabels[x] + "  )   " + nameCardArray[x]);
                                    //}

                                    Card card = cardConstructor(nameCardArray);
                                    if (intent.equalsIgnoreCase("name")) {
                                        tempCardList.add(card);
                                    }
                                    else {
                                        deckCards.add(card);
                                    }
                                }
                            }
                            break;
                        case "color":
                            String manaColor = nameCardArray[8].toLowerCase();
                            String[] searchLetter = searchTerm.toLowerCase().split("");
                            if (searchTerm.equals("none") || searchTerm.equals("colorless")) {
                                searchLetter[0] = "U";
                            }
                            if (manaColor.contains(searchLetter[0])) {
                                Card card = cardConstructor(nameCardArray);
                                tempCardList.add(card);
                            }
                            break;
                        case "cost":
                            int tempInt = Integer.parseInt(searchTerm);
                            if ((int) Double.parseDouble(nameCardArray[49]) == tempInt) {
                                Card card = cardConstructor(nameCardArray);
                                tempCardList.add(card);
                            }
                            break;

                    }
                    cardLine = cardListInput.readLine();
                }

                // for (int x = 0; x < tempCardList.size(); x++) {
                //    testCard.println(tempCardList.get(x));
                //}

            } catch (IOException e) {
                System.out.println("Card list not found. This is very very bad. " + e.getMessage());
            }
        if (intent.equalsIgnoreCase("deck")) {
            return deckCards;
        }
        else {
            return tempCardList;
        }
    }

    public static void cardSearchViewer(List cardList) {
        int cardCounter = cardList.size();
        int currentFirstCard = 1;
        int currentLastCard = 10;
            boolean viewCards = true;
            while(viewCards){
                cardViewCycle(cardCounter, currentFirstCard, currentLastCard, cardList);

                String pageChoice = userInput.nextLine().toLowerCase();

                if (pageChoice.matches(".*\\d.*")) {
                    viewCard(Integer.parseInt(pageChoice), cardList);

                    String response = userInput.nextLine().toLowerCase();
                    if (response.equals("exit")) {
                        viewCards = false;
                    } else continue; {
                    }
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
                                cardViewCycle(cardCounter, currentFirstCard, currentLastCard, cardList);
                            }
                            else {
                                System.out.println("Invalid Entry, sending you back to the card list");
                                    cardViewCycle(cardCounter, currentFirstCard, currentLastCard, cardList);

                            }

                    }
                }
            }

    }

    public static void cardViewCycle(int cardCounter, int currentFirstCard, int currentLastCard, List listOfDeckCards) {
        System.out.printf("%-39s\t%-15s\t%-30s\t%-3s\t%-3s\t%-5s%n","   Name", "Cost", "Type", "Att", "Def", "Mana Source");
        //System.out.println("   Name                                 " + "\t" + "Cost        " + "\t" + "Type                        "
                //+ "\t" + "Att" + "\t" + "Def" + "\t" + "Mana Source");
        for (int x = currentFirstCard; x <= currentLastCard; x++) {
            Card c = (Card) listOfDeckCards.get(x - 1);
            boolean manaSource = c.addToManaPool();
            System.out.printf(x + ") " + "%-35s\t%-15s\t%-30s\t%-3s\t%-3s\t%-5b%n", c.getCardName(), c.getDetailedManaCost(), c.getCardType(),
                    c.getAttackPower(), c.getDefensePower(), manaSource);

        }
        if(currentFirstCard == 1 && cardCounter < 10) {
            System.out.println("End of card list.");
            System.out.println("Enter the number of the card you wish to view. 'Exit' for another search.");
        }
        else if (currentFirstCard == 1 && cardCounter > 10) {
            System.out.println("Enter the number of the card you wish to view.");
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

    public static void viewCard( int cardChoice, List searchList) {
        System.out.println(searchList.get(cardChoice));
        System.out.println("Type 'Back' when ready to return to the previous list");

    }

    public static Card cardConstructor(String[] nameCardArray) {
        int manaCost = (int) Double.parseDouble(nameCardArray[49]);
        int attPower;
        int defPower;
        try {
            attPower = (int) Double.parseDouble(nameCardArray[57]);
            defPower = (int) Double.parseDouble(nameCardArray[72]);
        } catch (NumberFormatException e) {
            attPower = 0;
            defPower = 0;
        }


        String[] colorConstructor = nameCardArray[48].split("");
        String manaChar = "c";

        if (!nameCardArray[48].matches(".*[a-zA-Z]+.*")) {
            boolean needsArtifactCards = nameCardArray[71].toLowerCase().contains("affinity for artifacts");
            return new Colorless(nameCardArray[50], nameCardArray[8], nameCardArray[73], nameCardArray[48],
                    manaCost, nameCardArray[71], nameCardArray[21], attPower, defPower, false, needsArtifactCards);
        }
        else {

            for (int x = 0; x < colorConstructor.length; x++) {
                if (Character.isLetterOrDigit(colorConstructor[x].charAt(0))) {
                    manaChar = colorConstructor[x];
                    break;
                }
            }
        }
            switch (manaChar) {
                case "b":
                    boolean needsBlackMana = false;
                    boolean needsBlackCards = false;
                    if (nameCardArray[48].toLowerCase().contains("b")) {
                        needsBlackMana = true;
                    }
                    if (nameCardArray[71].toLowerCase().contains("black card") || nameCardArray[71].toLowerCase().contains("black creature") ||
                            nameCardArray[71].toLowerCase().contains("black spell") || nameCardArray[71].toLowerCase().contains("black mana")) {
                        needsBlackCards = true;
                    }
                    return new Black(nameCardArray[50], nameCardArray[8], nameCardArray[73], nameCardArray[48],
                            manaCost, nameCardArray[71], nameCardArray[21], attPower, defPower, needsBlackMana, needsBlackCards);

                case "u":
                    boolean needsBlueMana = false;
                    boolean needsBlueCards = false;
                    if (nameCardArray[48].toLowerCase().contains("u")) {
                        needsBlueMana = true;
                    }
                    if (nameCardArray[71].toLowerCase().contains("blue card") || nameCardArray[71].toLowerCase().contains("blue creature") ||
                            nameCardArray[71].toLowerCase().contains("blue spell") || nameCardArray[71].toLowerCase().contains("blue mana")) {
                        needsBlueCards = true;
                    }
                    return new Blue(nameCardArray[50], nameCardArray[8], nameCardArray[73], nameCardArray[48],
                            manaCost, nameCardArray[71], nameCardArray[21], attPower, defPower, needsBlueMana, needsBlueCards);

                case "g":
                    boolean needsGreenMana = false;
                    boolean needsGreenCards = false;
                    if (nameCardArray[48].toLowerCase().contains("b")) {
                        needsGreenMana = true;
                    }
                    if (nameCardArray[71].toLowerCase().contains("green card") || nameCardArray[71].toLowerCase().contains("green creature") ||
                            nameCardArray[71].toLowerCase().contains("green spell") || nameCardArray[71].toLowerCase().contains("green mana")) {
                        needsGreenCards = true;
                    }
                    return new Green(nameCardArray[50], nameCardArray[8], nameCardArray[73], nameCardArray[48],
                            manaCost, nameCardArray[71], nameCardArray[21], attPower, defPower, needsGreenMana, needsGreenCards);

                case "r":
                    boolean needsRedMana = false;
                    boolean needsRedCards = false;
                    if (nameCardArray[48].toLowerCase().contains("r")) {
                        needsRedMana = true;
                    }
                    if (nameCardArray[71].toLowerCase().contains("red card") || nameCardArray[71].toLowerCase().contains("red creature") ||
                            nameCardArray[71].toLowerCase().contains("red spell") || nameCardArray[71].toLowerCase().contains("red mana")) {
                        needsRedCards = true;
                    }
                    return new Red(nameCardArray[50], nameCardArray[8], nameCardArray[73], nameCardArray[48],
                            manaCost, nameCardArray[71], nameCardArray[21], attPower, defPower, needsRedMana, needsRedCards);

                case "w":
                    boolean needsWhiteMana = false;
                    boolean needsWhiteCards = false;
                    if (nameCardArray[48].toLowerCase().contains("w")) {
                        needsWhiteMana = true;
                    }
                    if (nameCardArray[71].toLowerCase().contains("white card") || nameCardArray[71].toLowerCase().contains("white creature") ||
                            nameCardArray[71].toLowerCase().contains("white spell") || nameCardArray[71].toLowerCase().contains("white mana")) {
                        needsWhiteCards = true;
                    }
                    return new White(nameCardArray[50], nameCardArray[8], nameCardArray[73], nameCardArray[48],
                            manaCost, nameCardArray[71], nameCardArray[21], attPower, defPower, needsWhiteMana, needsWhiteCards);

                case "c":
                    boolean needsArtifactMana = false;
                    boolean needsArtifactCards2 = false;
                    String colorlessString = nameCardArray[48].toLowerCase();
                    if (!colorlessString.matches(".*[a-zA-Z]+.*")) {
                        needsArtifactMana = true;
                    }
                    if (nameCardArray[71].toLowerCase().contains("affinity for artifacts")) {
                        needsArtifactCards2 = true;
                    }
                    return new Colorless(nameCardArray[50], nameCardArray[8], nameCardArray[73], nameCardArray[48],
                            manaCost, nameCardArray[71], nameCardArray[21], attPower, defPower, needsArtifactMana, needsArtifactCards2);
                default:
                    return new Colorless(nameCardArray[50], nameCardArray[8], nameCardArray[73], nameCardArray[48],
                            manaCost, nameCardArray[71], nameCardArray[21], attPower, defPower, false, false);
            }

    }
    @Override
    public String toString() {
        return cardName + "\t" + cardColor + "\t" + cardType + "\t" + totalManaCost + "\t" + detailedManaCost + "\t" +
               cardText + "\t" + flavorText + "\t" + attackPower + "\t" + defensePower + "\t";


    }
}

