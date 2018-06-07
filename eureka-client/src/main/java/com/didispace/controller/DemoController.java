package com.didispace.controller;

import com.common.util.SystemHWUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * @author : whuang
 */
@RestController
@RequestMapping(value = "/client")
public class DemoController {
    @ResponseBody
    @RequestMapping(value = "/hello/json", produces = SystemHWUtil.RESPONSE_CONTENTTYPE_JSON_UTF)
    public String jsonHello2(Model model, HttpServletRequest request, HttpServletResponse response
            , @RequestParam(required = false) String demo) {
        System.out.println("client 2222:");
        return "hello###";
    }
}
