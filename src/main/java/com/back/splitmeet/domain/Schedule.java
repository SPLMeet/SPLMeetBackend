package com.back.splitmeet.domain;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "schedule")
@Getter
@Setter
public class Schedule {
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long scheduleId;

	@Column(nullable = false)
	private Long teamId;

	private ZonedDateTime date;

	private String place;

	private Integer cost;
}