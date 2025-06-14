package ucd.fs.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
public class BruteForceProtectionService {
    private static final int MAX_ATTEMPTS = 5;
    private static final Duration BLOCK_DURATION = Duration.ofMinutes(15);
    private final Cache<String, Attempt> attemptsCache = Caffeine.newBuilder()
            .expireAfterWrite(BLOCK_DURATION)
            .build();

    public void loginFailed(String key) {
        Attempt attempt = attemptsCache.get(key, k -> new Attempt(0, Instant.now()));
        if (attempt != null) {
            attempt.increment();
            attemptsCache.put(key, attempt);
        }
    }

    public boolean isBlocked(String key) {
        Attempt attempt = attemptsCache.getIfPresent(key);
        return attempt != null && attempt.getCount() >= MAX_ATTEMPTS;
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    @Getter
    @AllArgsConstructor
    private static class Attempt {
        private int count;
        private Instant lastAttempt;

        public void increment() {
            this.count++;
            this.lastAttempt = Instant.now();
        }
    }
}