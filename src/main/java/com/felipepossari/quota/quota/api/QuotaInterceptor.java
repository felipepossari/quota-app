package com.felipepossari.quota.quota.api;

import com.felipepossari.quota.DateTimeUtil;
import com.felipepossari.quota.quota.Quota;
import com.felipepossari.quota.quota.QuotaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.time.LocalDateTime;
import java.util.Map;

import static com.felipepossari.quota.common.exception.ErrorReason.TOO_MANY_REQUESTS;

@Component
@RequiredArgsConstructor
public class QuotaInterceptor implements HandlerInterceptor {

    private final QuotaService service;
    private final DateTimeUtil dateTimeUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var attributes = ((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));

        if (!attributes.containsKey("id")) {
            response.sendError(HttpStatus.BAD_REQUEST.value());
            return false;
        }

        String userId = attributes.get("id");

        Quota quota = service.retrieveQuota(userId);
        quota.consumeQuota(1, dateTimeUtil.nowUtc());

        response.addHeader("RateLimit-Limit", quota.getQuotaLimit().toString());
        response.addHeader("RateLimit-Remaining", String.valueOf(Math.max(quota.getRemaining(), 0)));
        response.addHeader("RateLimit-Reset", String.valueOf(quota.getResetTimeInSeconds(LocalDateTime.now())));

        if (quota.isQuotaReached()) {
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), TOO_MANY_REQUESTS.getMessage());
            return false;
        }else{
            service.save(quota);
        }

        return true;
    }
}
