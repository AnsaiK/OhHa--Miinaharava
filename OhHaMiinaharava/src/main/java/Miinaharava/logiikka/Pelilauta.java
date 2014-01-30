/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Miinaharava.logiikka;

import java.util.Random;

public class Pelilauta {

    private int miinojenLkm;
    private Ruutu[][] ruudut;
    Random random;
    private int kentanKoko;
    
   
    public Pelilauta(int leveys, int miinojenLkm) {
        this.kentanKoko = leveys;
        this.miinojenLkm = miinojenLkm;
        this.ruudut = new Ruutu[this.kentanKoko][this.kentanKoko];
        this.random = new Random();
        luoLaudanRuudut();
        arvoMiinat();
        laskeRuutujenYmparoivatMiinat();
    }

    private void luoLaudanRuudut() {
        for (int x = 0; x < kentanKoko; x++) {
            for (int y = 0; y < kentanKoko; y++) {
                this.ruudut[x][y] = new Ruutu(x, y);
            }
        }
    }

    public Ruutu[][] getRuudut() {  // teestausta varten
        return ruudut;
    }

    public int getKentanKoko() {
        return this.kentanKoko;
    }
    
    public int getMiinojenlkm () {
        return this.miinojenLkm;
    }

    public void arvoMiinat() {
        int pommitJaljella = this.miinojenLkm;

        while (pommitJaljella > 0) {
            int rX = random.nextInt(kentanKoko);
            int rY = random.nextInt(kentanKoko);

            if (this.ruudut[rX][rY].getMiina() == false) {
                this.ruudut[rX][rY].setMiina(true);
                pommitJaljella--;
            }
        }
    }

    public void PiirraTekstiLauta() {   // testaukseen
        String lauta = "";

        for (int i = 0; i < kentanKoko; i++) {
            if (i > 0) {
                lauta += "\n";
            }
            for (int j = 0; j < kentanKoko; j++) {
                lauta += this.ruudut[i][j].toString() + " ";
            }
        }
        System.out.println(lauta);
    }
    
    public void laskeRuutujenYmparoivatMiinat() {
        for (int x = 0; x < kentanKoko; x++) {
            for (int y = 0; y < ruudut.length; y++) {
                ruudut[x][y].setYmparoivienLkm(haravoiYmparoivatMiinat(ruudut[x][y]));
            }
        }
    }

    public int haravoiYmparoivatMiinat(Ruutu ruutu) {

        int miinojenMaara = 0;

        int ruutuX = ruutu.getX();
        int ruutuY = ruutu.getY();

        int etsintarajaX = ruutuX + 2;
        int etsintarajaY = ruutuY + 2;

        if (ruutuX > 0) {
            ruutuX = ruutuX - 1;
            etsintarajaX = ruutuX + 3;
        }
        if (ruutuY > 0) {
            ruutuY = ruutuY - 1;
            etsintarajaY = ruutuY + 3;
        }

        for (int x = ruutuX; x < etsintarajaX && x < kentanKoko; x++) {
            for (int y = ruutuY; y < etsintarajaY && y < kentanKoko; y++) {
                if (ruudut[x][y].getMiina() == true) {
                    miinojenMaara++;
                }
            }
        }
             
        return miinojenMaara;
    }
}
