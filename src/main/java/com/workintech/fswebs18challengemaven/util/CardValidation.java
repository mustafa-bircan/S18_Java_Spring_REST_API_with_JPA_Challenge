package com.workintech.fswebs18challengemaven.util;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Type;

public class CardValidation {

    public static void validateCard(Card card) {
        if (card.getType() == Type.JOKER && (card.getValue() != null || card.getColor() != null)) {
            throw new IllegalArgumentException("JOKER type should not have value or color.");
        }
        if (card.getValue() != null && card.getType() != null) {
            throw new IllegalArgumentException("Card cannot have both value and type.");
        }
        if (card.getValue() == null && card.getType() == null) {
            throw new IllegalArgumentException("Card must have either value or type.");
        }
    }
}
