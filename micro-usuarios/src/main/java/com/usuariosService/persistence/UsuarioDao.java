package com.usuariosService.persistence;


import com.usuariosService.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDao {

    // CREATE
    Usuario save(Usuario usuario);

    // READ ALL
    List<Usuario> findAll();

    // READ BY ID
    Optional<Usuario> findById(Long id);

    // READ BY EMAIL
    Optional<Usuario> findByEmail(String email);

    // READ BY USERNAME
    Optional<Usuario> findByUsername(String username);

    // UPDATE
    Usuario update(Usuario usuario);

    // DELETE
    boolean delete(Long id);
}
