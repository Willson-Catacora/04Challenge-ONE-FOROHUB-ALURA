package com.alura.foroHub.domain.topico;

import com.alura.foroHub.domain.curso.Curso;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull Long id,
        String mensaje,
        String titulo,
        Curso curso
) {
}
