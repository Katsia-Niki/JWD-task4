package by.jwd.multithreading.exception;

public class LogisticsBaseException extends Exception {
    public LogisticsBaseException() {
        super();
    }
    public LogisticsBaseException(String message) {
        super(message);
    }
    public LogisticsBaseException(Exception e) {
        super(e);
    }
    public LogisticsBaseException(String message, Exception e) {
        super(message, e);
    }
}
