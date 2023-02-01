package com.jjacome.springcloud.msvc.cursos.controllers;

import com.jjacome.springcloud.msvc.cursos.models.Usuario;
import com.jjacome.springcloud.msvc.cursos.models.entity.Curso;
import com.jjacome.springcloud.msvc.cursos.service.CursoService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class CursoController {

    @Autowired
   private CursoService  service ;

     @GetMapping
    public ResponseEntity<List<Curso>> listar(){
         return ResponseEntity.ok(service.listar());
     }

     @GetMapping("/{id}")
     public ResponseEntity<?> detalle( @PathVariable Long id) {
       Optional<Curso> o = service.porIdConUsuarios(id);
         //service.porIdConUsuarios(id); //service.porId(id);
         if(o.isPresent()){
             return ResponseEntity.ok(o.get());
         }
         return ResponseEntity.notFound().build();
     }

     @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso , BindingResult result) {
         if(result.hasErrors()){
             return validar(result);
         }
         Curso cursodb= service.guardar(curso);
         return ResponseEntity.status(HttpStatus.CREATED).body(cursodb);
     }

     @PutMapping("/{id}")
    public ResponseEntity<?> editar ( @Valid @RequestBody Curso curso, BindingResult result ,@PathVariable Long id ) {
         if(result.hasErrors()){
             return validar(result);
         }
         Optional<Curso> o = service.porId(id);
         if(o.isPresent()){
             Curso cursoDB = o.get();
             cursoDB.setNombre(curso.getNombre());
             return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDB));
         }
         return ResponseEntity.notFound().build();
     }

     @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar (@PathVariable Long id){
         Optional<Curso> o = service.porId(id);
         if(o.isPresent()){
             service.eliminar(o.get().getId());
             ResponseEntity.noContent().build();
         }
         return ResponseEntity.noContent().build();
     }

     @PutMapping("/asignar-usuario/{cursoId}")
     public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId ){
       Optional<Usuario> o ;
        try {
            o  =  service.asignarUsuario(usuario, cursoId);
        }catch (FeignException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("menseje","No existe el usuario" +
                            " por Id o existe error en la comunicacion : " + e.getMessage() ));
        }
       if(o.isPresent()){
           return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
       }
       return ResponseEntity.notFound().build();
     }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId ){
        Optional<Usuario> o ;
        try {
            o  =  service.crearUsuario(usuario, cursoId);
        }catch (FeignException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("menseje","No se puedo crear el usuario" +
                            " por Id o existe error en la comunicacion : " + e.getMessage() ));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId ){
        Optional<Usuario> o ;
        try {
            o  =  service.eliminarUsuario(usuario, cursoId);
        }catch (FeignException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("menseje","No existe el usuario" +
                            " por Id o existe error en la comunicacion : " + e.getMessage() ));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/eliminar-curso-usuario/{id}")
     public  ResponseEntity<?> elimnarCursoUsuarioPorID(@PathVariable Long id){
          service.eliminarCursoUsuarioPorId(id);
         return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo "+ err.getField() + " "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }






}
