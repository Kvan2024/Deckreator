package tapmana;

public class Blue extends Card{
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
    private boolean requiresBlueMana;
    private boolean requiresBlueCards;
    public Blue(String cardName, String cardColor, String cardType, String detManaCost, int manaCost, String cardText, String flavorText,
                 int attack, int defense, boolean requiresBlueMana, boolean requiresBlueCards) {
        super(cardName, cardColor, cardType, detManaCost, manaCost, cardText, flavorText, attack, defense);
        this.requiresBlueMana = requiresBlueMana;
        this.requiresBlueCards = requiresBlueCards;}

    @Override
    public boolean addToManaPool() {
        if (this.getCardText().contains("to your mana pool")) {
            return true;
        }
        else if (this.getCardType().contains("Land")){
            return true;
        }
        return false;
    }
}