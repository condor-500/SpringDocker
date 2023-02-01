package com.jjacome.springcloudmsvc.usuarios.services;

import com.jjacome.springcloudmsvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll ();
    Optional<Usuario> findId (Long id) ;
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    Optional<Usuario> findByEmail(String email);

    boolean existePorEmail(String email);

    List<Usuario> listarPorIds(Iterable<Long> ids);



}
