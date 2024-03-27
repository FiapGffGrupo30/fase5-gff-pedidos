package br.fiap.gff.orders.util;

public class Coalesce {
    private Coalesce() {
    }

    public static <T> T of(T target, T defaultValue) {
        return target != null ? target : defaultValue;
    }
}
