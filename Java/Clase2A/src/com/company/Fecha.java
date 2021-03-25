package com.company;

import java.time.LocalDate;

public class Fecha {

    private LocalDate localDate;

    public Fecha() {
        this.localDate = LocalDate.now();
    }

    public Fecha(int year, int month, int day) {
        this.localDate = LocalDate.of(year, month, day);
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public void sumarUnDia() {
        this.localDate = localDate.plusDays(1);
    }

    public static boolean esCorrecta(int year, int month, int day) {
        boolean result = true;
        try {
            LocalDate.of(year, month, day);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @Override
    public String toString() {
        return "Fecha{" +
                "calendar=" + localDate +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(esCorrecta(2020, 80, 222));
        System.out.println(esCorrecta(2020, 2, 2));
    }

}