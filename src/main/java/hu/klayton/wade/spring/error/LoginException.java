package hu.klayton.wade.spring.error;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
public class LoginException extends RuntimeException {

    public LoginException(final String message) {
        super(message);
    }
}
