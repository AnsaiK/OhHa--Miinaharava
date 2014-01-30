package Miinaharava.logiikka;

public class Pelilogiikka {

    private Pelilauta lauta;
    private Ruutu[][] ruudut;
    private int ruutujaKiinni;

    public Pelilogiikka(int koko, int miinat) {
        this.lauta = new Pelilauta(koko, miinat);
        this.ruudut = lauta.getRuudut();
        this.ruutujaKiinni = ruudut.length * ruudut.length;
    }

    public void avaaRuutu(Ruutu ruutu) {
        if (ruutu.getAvattu() == false) {
            ruutu.setAvattu(true);
            this.ruutujaKiinni--;
        }
    }

    public void liputaRuutu(Ruutu ruutu) {
        if (ruutu.getLippu() == false) {
            ruutu.setLippu(true);
        }
    }

    public void uusiPeli() {
        for (int i = 0; i < ruudut.length; i++) {
            for (int j = 0; j < ruudut.length; j++) {
                ruudut[i][j].setAvattu(false);
                ruudut[i][j].setLippu(false);
                ruudut[i][j].setMiina(false);
            }
        }
        lauta.arvoMiinat();
        lauta.laskeRuutujenYmparoivatMiinat();
        this.ruutujaKiinni = ruudut.length;
    }

    public boolean peliVoitettu() {
        if (this.ruutujaKiinni == ruudut.length - lauta.getMiinojenlkm()) {
            return true;
        } else {
            return false;
        }
    }

    public void avaaKenttaa(Ruutu ruutu) {

        int x = ruutu.getX();
        int y = ruutu.getY();
        int kentankoko = lauta.getKentanKoko();

        if (x < 0 || y < 0 || x >= kentankoko || y >= kentankoko) {
            return;
        }

        if (ruutu.getAvattu() == true) {
            return;
        }

        avaaRuutu(ruutu);

        if (ruutu.getYmparoivatMiinatLkm() > 0) {
            return;
        }

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + i >= 0 && y + j >= 0 && x + i < kentankoko && y + j < kentankoko) {
                    avaaKenttaa(ruudut[x + i][y + j]);
                }
            }
        }
    }


    public Pelilauta getPelilauta() {
        return this.lauta;
    }

}
