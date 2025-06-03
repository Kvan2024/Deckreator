package tapmana.service;

import tapmana.model.dto.CardDto;
import org.springframework.web.client.RestClient;

public class CardImageService implements ImageService{

    private final String API_URL = "https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=";
    private final RestClient restClient = RestClient.create(API_URL);

    public CardDto getCardImage(int cardId) {
        CardDto cardImage = restClient .get() .uri(cardId + "&type=card") .retrieve() .body(CardDto.class);

        return cardImage;
    }



}
