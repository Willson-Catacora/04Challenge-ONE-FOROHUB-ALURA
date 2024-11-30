package com.alura.foroHub.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long Id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion
) {
    public DatosRespuestaTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaDeCreacion());
    }
}
