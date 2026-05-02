package com.ulp.vigiaespacialarg.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "satellites")
public class Satellite {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double height;
    private double velocity;
    private String status;

    public Satellite(String name, double height) {
        this.name = name;
        // Sanitización de entrada: evita altitudes negativas
        this.height = (height < 0) ? 0 : height;
        this.velocity = calculateVelocity(this.height);

        // Lógica de Negocio: Validación de Rango Orbital
        if (this.height < 160) {
            this.status = "ALERTA: DEGRADACIÓN ORBITAL (CRÍTICO)";
        } else if (this.height > 40000) {
            this.status = "FUERA DE RANGO: ÓRBITA NO CATALOGADA";
        } else {
            this.status = "SEÑAL NOMINAL (ESTABLE)";
        }
    }

    private double calculateVelocity(double h) {
        final double G = 6.674e-11;
        final double M = 5.972e24;
        final double R = 6371000;
        return Math.sqrt((G * M) / (R + (h * 1000))) / 1000;
    }

    // Getters y Setters necesarios para Room
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public double getVelocity() { return velocity; }
    public void setVelocity(double velocity) { this.velocity = velocity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}