package com.example.demo.modelos;



import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "carta")
public class Carta {
    
	public enum TipoItem {
        PLATILLO, BEBIDA, POSTRE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carta_id")
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private TipoItem tipo;

    private double precio;

    @NotNull
    private boolean disponible = true;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public TipoItem getTipo() { return tipo; }
    public void setTipo(TipoItem tipo) { this.tipo = tipo; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    
}

