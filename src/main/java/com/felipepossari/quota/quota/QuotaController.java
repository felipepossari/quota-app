package com.felipepossari.quota.quota;

import com.felipepossari.quota.quota.api.model.QuotaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/quotas")
@RequiredArgsConstructor
public class QuotaController {

    private final QuotaBuilder builder;
    private final QuotaService service;

    @GetMapping
    public ResponseEntity<Page<QuotaResponse>> getUsersQuota(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        var quotas = service.getUsersQuota(page, pageSize);
        return ResponseEntity.ok(quotas.map(builder::toResponse));
    }
}
