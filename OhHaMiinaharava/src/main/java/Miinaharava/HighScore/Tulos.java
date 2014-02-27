/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miinaharava.HighScore;

import java.io.Serializable;

/**
 *
 *Pelituloksen tiedot. 
 */
public class Tulos implements Comparable <Tulos>, Serializable{
    private static final long serialVersionUID = -6220082138832090333L;

    private int aika;
    private int klikkaukset;

    public Tulos(int aika, int klikkaukset) {
        this.aika = aika;
        this.klikkaukset = klikkaukset;
    }

    public int getAika() {
        return aika;
    }

    public int getKlikkaukset() {
        return klikkaukset;
    }

    @Override
    public int compareTo(Tulos o) {
        return this.aika - o.aika;
    }


    
}
