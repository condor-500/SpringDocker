package com.jjacome.springcloudmsvc.usuarios.controllers;

import com.jjacome.springcloudmsvc.usuarios.models.entity.Usuario;
import com.jjacome.springcloudmsvc.usuarios.services.UsuarioService;
import com.jjacome.springcloudmsvc.usuarios.vo.respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service ;

    @GetMapping
    public ResponseEntity<respuesta> findAllUsuarios (){

        return ResponseEntity.status(HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>  detalle (@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = service.findId(id);
        if(usuarioOptional.isPresent() ) {
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return  ResponseEntity.notFound().build() ;
    }

    @PostMapping
    public ResponseEntity<?> crear (@Valid @RequestBody Usuario usuario, BindingResult result){
        if(result.hasErrors()){
            return validar(result);}
        if(! usuario.getEmail().isEmpty() && service.findByEmail(usuario.getEmail()).isPresent()){
         return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje","Ya existe un usuario con ese correo"));

        }

        return  ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario)) ;
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> editar (@Valid @RequestBody Usuario usuario,BindingResult result ,@PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
          Optional<Usuario> user =  service.findId(id) ;
          if(user.isPresent()){
              Usuario usuariodb = user.get() ;
              if(!usuario.getEmail().equalsIgnoreCase(usuariodb.getEmail()) &&  service.findByEmail(usuario.getEmail()).isPresent()){
                  return ResponseEntity.badRequest()
                          .body(Collections.singletonMap("mensaje","Ya existe un usuario con ese correo"));

              }
              usuariodb.setNombre( usuario.getNombre());
              usuariodb.setEmail(usuario.getEmail());
              usuariodb.setPassword(usuario.getPassword() );
              return  ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuariodb));
          }
          return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  eliminar(@PathVariable Long id) {
        Optional<Usuario> userO = service.findId(id);
        if( userO.isPresent() ){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnoPorCurso(@RequestParam List<Long> ids) {
        return  ResponseEntity.ok(service.listarPorIds(ids));
    }


    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo "+ err.getField() + " "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
