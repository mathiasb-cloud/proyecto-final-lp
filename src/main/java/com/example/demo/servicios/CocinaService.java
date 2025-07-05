package com.example.demo.servicios;

import com.example.demo.modelos.Cocina;
import com.example.demo.repositorio.CocinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import java.util.List;

@Service
public class CocinaService {

    @Autowired
    private CocinaRepository cocinaRepository;

    
    public Cocina guardarPedido(Cocina cocina) {
        Cocina guardado = cocinaRepository.save(cocina);
        System.out.println("Guardado en BD: " + guardado.getId());
        return guardado;
    }


   
    public List<Cocina> listarCocina() {
        return cocinaRepository.findAll();
    }

    
    public void eliminarPedido(int id) {
        cocinaRepository.deleteById(id);
    }
    
    public List<Cocina> listarPorMesa(int mesa) {
        return cocinaRepository.findByNumeroMesa(mesa);
    }
    
    
    public Cocina obtenerPedidoPorId(int id) {
        Optional<Cocina> pedido = cocinaRepository.findById(id);
        return pedido.orElse(null);
    }
    
    @Transactional
    public void eliminarPedidosPorMesa(int numeroMesa) {
        cocinaRepository.deleteByNumeroMesa(numeroMesa);
    }

    

}
