package com.example.secondservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/second-service")
@RequiredArgsConstructor
public class SecondServiceController {

    private final Environment env;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Second Service.";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        log.info("header = {}", header);
        return "Hello World in Second Service.";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port = {}", request.getServerPort());
        return String.format("Hi, there. This is a message from First Service on PORT %s",
                env.getProperty("local.server.port"));
    }
}
