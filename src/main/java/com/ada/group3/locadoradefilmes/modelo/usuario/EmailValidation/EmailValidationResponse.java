package com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailValidationResponse {

    private boolean format_valid;
    private boolean smtp_check;

    public boolean isFormatValid() {
        return format_valid;
    }

    public boolean isSmtpCheck() {
        return smtp_check;
    }
}
