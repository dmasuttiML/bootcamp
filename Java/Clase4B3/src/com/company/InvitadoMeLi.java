package com.company;

public class InvitadoMeLi extends Invitado {
    public InvitadoMeLi(String nombre) {
        super(nombre);
    }

    @Override
    public void comerTorta() {
        super.comerTorta();
        System.out.println(getNombre() + " dice: Viva la Chiqui!!");
    }
}
