package com.example.demo.repositorio;


import com.example.demo.modelos.Carta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartaRepository extends JpaRepository<Carta, Long> {
    List<Carta> findByDisponibleTrue();
    List<Carta> findByTipo(Carta.TipoItem tipo);
}
