package com.felipepossari.quota.unit.quota;

import com.felipepossari.quota.common.config.QuotaProperties;
import com.felipepossari.quota.quota.QuotaBuilder;
import com.felipepossari.quota.quota.QuotaProducer;
import com.felipepossari.quota.quota.QuotaService;
import com.felipepossari.quota.quota.repository.QuotaRepository;
import com.felipepossari.quota.quota.repository.QuotaRepositoryFactory;
import com.felipepossari.quota.unit.base.QuotaTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.felipepossari.quota.unit.base.Constants.QUOTA_LIMIT;
import static com.felipepossari.quota.unit.base.Constants.RATE_KEY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuotaServiceTest {

    @Mock
    QuotaBuilder builder;

    @Mock
    QuotaRepositoryFactory factory;

    @Mock
    QuotaRepository repository;

    @Mock
    QuotaProducer producer;

    @Mock
    QuotaProperties properties;

    @InjectMocks
    QuotaService service;

    @Test
    void retrieveQuotaShouldJustRetrieveQuotaWhenQuotaExists() {
        // given
        var quota = QuotaTestBuilder.aQuota().build();

        // and
        given(factory.getRepository()).willReturn(repository);
        given(repository.findById(RATE_KEY)).willReturn(Optional.of(quota));

        // when
        var actualQuota = service.retrieveQuota(RATE_KEY);

        // then
        verify(factory).getRepository();
        verify(repository).findById(RATE_KEY);
        verify(repository, never()).create(any());
        verify(producer, never()).sendQuotaCreatedMessage(any());
    }

    @Test
    void retrieveQuotaShouldCreateQuotaWhenQuotaIsNotFound() {
        // given
        var quota = QuotaTestBuilder.aQuota().build();

        // and
        given(factory.getRepository()).willReturn(repository);
        given(repository.findById(RATE_KEY)).willReturn(Optional.empty());
        given(properties.getLimit()).willReturn(QUOTA_LIMIT);
        given(builder.buildNewQuota(RATE_KEY, QUOTA_LIMIT)).willReturn(quota);
        given(repository.create(quota)).willReturn(quota);
        willDoNothing().given(producer).sendQuotaCreatedMessage(quota);

        // when
        service.retrieveQuota(RATE_KEY);

        // then
        verify(factory, times(2)).getRepository();
        verify(repository).findById(RATE_KEY);
        verify(repository).create(quota);
        verify(producer).sendQuotaCreatedMessage(quota);
    }

    @Test
    void updateShouldUpdateQuotaSuccessfully() {
        // given
        var quota = QuotaTestBuilder.aQuota().build();

        // and
        given(factory.getRepository()).willReturn(repository);
        given(repository.update(quota)).willReturn(quota);
        willDoNothing().given(producer).sendQuotaUpdatedMessage(quota);

        // when
        service.update(quota);

        // then
        verify(factory).getRepository();
        verify(repository).update(quota);
        verify(producer).sendQuotaUpdatedMessage(quota);
    }

    @Test
    void syncQuotaCreatedShouldCreateQuotaIfQuotaIsNotFound() {
        // given
        var quota = QuotaTestBuilder.aQuota().build();

        // and
        given(factory.getIdleRepository()).willReturn(repository);
        given(repository.findById(RATE_KEY)).willReturn(Optional.empty());
        given(repository.create(quota)).willReturn(quota);

        // when
        service.syncQuotaCreated(quota);

        // then
        verify(factory, times(2)).getIdleRepository();
        verify(repository).findById(RATE_KEY);
        verify(repository).create(quota);
    }

    @Test
    void syncQuotaCreatedShouldNotCreateQuotaIfQuotaExists() {
        // given
        var quota = QuotaTestBuilder.aQuota().build();
        var quotaRegistered = QuotaTestBuilder.aQuota().build();

        // and
        given(factory.getIdleRepository()).willReturn(repository);
        given(repository.findById(RATE_KEY)).willReturn(Optional.of(quotaRegistered));

        // when
        service.syncQuotaCreated(quota);

        // then
        verify(factory).getIdleRepository();
        verify(repository).findById(RATE_KEY);
        verify(repository, never()).create(any());
    }

    @Test
    void syncQuotaUpdatedShouldUpdateQuotaSuccessfully() {
        // given
        var quota = QuotaTestBuilder.aQuota().build();
        var quotaRegistered = QuotaTestBuilder.aQuota().build();

        // and
        given(factory.getIdleRepository()).willReturn(repository);
        given(repository.findById(RATE_KEY)).willReturn(Optional.of(quotaRegistered));
        given(repository.update(quota)).willReturn(quota);

        // when
        service.syncQuotaUpdated(quota);

        // then
        verify(factory, times(2)).getIdleRepository();
        verify(repository).findById(RATE_KEY);
        verify(repository).update(quota);
    }

    @Test
    void syncQuotaUpdatedShouldNotUpdateQuotaWhenUpdatedAtTimeIsAfter() {
        // given
        var quota = QuotaTestBuilder.aQuota().build();
        var quotaRegistered = QuotaTestBuilder.aQuota()
                .updatedAt(quota.getUpdatedAt().plusSeconds(10))
                .build();

        // and
        given(factory.getIdleRepository()).willReturn(repository);
        given(repository.findById(RATE_KEY)).willReturn(Optional.of(quotaRegistered));

        // when
        service.syncQuotaUpdated(quota);

        // then
        verify(factory).getIdleRepository();
        verify(repository).findById(RATE_KEY);
        verify(repository, never()).update(quota);
    }

    @Test
    void syncQuotaUpdateShouldCreateQuotaWhenQuotaIsNotFound() {
        // given
        var quota = QuotaTestBuilder.aQuota().build();

        // and
        given(factory.getIdleRepository()).willReturn(repository);
        given(repository.findById(RATE_KEY)).willReturn(Optional.empty());
        given(repository.create(quota)).willReturn(quota);

        // when
        service.syncQuotaUpdated(quota);

        // then
        verify(factory, times(2)).getIdleRepository();
        verify(repository).findById(RATE_KEY);
        verify(repository).create(quota);
    }
}
