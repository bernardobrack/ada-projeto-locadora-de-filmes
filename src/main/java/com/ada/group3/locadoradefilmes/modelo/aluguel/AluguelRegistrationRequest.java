package com.ada.group3.locadoradefilmes.modelo.aluguel;

import java.util.UUID;

public record AluguelRegistrationRequest(
        String usuarioLogin,
        UUID filmeUuid
) {
}
