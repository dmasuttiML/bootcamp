package com.company;

import java.util.ArrayList;

public class FuegoArtificialPack implements FuegoArtificial {
    private ArrayList<FuegoArtificial> fuegoArtificialList;

    public FuegoArtificialPack(){
        fuegoArtificialList = new ArrayList<FuegoArtificial>();
    }

    public FuegoArtificialPack(ArrayList<FuegoArtificial> fuegoArtificialList) {
        this.fuegoArtificialList = fuegoArtificialList;
    }

    public void add(FuegoArtificial fa){
        fuegoArtificialList.add(fa);
    }

    @Override
    public void explotar() {
        for (FuegoArtificial e: fuegoArtificialList) {
            e.explotar();
        }
    }
}
