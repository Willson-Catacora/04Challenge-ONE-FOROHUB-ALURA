package com.alura.foroHub.domain.topico;

import com.alura.foroHub.domain.curso.Curso;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepositorio extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensaje(@NotBlank String titulo, @NotBlank String mensaje);

    Page<Topico> findByStatusTrue(Pageable pagina);

    Page<Topico> findByStatusTrueAndCurso(Curso curso, Pageable pagina);
}
