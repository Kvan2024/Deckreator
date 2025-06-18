package tapmana.controller;

import tapmana.dao.DeckDao;
import tapmana.exception.DaoException;
import tapmana.model.Deck;
import tapmana.model.dto.CardDto;
import tapmana.model.dto.DeckDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin(
        origins = {"https://deckreator.netlify.app"},
        allowCredentials = "true"
)
@RestController
@RequestMapping(path = "/users/{user}/decks")
public class DeckController {

    private final DeckDao deckDao;
    public DeckController(DeckDao deckDao) {
        this.deckDao = deckDao;
    }
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Deck> getAllDecks(@PathVariable String user, Principal principal) {
        if(!(principal.getName().equals(user))) {
            throw new AccessDeniedException("You are not allowed to view another users decks");
        }
        if (deckDao.getDecks(user).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return deckDao.getDecks(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Deck getDeckById(@PathVariable String user, @PathVariable int id, Principal principal) {
        if (!(principal.getName().equals(user))) {
            throw new AccessDeniedException("Can not view decks of another user");
        }
        Deck tempDeck = deckDao.getDeckById(user, id);
        if (tempDeck.getOwner() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return tempDeck;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Deck createDeck(
            @PathVariable String user,
            @Valid @RequestBody DeckDto deckDto, Principal principal) {

        if (!(principal.getName().equals(user))) {
            throw new AccessDeniedException("Can not create deck for another user");
        }
        try {
            return deckDao.createDeck(user, deckDto.getName(), deckDto.getDescription());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid deck data provided", e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Deck editDeck(
            @PathVariable String user,
            @PathVariable int id,
            @Valid @RequestBody DeckDto deckDto,
            Principal principal) {
        if (!(principal.getName().equals(user))) {
            throw new AccessDeniedException("You are not allowed to edit this deck");
        }
        if (deckDao.getDeckById(user, id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Deck tempDeck = new Deck();
        tempDeck.setName(deckDto.getName());
        tempDeck.setDescription(deckDto.getDescription());

        try {
            return deckDao.editDeck(user, deckDto.getName(), deckDto.getDescription(), id);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid deck data provided", e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteDeck(
            @PathVariable String user,
            @PathVariable int id, Principal principal) {

        if (!(principal.getName().equals(user))) {
            throw new AccessDeniedException("Can not delete decks for another user");
        }
        try {
            deckDao.deleteDeck(id);
        }
        catch(DaoException  e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
    public Deck addCardToDeck(
            @PathVariable String user,
            @PathVariable int id,
            @Valid @RequestBody CardDto cardDto, Principal principal) {
        if (!(principal.getName().equals(user))) {
            throw new AccessDeniedException("Can not add card to deck of another user");
        }
        return deckDao.addCardToDeck(cardDto.getMultiverseId(), id, user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "/{id}/{cardid}", method = RequestMethod.DELETE)
    public Deck deleteCardFromDeck(
            @PathVariable String user,
            @PathVariable int id,
            @PathVariable int cardid, Principal principal) {

        if (!(principal.getName().equals(user))) {
            throw new AccessDeniedException("Can not delete card from deck of another user");
        }
        return deckDao.deleteCardFromDeck(cardid, id, user);
    }


}
