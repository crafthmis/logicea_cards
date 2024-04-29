package com.logicea.cards.services;


import com.logicea.cards.dtos.CardDto;
import com.logicea.cards.dtos.CardSearchAndFilterRequest;
import com.logicea.cards.exceptions.CustomErrorException;
import com.logicea.cards.exceptions.CustomExtraErrorException;
import com.logicea.cards.models.Card;
import com.logicea.cards.models.User;
import com.logicea.cards.repositories.CardRepository;
import com.logicea.cards.repositories.UserRepository;
import com.logicea.cards.utils.UtilService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CardService {


    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> getCards() {
        List<CardDto> cardDtos = cardRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(dto -> new CardDto(dto.getName(), dto.getDescription(), dto.getColor()))
                .toList();
        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", cardDtos);
    }

    public ResponseEntity<Object> createCard(Long userId, @RequestBody Card card) {

        User user = userRepository.findById(userId).orElseThrow(null);
        if (user == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such user with id: " + userId);
        }
        if (null != card.getName() && !card.getName().isEmpty()) {
            Card obj = new Card();
            obj.setUserId(userId);
            obj.setStatusId(card.getStatusId());
            obj.setName(card.getName());
            obj.setDescription(card.getDescription());
            obj.setColor(card.getColor());
            cardRepository.save(obj);
            throw new CustomErrorException(HttpStatus.CREATED, "Card created successfully");
        } else {
            throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something has gone wrong");
        }
    }

    public ResponseEntity<Object> getCard(Long userId, Long cardId) {

        User user = userRepository.findById(userId).orElseThrow(null);
        if (user == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such user with id: " + userId);
        }
        Optional<Card> card = cardRepository.findById(userId);
        if (card.isEmpty()) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such card with id: " + cardId);
        }

        CardDto cDto = card.map(dto -> new CardDto(dto.getName(), dto.getDescription(), dto.getColor())).orElseThrow(null);
        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", cDto);

    }

    public ResponseEntity<Object> getUserCards(Long id) {
        User user = userRepository.findById(id).orElseThrow(null);
        if (user == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such user with id: " + id);
        }
        List<CardDto> cardsDto = user
                .getUserCards()
                .stream()
                .filter(Objects::nonNull)
                .map(dto -> new CardDto(dto.getName(), dto.getDescription(), dto.getColor()))
                .toList();
        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", cardsDto);
    }

    public ResponseEntity<Object> deleteCardsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(null);
        if (user == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such user with id: " + userId);
        }
        List<Card> cards  = user.getUserCards();
        if(cards.isEmpty()){
            cardRepository.deleteAllInBatch(cards);
            throw new CustomErrorException(HttpStatus.OK, "Cards deleted Successfully");
        }
        throw new CustomErrorException(HttpStatus.OK, "No cards exist Successfully");

    }

    @Transactional
    public ResponseEntity<Object> updateUserCard(Long userId, Long cardId, @RequestBody Card card) {
        User user = userRepository.findById(userId).orElseThrow(null);
        if (user == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such user with id: " + userId);
        }
        Card cardOptional = cardRepository.findById(userId).orElse(null);
        if (cardOptional == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such card with id: " + cardId);
        }

        if (!Objects.equals(card.getName(), "")) {
            cardOptional.setName(card.getName());
            System.out.println(card.getName());
        }
        if (!Objects.equals(card.getDescription(), "")) {
            cardOptional.setDescription(card.getDescription());
        }

        if (!Objects.equals(card.getColor(), "")) {
            cardOptional.setColor(card.getColor());
        }

        if (!Objects.equals(card.getStatus(), null)) {
            cardOptional.setStatusId(card.getStatusId());
        }
        cardRepository.save(cardOptional);

        throw new CustomErrorException(HttpStatus.OK, "Card updated successfully");

    }

    public Page<Card> searchAndFilterProject(Long userId,CardSearchAndFilterRequest searchRequest) {
        Pageable pageable = UtilService.getPageable(searchRequest);
        String searchText = searchRequest.getSearchText();
        User user = userRepository.findById(userId).orElseThrow(null);
        if (user == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such user with id: " + userId);
        }
        if(searchRequest.getSearchText() != null){
            searchText = "%" + searchText + "%";
        }
        return cardRepository.search(searchText, searchRequest.getStartDate(), searchRequest.getEndDate(), pageable);
    }

}
