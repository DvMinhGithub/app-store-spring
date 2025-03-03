package com.mdv.appstore.exception;

public class DiscountGreaterThanTotalPriceException extends RuntimeException {
    public DiscountGreaterThanTotalPriceException(String message) {
        super(message);
    }
}
