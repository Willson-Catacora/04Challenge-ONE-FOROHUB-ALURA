package com.alura.foroHub.controller;

import com.alura.foroHub.domain.curso.Curso;
import com.alura.foroHub.domain.topico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
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

    @GetMapping("/curso/{curso}")
    public ResponseEntity<Page<DatosListaTopicos>> listarTopicosPorCurso(@PathVariable Curso curso, @PageableDefault(size = 10) Pageable pagina) {
        System.out.println(curso);
        return ResponseEntity.ok(topicoRepositorio.findByStatusTrueAndCurso(curso, pagina).map(DatosListaTopicos::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> detalleTopico(@PathVariable Long id){
        Topico topico = topicoRepositorio.getReferenceById(id);
        DatosDetalleTopico datosDetalleTopico = new DatosDetalleTopico(topico);
        return ResponseEntity.ok().body(datosDetalleTopico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        var topico = topicoRepositorio.getReferenceById(id);
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok().body(new DatosRespuestaTopico(topico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> eliminartopico(@PathVariable Long id){
        var topico = topicoRepositorio.getReferenceById(id);
        topico.desactivaStatus();
        return ResponseEntity.noContent().build();
    }
}
