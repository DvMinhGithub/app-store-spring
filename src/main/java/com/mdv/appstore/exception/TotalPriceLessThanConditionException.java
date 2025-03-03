package com.mdv.appstore.exception;

public class TotalPriceLessThanConditionException extends RuntimeException {
    public TotalPriceLessThanConditionException(String message) {
        super(message);
    }
}
