package com.ulp.vigiaespacialarg.model;

import com.google.gson.annotations.SerializedName;

public class IssResponse {
    private String name;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("altitude")
    private double altitude;

    @SerializedName("velocity")
    private double velocity;

    // Getters
    public String getName() { return "ISS (ESTACIÓN ESPACIAL)"; }
    public double getAltitude() { return altitude; }
    public double getVelocity() { return velocity / 3600; } // Convertimos a km/s
}