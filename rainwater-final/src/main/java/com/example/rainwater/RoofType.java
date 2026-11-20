package com.example.rainwater;

import java.util.Locale;

public enum RoofType {
    CONCRETE(0.80),
    METAL(0.90),
    TILE(0.75),
    CEMENT_SHEET(0.85);

    private final double runoffCoefficient;

    RoofType(double runoffCoefficient) {
        this.runoffCoefficient = runoffCoefficient;
    }

    public static RoofType fromInput(String roofTypeValue) {
        if (roofTypeValue == null) {
            return null;
        }

        String normalized = roofTypeValue.trim()
                .replaceAll("[^A-Za-z0-9]", "_")
                .toUpperCase(Locale.ROOT)
                .replaceAll("_+", "_")
                .replaceAll("^_+|_+$", "");

        if (normalized.isEmpty()) {
            return null;
        }

        return switch (normalized) {
            case "CONCRETE" -> CONCRETE;
            case "METAL", "METAL_SHEET" -> METAL;
            case "TILE" -> TILE;
            case "CEMENT", "CEMENT_SHEET", "CEMENTSHEET" -> CEMENT_SHEET;
            default -> null;
        };
    }

    public double getRunoffCoefficient() {
        return runoffCoefficient;
    }
}
