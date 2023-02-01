package com.jjacome.springcloud.msvc.cursos.service;

import com.jjacome.springcloud.msvc.cursos.clients.UsuarioClienteRest;
import com.jjacome.springcloud.msvc.cursos.models.Usuario;
import com.jjacome.springcloud.msvc.cursos.models.entity.Curso;
import com.jjacome.springcloud.msvc.cursos.models.entity.CursoUsuario;
import com.jjacome.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClienteRest cliente ;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> o = cursoRepository.findById(id);
        if(o.isPresent()){
            Curso curso= o.get();
            if(!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids = curso.getCursoUsuarios().stream()
                        .map(cu -> cu.getUsuarioId() )
                        .collect(Collectors.toList());
                List<Usuario> usuarios = cliente.obtenerAlumnosPorCurso(ids);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
            cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        cursoRepository.elimianrCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMsv = cliente.detalle(usuario.getId());
            Curso curso = o.get();
            CursoUsuario cursoUsuario= new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsv.getId());
            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsv);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioNuevoMsv = cliente.crear (usuario);
            Curso curso = o.get();
            CursoUsuario cursoUsuario= new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsv.getId());
            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioNuevoMsv);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMsv = cliente.detalle(usuario.getId());
            Curso curso = o.get();
            CursoUsuario cursoUsuario= new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsv.getId());
            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsv);
        }
        return Optional.empty();
    }
}
