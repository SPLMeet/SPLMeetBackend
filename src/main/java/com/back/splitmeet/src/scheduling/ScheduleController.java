package com.back.splitmeet.src.scheduling;

import com.back.splitmeet.src.scheduling.dto.ScheduleCommunityReq;
import com.back.splitmeet.src.scheduling.dto.ScheduleCommunityRes;
import com.back.splitmeet.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/community")
    public BaseResponse<List<ScheduleCommunityRes>> inquireSchedule(@RequestBody ScheduleCommunityReq req){
        List<ScheduleCommunityRes> scheduleCommunityRes = scheduleService.inquireSchedule(req);
        return new BaseResponse<>(scheduleCommunityRes);
    }
}
