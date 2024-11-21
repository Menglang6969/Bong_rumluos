package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class IndexController {
    @GetMapping(value = {"api/doc","api/docs"})
    public RedirectView apiDoc(){
        return new RedirectView("/swagger-ui/index.html");
    }
}
