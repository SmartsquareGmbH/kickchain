package de.smartsquare.kickchain;

public class BlockchainException extends Exception {

    public BlockchainException(String message) {
        super(message);
    }

    public BlockchainException(String message, Throwable cause) {
        super(message, cause);
    }

}
