package com.example.demo.repositorio;

import com.example.demo.modelos.Cocina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CocinaRepository extends JpaRepository<Cocina, Integer> {
	List<Cocina> findByNumeroMesa(int numeroMesa);
	void deleteByNumeroMesa(int numeroMesa);
}


