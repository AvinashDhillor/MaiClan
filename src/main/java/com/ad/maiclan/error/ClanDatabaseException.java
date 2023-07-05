package com.ad.maiclan.error;

public class ClanDatabaseException extends RuntimeException {

    public ClanDatabaseException(final String message) {
        super(message);
    }

    public ClanDatabaseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
