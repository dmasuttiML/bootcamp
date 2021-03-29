package com.example.demo.services;

import com.example.demo.dtos.CasaDTO;
import com.example.demo.dtos.HabitacionDTO;

public class CasaService {
    public int retornarMetros(CasaDTO casa){
        int metros = 0;
        for (HabitacionDTO habitacion : casa.getHabitaciones()) {
            metros += habitacion.calcularArea();
        }
        return metros;
    }

    public int getValorCasa(CasaDTO casa) {
        if (casa.getMetrosCuadrados() == 0){
            casa.setMetrosCuadrados(retornarMetros(casa));
        }
        return casa.getMetrosCuadrados() * 800;
    }

    public HabitacionDTO habitacionGrande(CasaDTO casa){
        HabitacionDTO habitacionMayor = null;
        for (HabitacionDTO habitacion : casa.getHabitaciones()) {
            if (habitacionMayor == null || habitacion.calcularArea() > habitacionMayor.calcularArea()){
                habitacionMayor = habitacion;
            }
        }
        return habitacionMayor;
    }
}
