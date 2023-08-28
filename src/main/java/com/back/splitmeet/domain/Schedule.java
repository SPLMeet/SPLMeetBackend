package com.back.splitmeet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Table(name = "schedule")
@Getter
@Setter
public class Schedule{
    @Id
    @Column(nullable = false)
    private Long teamId;

    private ZonedDateTime date;

    private String place;

    private Integer cost;
}