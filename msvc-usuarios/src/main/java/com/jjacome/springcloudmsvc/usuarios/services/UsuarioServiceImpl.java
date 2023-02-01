package com.jjacome.springcloudmsvc.usuarios.services;

import com.jjacome.springcloudmsvc.usuarios.clients.CursoClienteRest;
import com.jjacome.springcloudmsvc.usuarios.models.entity.Usuario;
import com.jjacome.springcloudmsvc.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository  usuarioRepo;

    @Autowired
    private CursoClienteRest client;


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)

    public Optional<Usuario> findId(Long id) {
        return usuarioRepo.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
         usuarioRepo.deleteById(id);
         client.elimnarCursoUsuarioPorID(id);

    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepo.porEmail(email);
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepo.existsByEmail(email);
    }

    @Override
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) usuarioRepo.findAllById(ids);
    }


}
