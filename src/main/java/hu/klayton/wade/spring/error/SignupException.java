package hu.klayton.wade.spring.error;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
public class SignupException extends RuntimeException {

    public SignupException(final String message) {
        super(message);
    }
}
