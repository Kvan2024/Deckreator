package tapmana;

import java.io.*;

public class Test {
    private static final File cardList = new File("design/cards.csv");

    public static void cardTest() {


        try (BufferedReader cardListInput = new BufferedReader(new FileReader(cardList)); PrintWriter testCard = new PrintWriter("design/Test.txt")) {
            for (int x = 0; x < 17868; x++) {
                String cardLine = cardListInput.readLine();
                if (x > 17859 && x < 17867) {

                    String[] nameCardArray = cardLine.split(",");
                        for (int i = 0; i < nameCardArray.length; i++) {
                            testCard.println("Cell #" + i + ") " + nameCardArray[i]);
                        }
                }
            }
        } catch (IOException e) {
            System.out.println("Card list not found. This is very very bad. " + e.getMessage());
        }
    }


}