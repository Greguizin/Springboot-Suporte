package com.example.demo.LAYERS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.LAYERS.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

  Usuario getUsuarioPorLogin(String login);
  
}
