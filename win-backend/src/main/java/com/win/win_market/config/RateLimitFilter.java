package com.win.win_market.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RateLimitFilter implements Filter {

    private final ConcurrentHashMap<String, AtomicLong> requestCounts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> requestTimes = new ConcurrentHashMap<>();

    // Limite de 100 requisições por minuto por IP
    private static final long MAX_REQUESTS_PER_MINUTE = 100;
    private static final long TIME_WINDOW = 60000; // 1 minuto em millisegundos

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIP = getClientIP(httpRequest);
        long currentTime = System.currentTimeMillis();

        // Limpa contadores antigos
        cleanOldEntries(currentTime);

        // Verifica rate limit
        if (isRateLimited(clientIP, currentTime)) {
            httpResponse.setStatus(429); // Too Many Requests
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\": \"Muitas requisições\", \"message\": \"Limite de requisições excedido. Tente novamente em alguns instantes.\"}");
            return;
        }

        chain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIP = request.getHeader("X-Real-IP");
        if (xRealIP != null && !xRealIP.isEmpty()) {
            return xRealIP;
        }

        return request.getRemoteAddr();
    }

    private boolean isRateLimited(String clientIP, long currentTime) {
        Long firstRequestTime = requestTimes.get(clientIP);

        if (firstRequestTime == null || (currentTime - firstRequestTime) > TIME_WINDOW) {
            // Nova janela de tempo
            requestTimes.put(clientIP, currentTime);
            requestCounts.put(clientIP, new AtomicLong(1));
            return false;
        }

        // Incrementa contador
        AtomicLong count = requestCounts.computeIfAbsent(clientIP, k -> new AtomicLong(0));
        long currentCount = count.incrementAndGet();

        return currentCount > MAX_REQUESTS_PER_MINUTE;
    }

    private void cleanOldEntries(long currentTime) {
        requestTimes.entrySet().removeIf(entry ->
            (currentTime - entry.getValue()) > TIME_WINDOW);

        requestCounts.entrySet().removeIf(entry ->
            !requestTimes.containsKey(entry.getKey()));
    }
}
