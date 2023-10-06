package com.back.splitmeet.src.scheduling;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.back.splitmeet.src.scheduling.dto.ScheduleAddReq;
import com.back.splitmeet.src.scheduling.dto.ScheduleInquiryRes;
import com.back.splitmeet.src.scheduling.dto.ScheduleModifyReq;
import com.back.splitmeet.util.BaseResponse;
import com.back.splitmeet.util.BaseResponseStatus;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

	Queue<DeferredResult<BaseResponse<ScheduleInquiryRes>>> result = new ConcurrentLinkedQueue<>();

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private ScheduleRegisterService scheduleRegisterService;

	@GetMapping("/inquiry")
	public DeferredResult<BaseResponse<ScheduleInquiryRes>> inquireSchedule(@RequestParam(value = "Authorization") String accessToken, @RequestParam(value = "Option")Boolean option) {
		DeferredResult<BaseResponse<ScheduleInquiryRes>> deferredResult = new DeferredResult<>();

		if(option){
			ScheduleInquiryRes scheduleInquiryRes = scheduleService.inquireSchedule(accessToken);
			BaseResponse<ScheduleInquiryRes> response = new BaseResponse<>(scheduleInquiryRes);
			deferredResult.setResult(response);
		}
		else if(!option){
			result.add(deferredResult);
		}
		return deferredResult;
	}

	@PostMapping("/modify")
	public BaseResponse<BaseResponseStatus> modifySchedule(@RequestHeader("Authorization") String accessToken, @RequestBody ScheduleModifyReq req) {
		Boolean scheduleModifyRes = scheduleRegisterService.modifySchedule(req);
		if (scheduleModifyRes == null) {
			return new BaseResponse<>(BaseResponseStatus.SCHEDULE_NOT_CHANGED);
		} else {
			ScheduleInquiryRes scheduleInquiryRes = scheduleService.inquireSchedule(accessToken);
			BaseResponse<ScheduleInquiryRes> response = new BaseResponse<>(scheduleInquiryRes);

			for(DeferredResult<BaseResponse<ScheduleInquiryRes>> deferredResult : result){
				deferredResult.setResult(response);
				result.remove(deferredResult);
			}

			return new BaseResponse<>(BaseResponseStatus.SUCCESS);
		}
	}

	@PostMapping("/add")
	public BaseResponse<BaseResponseStatus> addSchedule(@RequestBody ScheduleAddReq req) {
		Boolean scheduleAddRes = scheduleRegisterService.addSchedule(req);
		if (scheduleAddRes == null) {
			return new BaseResponse<>(BaseResponseStatus.SCHEDULE_ADD_FAIL);
		}
		ScheduleInquiryRes scheduleInquiryRes = scheduleService.inquireSchedule(req.getAccessToken());
		BaseResponse<ScheduleInquiryRes> response = new BaseResponse<>(scheduleInquiryRes);

		for(DeferredResult<BaseResponse<ScheduleInquiryRes>> deferredResult : result){
			deferredResult.setResult(response);
			result.remove(deferredResult);
		}
		return new BaseResponse<>(BaseResponseStatus.SUCCESS);
	}

}
