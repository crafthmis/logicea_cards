package com.logicea.cards.controllers;


import com.logicea.cards.dtos.CardSearchAndFilterRequest;
import com.logicea.cards.models.Card;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class CardsController extends BaseController{
    @GetMapping("/user/{id}/cards")
    public ResponseEntity<Object> getUsercards(@PathVariable Long id) {
        return cardService.getUserCards(id);
    }

    @GetMapping("/user/{userId}/card/{cardId}")
    public ResponseEntity<Object> getUsercard(@PathVariable Long userId,@PathVariable Long cardId) {
        return cardService.getCard(userId,cardId);
    }


    @PostMapping("/user/{id}/create")
    public ResponseEntity<Object> createCard(@PathVariable Long id, @RequestBody Card card){

        return cardService.createCard(id,card);

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/cards")
    public ResponseEntity<Object> getAllCards(){

        return cardService.getCards();

    }

    @DeleteMapping("/user/{id}/delete")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id){
        return cardService.deleteCardsByUserId(id);
    }

    @PatchMapping("/user/{userId}/card/{cardId}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long userId, @PathVariable Long cardId,@RequestBody Card card){
        return cardService.updateUserCard(userId,cardId,card);
    }

    @PostMapping("/user/{userId}/pages")
    public ResponseEntity<Page<Card>> getCardsById(@PathVariable long userId, @RequestBody CardSearchAndFilterRequest searchRequest){
        try{
            return ResponseEntity.ok(cardService.searchAndFilterProject(userId,searchRequest));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


}
