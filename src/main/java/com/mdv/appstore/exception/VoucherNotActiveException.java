package com.mdv.appstore.exception;

public class VoucherNotActiveException extends RuntimeException {
    public VoucherNotActiveException(String message) {
        super(message);
    }
}
