package com.alura.foroHub.domain.topico;

import java.time.LocalDateTime;

public record DatosListaTopicos(
        Long Id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion
) {
    public DatosListaTopicos(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaDeCreacion());
    }
}
