package tapmana.model;

public class Card {
    private String colorIdent;
    private String flavorText;
    private String keyWords;
    private String detailedManaCost;
    private int basicManaCost;
    private String cardName;
    private int power;
    private String rarity;
    private String subType;
    private String cardText;
    private int toughness;
    private String type;
    int multiverseId;


    public Card() {

    }
    public Card(String colorIdent, String flavorText, String keyWords, String detailedManaCost, int basicManaCost,
                String cardName, int power, String rarity, String subType, String cardText, int toughness, String type, int multiverseId) {
        this.colorIdent = colorIdent;
        this.flavorText = flavorText;
        this.keyWords = keyWords;
        this.detailedManaCost = detailedManaCost;
        this.basicManaCost = basicManaCost;
        this.cardName = cardName;
        this.power = power;
        this.rarity = rarity;
        this.subType = subType;
        this.cardText = cardText;
        this.toughness = toughness;
        this.type = type;
        this.multiverseId = multiverseId;
    }

    public String getColorIdent() {
        return colorIdent;
    }

    public void setColorIdent(String colorIdent) {
        this.colorIdent = colorIdent;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getDetailedManaCost() {
        return detailedManaCost;
    }

    public void setDetailedManaCost(String detailedManaCost) {
        this.detailedManaCost = detailedManaCost;
    }

    public int getBasicManaCost() {
        return basicManaCost;
    }

    public void setBasicManaCost(int basicManaCost) {
        this.basicManaCost = basicManaCost;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }

    public int getToughness() {
        return toughness;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMultiverseId() {
        return multiverseId;
    }
    public void setMultiverseId(int multiverseId) {
        this.multiverseId = multiverseId;
    }
}
