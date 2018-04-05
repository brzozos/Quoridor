package utilities.exception;

public class WrongNumberOfResultsException extends Exception {
    public WrongNumberOfResultsException(String message){
        super(message);
    }
    public WrongNumberOfResultsException(String message, Throwable throwable){
        super(message, throwable);
    }
}

