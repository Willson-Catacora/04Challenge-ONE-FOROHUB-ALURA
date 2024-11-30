package com.alura.foroHub.domain.topico;

import com.alura.foroHub.domain.curso.Curso;

import java.time.LocalDateTime;
import java.util.Optional;

public record DatosDetalleTopico(
        Long Id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        Boolean status,
        String autor,
        Curso curso
) {
    public DatosDetalleTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaDeCreacion(),
                topico.getStatus(), topico.getAutor(), topico.getCurso());
    }
}
