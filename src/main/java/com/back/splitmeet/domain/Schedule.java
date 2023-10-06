package com.back.splitmeet.domain;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "schedule")
@NoArgsConstructor
@Getter
@Setter
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long scheduleId;

	@Column(nullable = false)
	private Long teamId;

	@Column()
	private ZonedDateTime startTime;

	private ZonedDateTime endTime;

	private String place;

	private Integer cost;
}