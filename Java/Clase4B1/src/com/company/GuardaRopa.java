package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuardaRopa {
    private HashMap<Integer, ArrayList<Prenda>> prendas = new HashMap<>();
    private Integer contador = 0;

    private Integer getNewIndex(){
        return  contador++;
    }

    public Integer guardarPrendas(ArrayList<Prenda> listaDePrenda){
        Integer index = getNewIndex();
        prendas.put(index, listaDePrenda);
        return index;
    }

    public void mostrarPrendas(){
        prendas.forEach((k,v) -> {
            for (Prenda p: v) {
                System.out.println(k + ": " + p.toString());
            }
        });
    }

    public ArrayList<Prenda> devolverPrendas(Integer numero){
        ArrayList<Prenda> listaDePrenda = prendas.get(numero);
        prendas.remove(numero);
        return listaDePrenda;
    }
}
