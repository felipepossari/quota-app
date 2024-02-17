package com.felipepossari.quota.quota.api;

import com.felipepossari.quota.quota.QuotaBuilder;
import com.felipepossari.quota.quota.QuotaService;
import com.felipepossari.quota.quota.api.model.QuotaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.felipepossari.quota.quota.QuotaConstants.API_PARAM_PAGE;
import static com.felipepossari.quota.quota.QuotaConstants.API_PARAM_PAGE_DEFAULT_VALUE;
import static com.felipepossari.quota.quota.QuotaConstants.API_PARAM_PAGE_SIZE;
import static com.felipepossari.quota.quota.QuotaConstants.API_PARAM_PAGE_SIZE_DEFAULT_VALUE;
import static com.felipepossari.quota.quota.QuotaConstants.API_V1_QUOTA_URL;

@RestController
@RequestMapping(API_V1_QUOTA_URL)
@RequiredArgsConstructor
public class QuotaController {

    private final QuotaBuilder builder;
    private final QuotaService service;

    @GetMapping
    public ResponseEntity<Page<QuotaResponse>> getUsersQuota(
            @RequestParam(value = API_PARAM_PAGE, defaultValue = API_PARAM_PAGE_DEFAULT_VALUE) int page,
            @RequestParam(value = API_PARAM_PAGE_SIZE, defaultValue = API_PARAM_PAGE_SIZE_DEFAULT_VALUE) int pageSize) {
        var quotas = service.getUsersQuota(page, pageSize);
        return ResponseEntity.ok(quotas.map(builder::toResponse));
    }
}
