package com.back.splitmeet.src.scheduling.dto;

import java.util.List;

import com.back.splitmeet.domain.Schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ScheduleInquiryRes {
	private List<Schedule> schedule;
}
