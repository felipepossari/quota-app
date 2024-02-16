package com.felipepossari.quota.user.repository.elasticsearch.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "user")
@Data
@Builder
public class UserIndex {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "firstName")
    private String firstName;

    @Field(type = FieldType.Text, name = "lastName")
    private String lastName;

    @Field(type = FieldType.Date, name = "lastLoginTimeUtc", format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime lastLoginTimeUtc;

    @Field(type = FieldType.Date, name = "createdAt", format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, name = "updatedAt", format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updatedAt;
}
