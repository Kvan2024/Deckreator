package tapmana;

public class TooManyCardsException  extends Exception{

    public TooManyCardsException() {
        super("Your deck may not contain more than 4 on this card");
    }

    public TooManyCardsException(String message) {
        super(message);
    }

}
