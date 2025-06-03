package tapmana;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardList {
    public static void main(String[] args) {
        File cardFile = new File("design/cards.csv");

        List<String> cardList = new ArrayList<>();
        try(Scanner cardInfo = new Scanner(cardFile); PrintWriter testFile = new PrintWriter("design/CardList.txt")) {
            String currentLine = cardInfo.nextLine();
            String[] splitInfo = currentLine.split(",");

            for (int x = 0; x < splitInfo.length; x++) {
                testFile.println("Cell #" + x + " " + splitInfo[x]);
                cardList.add(splitInfo[x]);
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage());
        }

        }
    }
