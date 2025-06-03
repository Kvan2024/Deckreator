package tapmana;

public class Black extends Card implements ManaSource{
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
    private boolean requiresBlackMana;
    private boolean requiresBlackCards;
public Black(String cardName, String cardColor, String cardType, String detManaCost, int manaCost, String cardText, String flavorText,
                 int attack, int defense, boolean requiresBlackMana, boolean requiresBlackCards) {
    super(cardName, cardColor, cardType, detManaCost, manaCost, cardText, flavorText, attack, defense);
    this.requiresBlackMana = requiresBlackMana;
    this.requiresBlackCards = requiresBlackCards;}

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
