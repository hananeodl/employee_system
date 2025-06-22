package ucd.fs.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@Order(1)
public class RateLimiterFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS = 100;
    private static final long TIME_WINDOW = TimeUnit.MINUTES.toMillis(1);
    private final ConcurrentHashMap<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        RequestCounter counter = requestCounts.computeIfAbsent(ip, k -> new RequestCounter());

        if (counter.isRateLimited()) {
            response.setStatus(429);
            response.getWriter().write("Too many requests");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private static class RequestCounter {
        private long lastResetTime = System.currentTimeMillis();
        private int count = 0;

        synchronized boolean isRateLimited() {
            if (System.currentTimeMillis() - lastResetTime > TIME_WINDOW) {
                lastResetTime = System.currentTimeMillis();
                count = 0;
            }
            count++;
            return count > MAX_REQUESTS;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/graphiql")
                || request.getServletPath().equals("/playground");
    }
}