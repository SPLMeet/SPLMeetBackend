package com.back.splitmeet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
public class controller {
    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public String restTest(@RequestParam String str) {
        return str + "test done!!";
    }

    @RequestMapping(value = "/api/groupBuy", method = RequestMethod.POST)
    public String restGroupBuy(@RequestParam Long localID, @RequestParam Integer peopleNum) {
        return "done";
    }
}