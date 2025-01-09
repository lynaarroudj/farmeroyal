package exception;

public class InvalidException extends Throwable {
    public InvalidException(String emailIsMalformed) {
        super(emailIsMalformed);
    }
}
