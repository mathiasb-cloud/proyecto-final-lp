package com.example.demo.modelos;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
public class Cocina {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cocina_id")
    private int id;

    private String nombre;
    private String detalle;
    private boolean activo;
    private int numeroMesa;

    @Column(nullable = false)
    private BigDecimal precio;

    // Constructores
    public Cocina() {}

    public Cocina(int numeroMesa, String nombre, String detalle, boolean activo) {
        this.nombre = nombre;
        this.detalle = detalle;
        this.activo = activo;
        this.numeroMesa = numeroMesa;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}
