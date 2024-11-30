package com.alura.foroHub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepositorio extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensaje(@NotBlank String titulo, @NotBlank String mensaje);

    Page<Topico> findByStatusTrue(Pageable pagina);
}
