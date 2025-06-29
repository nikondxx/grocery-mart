package com.grocerymart.grocerymartbackend.mock;

import java.util.Arrays;
import java.util.Optional;

public enum DiscountCodes {
    SAVE10(10.0),
    FIRST20(20.0),
    WELCOME15(15.0);

    private final double discountValue;

    DiscountCodes(double discountValue) {
        this.discountValue = discountValue;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    // Optional: Case-insensitive lookup
    public static Optional<DiscountCodes> fromString(String code) {
        return Arrays.stream(DiscountCodes.values())
                .filter(d -> d.name().equalsIgnoreCase(code))
                .findFirst();
    }
}
