package com.example.demo.modelos;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity
public class Venta {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venta_id")
    private Long id;

    private String nombre;
    private String detalle;
    private BigDecimal precio;

    @Column(name = "numero_mesa")
    private int numeroMesa;

    private LocalDateTime fecha = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocina_id", referencedColumnName = "cocina_id", nullable = true)
    private Cocina cocina;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public Cocina getCocina() { return cocina; }
    public void setCocina(Cocina cocina) { this.cocina = cocina; }
}
