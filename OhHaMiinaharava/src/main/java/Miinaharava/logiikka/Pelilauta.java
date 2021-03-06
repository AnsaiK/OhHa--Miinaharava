package Miinaharava.logiikka;

import java.util.Random;

/**
 * Luokka sisältää Pelikentän muodostamiseen tarvittavat toiminnot.
 *
 */
public class Pelilauta {

    private int miinojenLkm;
    private Ruutu[][] ruudut;

    Random random;
    private int kentanKoko;

    /**
     * Konstruktori luo laudan. Se kutsuu kolmea metodia laudan ruutujen
     * luontia, miinojen arvomista, ja ympäröivien miinojen laskua varten.
     *
     */
    public Pelilauta(int leveys, int miinojenLkm) {
        this.kentanKoko = leveys;
        this.miinojenLkm = miinojenLkm;
        this.ruudut = new Ruutu[this.kentanKoko][this.kentanKoko];
        this.random = new Random();
        luoLaudanRuudut();
        arvoMiinat();
        laskeRuutujenYmparoivatMiinat();
    }

    /**
     * Luo laudan ruudut kentän koon mukaan. Ruudut talletaan kaksiuloitteiseen
     * taulukkoon.
     */
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

    public int getMiinojenlkm() {
        return this.miinojenLkm;
    }

    /**
     * Asettaa miinojen lukumäärän verran miinoja satunnaisiin ruutuihin.
     *
     */
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

    /**
     * Metodi käy läpi kentän ruudut. Asettaa jokaiselle ruudulle ympäroivien
     * miinojen lukumääräksi haravoiYmparoivatMiinat -metodilta saamansa arvon.
     */
    public void laskeRuutujenYmparoivatMiinat() {
        for (int x = 0; x < kentanKoko; x++) {
            for (int y = 0; y < ruudut.length; y++) {
                ruudut[x][y].setYmparoivienLkm(haravoiYmparoivatMiinat(ruudut[x][y]));
            }
        }
    }

    /**
     * Käy läpi ympäröivissä ruuduissa olevat miinat.
     *
     * @param ruutu Metodi saa parametriksi Ruutu-olion.
     * @return palauttaa ympäröivien miinojen määrän.
     */
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
