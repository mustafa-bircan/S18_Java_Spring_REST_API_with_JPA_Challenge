package com.workintech.fswebs18challengemaven.repository;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardRepositoryImpl implements CardRepository{


    private final EntityManager entityManager;

    private static final String FIND_BY_COLOR_JPQL = "SELECT c FROM Card c WHERE c.color = :color";
    private static final String FIND_ALL_JPQL = "SELECT c FROM Card c";
    private static final String FIND_BY_VALUE_JPQL = "SELECT c FROM Card c WHERE c.value = :value";
    private static final String FIND_BY_TYPE_JPQL = "SELECT c FROM Card c WHERE c.type = :type";

    @Autowired
    public CardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Card save(Card card) {
        entityManager.persist(card);
        return card;
    }

    @Override
    public List<Card> findByColor(String color) {
        Color colorEnum;
        try {
            colorEnum = Color.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CardException("Invalid color value: " + color, HttpStatus.BAD_REQUEST);
        }

        TypedQuery<Card> query = entityManager.createQuery(FIND_BY_COLOR_JPQL, Card.class);
        query.setParameter("color", colorEnum);

        List<Card> result = query.getResultList();

        if (result.isEmpty()) {
            throw new CardException("No cards found for color: " + color, HttpStatus.NOT_FOUND);
        }

        return result;
    }







    @Override
    public List<Card> findAll() {
        return entityManager.createQuery(FIND_ALL_JPQL, Card.class).getResultList();

    }

    @Override
    public List<Card> findByValue(Integer value) {
        TypedQuery<Card> query = entityManager.createQuery(FIND_BY_VALUE_JPQL, Card.class);
        query.setParameter("value", value);
        return query.getResultList();
    }


    @Override
    public List<Card> findByType(String type) {
        TypedQuery<Card> query = entityManager.createQuery(FIND_BY_TYPE_JPQL, Card.class);
        query.setParameter("type", Type.valueOf(type.toUpperCase()));
        return query.getResultList();
    }


    @Override
    public Card update(Card card) {
        return entityManager.merge(card);
    }

    @Override
    public Card remove(Long id) {
        Card card = entityManager.find(Card.class, id);
        if (card != null) {
            entityManager.remove(card);
        }
        return card;
    }

}
