package hu.klayton.wade.spring.error;

/**
 * @author Walter Krix <walter.krix@inbuss.hu>
 */
public class LoginException extends RuntimeException {

    public LoginException(final String message) {
        super(message);
    }
}
