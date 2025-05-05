package com.example.bcsd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @ResponseBody
    @GetMapping(value = {"/introduce", "/introduce/{name}"})
    public String introduce(@RequestParam(name="name", required=false, defaultValue="이경표") String name) {
        return "안녕하세요 제 이름은 " + name + "입니다!";
    }

    @ResponseBody
    @GetMapping("/json")
    public Json json() {
        Json json = new Json(22, "이경표");
        return json;
    }
}
