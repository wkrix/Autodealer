package hu.klayton.wade.spring.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Service
public class LoginAttemptService {

    public static final int MAX_ATTEMPT = 3;
    private static Map<String, Integer> attemptsMap;

    @PostConstruct
    public void initialize() {
        attemptsMap = new HashMap<>();
    }

    public void loginSucceeded(String key) {
        attemptsMap.remove(key);
    }

    public void loginFailed(String key) {
        int attempts = getValueFromMap(key);
        attempts++;
        attemptsMap.put(key, attempts);

    }

    public boolean isBlocked(String key) {
        int attempts = getValueFromMap(key);
        return attempts >= MAX_ATTEMPT;
    }

    private int getValueFromMap(String key) {
        Integer attemptNumber = attemptsMap.get(key);
        return attemptNumber == null ? 0 : attemptNumber;
    }

    public void clearAll() {
        attemptsMap.clear();
    }

    public void clearIP(String key) {
        attemptsMap.remove(key);
    }

}
