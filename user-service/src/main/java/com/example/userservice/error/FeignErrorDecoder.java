package com.example.userservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                break;
            case 404:
                if (methodKey.contains("getOrder")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), "User's orders is empty.");
                }
                break;
            default:
                return new Exception(response.reason());
        }

        return null;
    }
}
