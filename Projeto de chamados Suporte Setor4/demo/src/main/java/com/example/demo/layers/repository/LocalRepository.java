package com.example.demo.layers.repository;

import com.example.demo.layers.entities.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    // Método para buscar Local por nome
    Optional<Local> findByDepartamento(String departamento);

}
