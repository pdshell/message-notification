package com.mwt.wallet.message.notification.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class Home {
    @GetMapping("/")
    public String home() {
        return "redirect:doc.html";
    }
}
