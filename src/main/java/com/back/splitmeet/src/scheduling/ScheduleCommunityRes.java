package com.back.splitmeet.src.scheduling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Data
@Getter @Setter
@AllArgsConstructor
public class ScheduleCommunityRes {
    private String date;

    private String time;

    private String place;

    private Integer cost;
}
