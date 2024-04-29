package com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailValidationService {

    @Value("${mailboxlayer.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public EmailValidationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean validarEmail(String email) {
        String url = "http://apilayer.net/api/check?access_key=" + apiKey + "&email=" + email;

        EmailValidationResponse response = restTemplate.getForObject(url, EmailValidationResponse.class);

        return response.isSmtpCheck() && response.isFormatValid();
    }
}
