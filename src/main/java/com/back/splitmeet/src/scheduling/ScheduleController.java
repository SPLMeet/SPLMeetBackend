package com.back.splitmeet.src.scheduling;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.src.scheduling.dto.ScheduleCommunityReq;
import com.back.splitmeet.src.scheduling.dto.ScheduleCommunityRes;
import com.back.splitmeet.src.scheduling.dto.ScheduleModifyReq;
import com.back.splitmeet.src.scheduling.dto.ScheduleModifyRes;
import com.back.splitmeet.util.BaseResponse;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private ScheduleModifyService scheduleModifyService;

	@GetMapping("/community")
	public BaseResponse<List<ScheduleCommunityRes>> inquireSchedule(@ModelAttribute ScheduleCommunityReq req) {
		List<ScheduleCommunityRes> scheduleCommunityRes = scheduleService.inquireSchedule(req);
		return new BaseResponse<>(scheduleCommunityRes);
	}

	@PostMapping("/modify")
	public BaseResponse<ScheduleModifyRes> modifySchedule(@RequestBody ScheduleModifyReq req) {
		ScheduleModifyRes scheduleModifyRes = scheduleModifyService.modifySchedule(req);
		return new BaseResponse<>(scheduleModifyRes);
	}

}
