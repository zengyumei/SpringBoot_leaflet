package com.hbase;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/openlayerstest")
    public String say(){ return "hello"; }
}
