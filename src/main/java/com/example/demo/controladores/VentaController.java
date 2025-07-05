package com.example.demo.controladores;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.modelos.Venta;
import com.example.demo.repositorio.VentaRepository;

import org.springframework.ui.Model;
@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @GetMapping
    public String verVentas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Model model) {

        List<Venta> ventas;

        if (desde != null && hasta != null) {
            LocalDateTime desdeHora = desde.atStartOfDay();
            LocalDateTime hastaHora = hasta.atTime(23, 59, 59);
            ventas = ventaRepository.findByFechaBetween(desdeHora, hastaHora);
        } else {
            ventas = ventaRepository.findAll();
        }

        BigDecimal total = ventas.stream()
                .map(Venta::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("ventas", ventas);
        model.addAttribute("total", total);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);

        return "ventas"; 
    }
    
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<?> registrarVentas(@RequestBody List<Venta> ventas) {
        try {
            for (Venta venta : ventas) {
                venta.setFecha(LocalDateTime.now());
                ventaRepository.save(venta);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar ventas");
        }
    }

}

