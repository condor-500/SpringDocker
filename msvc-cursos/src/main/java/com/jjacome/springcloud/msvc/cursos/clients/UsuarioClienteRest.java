package com.jjacome.springcloud.msvc.cursos.clients;

import com.jjacome.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "mscv-usuarios",url = "localhost:8001")
public interface UsuarioClienteRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id );

    @PostMapping("/")
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-por-curso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);




}
