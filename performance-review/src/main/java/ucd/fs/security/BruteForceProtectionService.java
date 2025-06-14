package ucd.fs.security;

import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BruteForceProtectionService {

    private static final int MAX_ATTEMPTS = 5;
    private static final long BLOCK_DURATION = 15 * 60; // 15 minutes en secondes

    private final Map<String, Attempt> attempts = new ConcurrentHashMap<>();

    public void loginFailed(String key) {
        Attempt attempt = attempts.getOrDefault(key, new Attempt(0, Instant.now().getEpochSecond()));
        attempt.count++;
        attempt.lastAttempt = Instant.now().getEpochSecond();
        attempts.put(key, attempt);
    }

    public boolean isBlocked(String key) {
        Attempt attempt = attempts.get(key);
        if (attempt == null) {
            return false;
        }
        if (attempt.count >= MAX_ATTEMPTS) {
            long timeSinceLastAttempt = Instant.now().getEpochSecond() - attempt.lastAttempt;
            if (timeSinceLastAttempt < BLOCK_DURATION) {
                return true;
            } else {
                attempts.remove(key); // reset après expiration
            }
        }
        return false;
    }

    public void loginSucceeded(String key) {
        attempts.remove(key);
    }

    private static class Attempt {
        int count;
        long lastAttempt;

        public Attempt(int count, long lastAttempt) {
            this.count = count;
            this.lastAttempt = lastAttempt;
        }
    }
}

