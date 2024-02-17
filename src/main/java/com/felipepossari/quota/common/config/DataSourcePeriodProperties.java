package com.felipepossari.quota.common.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
@ConfigurationProperties(prefix = "datasource-period")
@NoArgsConstructor
@Getter
public class DataSourcePeriodProperties {

    private LocalTime fromTime;
    private LocalTime toTime;

    public void setFromTime(String fromTime){
        var timeSplit = fromTime.split(":");
        this.fromTime = LocalTime.of(Integer.parseInt(timeSplit[0]),
                Integer.parseInt(timeSplit[1]));
    }

    public void setToTime(String toTime){
        var timeSplit = toTime.split(":");
        this.toTime = LocalTime.of(Integer.parseInt(timeSplit[0]),
                Integer.parseInt(timeSplit[1]));
    }
}
