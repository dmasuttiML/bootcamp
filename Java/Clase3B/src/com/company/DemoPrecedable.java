package com.company;

import com.company.Celular;

public class DemoPrecedable {
    public static void main(String[] args) {
        Precedable precedables[] = new Precedable[5];

        // Personas
        precedables[0] = new Persona("Diego",23524512);
        precedables[1] = new Persona("Franco",48924354);
        precedables[2] = new Persona("Juan",8908023);
        precedables[3] = new Persona("Pedro",32737127);
        precedables[4] = new Persona("Roberto",23499929);

        SortUtil.ordenar(precedables);
        for(int i=0; i<precedables.length;i++) {
            System.out.print(precedables[i]+(i<precedables.length-1?", ":"\n"));
        }

        // Celulares
        precedables[0] = new Celular(999999, (Persona) precedables[0]);
        precedables[1] = new Celular(444444, (Persona) precedables[1]);
        precedables[2] = new Celular(777777, (Persona) precedables[2]);
        precedables[3] = new Celular(111111, (Persona) precedables[3]);
        precedables[4] = new Celular(555555, (Persona) precedables[4]);

        SortUtil.ordenar(precedables);
        for(int i=0; i<precedables.length;i++) {
            System.out.print(precedables[i]+(i<precedables.length-1?", ":"\n"));
        }
    }
}
