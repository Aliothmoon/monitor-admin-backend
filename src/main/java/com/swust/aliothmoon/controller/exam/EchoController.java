package com.swust.aliothmoon.controller.exam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {


    @RequestMapping("/echo")
    public String echo(String msg) {
        return msg;
    }
}
