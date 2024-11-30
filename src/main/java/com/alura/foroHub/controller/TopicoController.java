package com.alura.foroHub.controller;

import com.alura.foroHub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepositorio topicoRepositorio;

    @PostMapping
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                             UriComponentsBuilder uriComponentsBuilder) {
        if (topicoRepositorio.existsByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje()))
            return new ResponseEntity<>("ERROR: Ya existe un tópico con el mismo título y mensaje", HttpStatus.BAD_REQUEST);
        else {
            Topico topico = topicoRepositorio.save(new Topico(datosRegistroTopico));
            DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico);
            URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(url).body(datosRespuestaTopico);
        }
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopicos>> listarTopicos(@PageableDefault(size = 10) Pageable pagina) {
        Pageable paginaOrdenada = PageRequest.of(pagina.getPageNumber(), pagina.getPageSize(), Sort.by(Sort.Order.asc("FechaDeCreacion")));
        return ResponseEntity.ok(topicoRepositorio.findByStatusTrue(paginaOrdenada).map(DatosListaTopicos::new));
    }
}
