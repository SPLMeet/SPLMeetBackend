package com.back.splitmeet.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class restController {
	// 테스트 api
	@RequestMapping(value = "/api/test", method = RequestMethod.GET)
	public String restTest(@RequestParam String str) {
		return str + "test done!!";
	}

	//  정산하기 api
	@RequestMapping(value = "/api/split/select", method = RequestMethod.GET)
	public String restSplit(@RequestParam String test) {
		return test + "test";
	}
}