package com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation;

import lombok.Setter;

import java.io.Serializable;


@Setter
public class EmailValidationResponse implements Serializable {


    private boolean format_valid;
    private boolean smtp_check;

    public boolean isFormatValid() {
        return format_valid;
    }

    public boolean isSmtpCheck() {
        return smtp_check;
    }
}
