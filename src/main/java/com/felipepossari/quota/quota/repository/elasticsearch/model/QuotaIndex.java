package com.felipepossari.quota.quota.repository.elasticsearch.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

import static com.felipepossari.quota.quota.QuotaConstants.QUOTA_INDEX_NAME;

@Document(indexName = QUOTA_INDEX_NAME)
@Data
@Builder
public class QuotaIndex {

    @Id
    private String rateKey;

    @Field(type = FieldType.Integer)
    private Integer quotaLimit;

    @Field(type = FieldType.Integer)
    private Integer remaining;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime resetTime;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updatedAt;
}
