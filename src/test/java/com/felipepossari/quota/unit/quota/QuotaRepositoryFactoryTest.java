package com.felipepossari.quota.unit.quota;

import com.felipepossari.quota.common.DateTimeUtil;
import com.felipepossari.quota.common.config.DataSourcePeriodProperties;
import com.felipepossari.quota.quota.repository.QuotaRepository;
import com.felipepossari.quota.quota.repository.QuotaRepositoryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuotaRepositoryFactoryTest {

    @Mock
    QuotaRepository quotaMySqlRepository;

    @Mock
    QuotaRepository quotaEsRepository;

    @Mock
    DataSourcePeriodProperties properties;

    @Mock
    DateTimeUtil dateTimeUtil;

    QuotaRepositoryFactory factory;

    @BeforeEach
    void clearMocks() {
        factory = new QuotaRepositoryFactory(quotaMySqlRepository, quotaEsRepository, properties, dateTimeUtil);
    }

    @Test
    void getRepositoryShouldReturnMySqlRepositoryIfTimeIsWithinDataSourcePeriodFrom() {
        // given
        given(dateTimeUtil.timeNowUtc()).willReturn(LocalTime.of(9, 0));
        given(properties.getFromTime()).willReturn(LocalTime.of(9, 0));
        given(properties.getToTime()).willReturn(LocalTime.of(17, 0));

        // when
        var repository = factory.getRepository();

        // then
        assertEquals(quotaMySqlRepository, repository);
    }

    @Test
    void getRepositoryShouldReturnMySqlRepositoryIfTimeIsWithinDataSourcePeriodTo() {
        // given
        given(dateTimeUtil.timeNowUtc()).willReturn(LocalTime.of(17, 0));
        given(properties.getFromTime()).willReturn(LocalTime.of(9, 0));
        given(properties.getToTime()).willReturn(LocalTime.of(17, 0));

        // when
        var repository = factory.getRepository();

        // then
        assertEquals(quotaMySqlRepository, repository);
    }

    @Test
    void getRepositoryShouldReturnEsRepositoryIfTimeIsOutOfDataSourcePeriodFrom() {
        // given
        given(dateTimeUtil.timeNowUtc()).willReturn(LocalTime.of(8, 59));
        given(properties.getFromTime()).willReturn(LocalTime.of(9, 0));

        // when
        var repository = factory.getRepository();

        // then
        assertEquals(quotaEsRepository, repository);
    }

    @Test
    void getRepositoryShouldReturnEsRepositoryIfTimeIsOutOfDataSourcePeriodTo() {
        // given
        given(dateTimeUtil.timeNowUtc()).willReturn(LocalTime.of(17, 1));
        given(properties.getFromTime()).willReturn(LocalTime.of(9, 0));
        given(properties.getToTime()).willReturn(LocalTime.of(17, 0));

        // when
        var repository = factory.getRepository();

        // then
        assertEquals(quotaEsRepository, repository);
    }

    @Test
    void getIdleRepositoryShouldReturnEsRepositoryIfTimeIsWithinDataSourcePeriodFrom() {
        // given
        given(dateTimeUtil.timeNowUtc()).willReturn(LocalTime.of(9, 0));
        given(properties.getFromTime()).willReturn(LocalTime.of(9, 0));
        given(properties.getToTime()).willReturn(LocalTime.of(17, 0));

        // when
        var repository = factory.getIdleRepository();

        // then
        assertEquals(quotaEsRepository, repository);
    }

    @Test
    void getIdleRepositoryShouldReturnEsRepositoryIfTimeIsWithinDataSourcePeriodTo() {
        // given
        given(dateTimeUtil.timeNowUtc()).willReturn(LocalTime.of(17, 0));
        given(properties.getFromTime()).willReturn(LocalTime.of(9, 0));
        given(properties.getToTime()).willReturn(LocalTime.of(17, 0));

        // when
        var repository = factory.getIdleRepository();

        // then
        assertEquals(quotaEsRepository, repository);
    }

    @Test
    void getIdleRepositoryShouldReturnMySqlRepositoryIfTimeIsOutOfDataSourcePeriodFrom() {
        // given
        given(dateTimeUtil.timeNowUtc()).willReturn(LocalTime.of(8, 59));
        given(properties.getFromTime()).willReturn(LocalTime.of(9, 0));

        // when
        var repository = factory.getIdleRepository();

        // then
        assertEquals(quotaMySqlRepository, repository);
    }

    @Test
    void getIdleRepositoryShouldReturnMySqlRepositoryIfTimeIsOutOfDataSourcePeriodTo() {
        // given
        given(dateTimeUtil.timeNowUtc()).willReturn(LocalTime.of(17, 1));
        given(properties.getFromTime()).willReturn(LocalTime.of(9, 0));
        given(properties.getToTime()).willReturn(LocalTime.of(17, 0));

        // when
        var repository = factory.getIdleRepository();

        // then
        assertEquals(quotaMySqlRepository, repository);
    }
}
