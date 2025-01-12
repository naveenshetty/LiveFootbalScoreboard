package org.sportrader.Exception;

public class InvalidTeamNameException extends RuntimeException{
    public InvalidTeamNameException(String message) {
        super(message);
    }
}
