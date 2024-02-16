package com.felipepossari.quota.quota.repository.elasticsearch.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "quota")
@Data
@Builder
public class QuotaIndex {

    @Id
    private String rateKey;

    @Field(type = FieldType.Integer, name = "quotaLimit")
    private Integer quotaLimit;

    @Field(type = FieldType.Integer, name = "remaining")
    private Integer remaining;

    @Field(type = FieldType.Date, name = "resetTime", format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime resetTime;

    @Field(type = FieldType.Date, name = "createdAt", format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, name = "updatedAt", format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updatedAt;
}
