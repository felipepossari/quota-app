package com.felipepossari.quota.unit.user;

import com.felipepossari.quota.common.DateTimeUtil;
import com.felipepossari.quota.common.config.DataSourcePeriodProperties;
import com.felipepossari.quota.user.repository.UserRepository;
import com.felipepossari.quota.user.repository.UserRepositoryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserRepositoryFactoryTest {

    @Mock
    UserRepository userMySqlRepository;

    @Mock
    UserRepository userEsRepository;

    @Mock
    DataSourcePeriodProperties properties;

    @Mock
    DateTimeUtil dateTimeUtil;

    UserRepositoryFactory factory;

    @BeforeEach
    void clearMocks() {
        factory = new UserRepositoryFactory(userMySqlRepository, userEsRepository, properties, dateTimeUtil);
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
        assertEquals(userMySqlRepository, repository);
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
        assertEquals(userMySqlRepository, repository);
    }

    @Test
    void getRepositoryShouldReturnEsRepositoryIfTimeIsOutOfDataSourcePeriodFrom() {
        // given
        given(dateTimeUtil.timeNowUtc()).willReturn(LocalTime.of(8, 59));
        given(properties.getFromTime()).willReturn(LocalTime.of(9, 0));

        // when
        var repository = factory.getRepository();

        // then
        assertEquals(userEsRepository, repository);
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
        assertEquals(userEsRepository, repository);
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
        assertEquals(userEsRepository, repository);
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
        assertEquals(userEsRepository, repository);
    }

    @Test
    void getIdleRepositoryShouldReturnMySqlRepositoryIfTimeIsOutOfDataSourcePeriodFrom() {
        // given
        given(dateTimeUtil.timeNowUtc()).willReturn(LocalTime.of(8, 59));
        given(properties.getFromTime()).willReturn(LocalTime.of(9, 0));

        // when
        var repository = factory.getIdleRepository();

        // then
        assertEquals(userMySqlRepository, repository);
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
        assertEquals(userMySqlRepository, repository);
    }
}
