package com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@FeignClient(value = "teste", url = "http://apilayer.net", path = "/api/check")
public interface EmailValidationService {


    @GetMapping(params = {"email","access_key"})
    EmailValidationResponse validarEmail(@RequestParam("email") String email, @RequestParam("access_key") String accessKey);
}
