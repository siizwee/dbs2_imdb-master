package de.hsh.dbs2.imdb.util;

public class IMDBException extends Exception {
    public IMDBException(String message, String originalMessage) {
        super(message);
    }
}
