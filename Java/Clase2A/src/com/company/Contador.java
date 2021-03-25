package com.company;

public class Contador {
    private int valor;

    public Contador(int valor) {
        this.valor = valor;
    }

    public Contador(Contador contador) {
        this.valor = contador.getValor();
    }

    public Contador() {
    }

    public void incrementar() {
        valor++;
    }

    public void decrementar() {
        if (this.valor > 0) {
            valor--;
        }
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
