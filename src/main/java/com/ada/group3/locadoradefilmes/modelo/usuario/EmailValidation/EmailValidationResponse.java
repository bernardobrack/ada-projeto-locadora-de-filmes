package com.ada.group3.locadoradefilmes.modelo.usuario.EmailValidation;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
public class EmailValidationResponse implements Serializable {


    private boolean format_valid;
    private boolean mx_found;


}
