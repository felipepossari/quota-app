package com.felipepossari.quota.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
public class SourceTimeConfig {

    @Bean
    @Qualifier("mysqlFromTime")
    public LocalTime mysqlFromTime(@Value("${mysql.from-time}") String mysqlFromTime) {
        var timeSplit = mysqlFromTime.split(":");
        return LocalTime.of(Integer.parseInt(timeSplit[0]),
                Integer.parseInt(timeSplit[1]));
    }

    @Bean
    @Qualifier("mysqlToTime")
    public LocalTime mysqlToTime(@Value("${mysql.to-time}") String mysqlToTime) {
        var timeSplit = mysqlToTime.split(":");
        return LocalTime.of(Integer.parseInt(timeSplit[0]),
                Integer.parseInt(timeSplit[1]));
    }
}
