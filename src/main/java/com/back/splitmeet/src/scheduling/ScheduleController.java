package com.back.splitmeet.src.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.splitmeet.src.scheduling.dto.ScheduleAddReq;
import com.back.splitmeet.src.scheduling.dto.ScheduleInquiryRes;
import com.back.splitmeet.src.scheduling.dto.ScheduleModifyReq;
import com.back.splitmeet.util.BaseResponse;
import com.back.splitmeet.util.BaseResponseStatus;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private ScheduleRegisterService scheduleRegisterService;

	@GetMapping("/inquiry")
	public BaseResponse<ScheduleInquiryRes> inquireSchedule(@RequestParam(value = "Authorization") String accessToken) {
		ScheduleInquiryRes scheduleInquiryRes = scheduleService.inquireSchedule(accessToken);
		return new BaseResponse<>(scheduleInquiryRes);
	}

	@PostMapping("/modify")
	public BaseResponse<BaseResponseStatus> modifySchedule(@RequestBody ScheduleModifyReq req) {
		Boolean scheduleModifyRes = scheduleRegisterService.modifySchedule(req);
		if (scheduleModifyRes == null) {
			return new BaseResponse<>(BaseResponseStatus.SCHEDULE_NOT_CHANGED);
		} else {
			return new BaseResponse<>(BaseResponseStatus.SUCCESS);
		}

	}

	@PostMapping("/add")
	public BaseResponse<BaseResponseStatus> addSchedule(@RequestBody ScheduleAddReq req) {
		Boolean scheduleAddRes = scheduleRegisterService.addSchedule(req);
		if (scheduleAddRes == null) {
			return new BaseResponse<>(BaseResponseStatus.SCHEDULE_ADD_FAIL);
		}
		return new BaseResponse<>(BaseResponseStatus.SUCCESS);
	}

}
