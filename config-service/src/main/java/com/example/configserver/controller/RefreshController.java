package com.example.configserver.controller;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RefreshController {

    @PostMapping("/refreshAll")
    public void refreshAll() {
        String url = "http://localhost:8888/monitor";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println("요청 옴");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("path", "*");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        restTemplate.postForEntity(url, request, String.class);
    }
}
