package hu.klayton.wade.spring.error;

/**
 * @author Walter Krix <walter.krix@inbuss.hu>
 */
public class SignupException extends RuntimeException {

    public SignupException(final String message) {
        super(message);
    }
}
