package com.felipepossari.quota.unit.quota;

import com.felipepossari.quota.quota.QuotaEventHandler;
import com.felipepossari.quota.quota.QuotaParser;
import com.felipepossari.quota.quota.QuotaService;
import com.felipepossari.quota.unit.base.EventTestBuilder;
import com.felipepossari.quota.unit.base.QuotaTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.felipepossari.quota.common.event.EventType.QUOTA_CREATED;
import static com.felipepossari.quota.common.event.EventType.QUOTA_UPDATED;
import static com.felipepossari.quota.common.event.EventType.USER_UPDATED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuotaEventHandlerTest {

    @Mock
    QuotaParser parser;

    @Mock
    QuotaService service;

    @InjectMocks
    QuotaEventHandler eventHandler;

    @Test
    void handleEventShouldSyncQuotaCreatedWhenEventIsQuotaCreated() {
        // given
        var event = EventTestBuilder.event().eventType(QUOTA_CREATED).build();
        var quota = QuotaTestBuilder.aQuota().build();

        // and
        given(parser.parse(event.getData())).willReturn(quota);
        willDoNothing().given(service).syncQuotaCreated(quota);

        // when
        eventHandler.handleEvent(event);

        // then
        verify(service).syncQuotaCreated(quota);
    }

    @Test
    void handleEventShouldSyncQuotaUpdatedWhenEventIsQuotaUpdated() {
        // given
        var event = EventTestBuilder.event().eventType(QUOTA_UPDATED).build();
        var quota = QuotaTestBuilder.aQuota().build();

        // and
        given(parser.parse(event.getData())).willReturn(quota);
        willDoNothing().given(service).syncQuotaUpdated(quota);

        // when
        eventHandler.handleEvent(event);

        // then
        verify(service).syncQuotaUpdated(quota);
    }

    @Test
    void handleEventShouldDoNothingWhenEventIsNotReadyToBeHandled() {
        // given
        var event = EventTestBuilder.event().eventType(USER_UPDATED).build();

        // when
        eventHandler.handleEvent(event);

        // then
        verify(service, never()).syncQuotaCreated(any());
        verify(service, never()).syncQuotaUpdated(any());
    }
}
