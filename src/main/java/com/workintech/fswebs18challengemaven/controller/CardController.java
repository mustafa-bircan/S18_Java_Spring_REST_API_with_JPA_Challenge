package com.workintech.fswebs18challengemaven.controller;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.repository.CardRepository;
import com.workintech.fswebs18challengemaven.util.CardValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@Slf4j
public class CardController {

    private final CardRepository cardRepository;

    @Autowired
    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        log.info("Fetching all cards");
        List<Card> cards = cardRepository.findAll();
        if (cards == null || cards.isEmpty()) {
            log.warn("No cards found");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/byColor/{color}")
    public ResponseEntity<List<Card>> getCardsByColor(@PathVariable String color) {
        log.info("Fetching cards with color: {}", color);
        return ResponseEntity.ok(cardRepository.findByColor(color));
    }

    @GetMapping("/byValue/{value}")
    public ResponseEntity<List<Card>> getCardsByValue(@PathVariable Integer value) {
        log.info("Fetching cards with value: {}", value);
        return ResponseEntity.ok(cardRepository.findByValue(value));
    }

    @GetMapping("/byType/{type}")
    public ResponseEntity<List<Card>> getCardsByType(@PathVariable String type) {
        log.info("Fetching cards with type: {}", type);
        return ResponseEntity.ok(cardRepository.findByType(type));
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        log.info("Creating a new card: {}", card);

        CardValidation.validateCard(card);

        return ResponseEntity.status(HttpStatus.CREATED).body(cardRepository.save(card));
    }

    @PutMapping("/")
    public ResponseEntity<Card> updateCard(@RequestBody Card card) {
        log.info("Updating card with id: {}", card.getId());

        if (card.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        CardValidation.validateCard(card);

        Card updatedCard = cardRepository.update(card);
        return ResponseEntity.ok(updatedCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        log.warn("Deleting card with id: {}", id);
        cardRepository.remove(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
