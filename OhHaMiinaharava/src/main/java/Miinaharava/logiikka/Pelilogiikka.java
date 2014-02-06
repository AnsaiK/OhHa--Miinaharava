package Miinaharava.logiikka;

/**
 * Pitää sisällään pelin toimintalogiikan.
 *
 */
public class Pelilogiikka {

    private Pelilauta lauta;
    private Ruutu[][] ruudut;
    private int ruutujaKiinni;

    public Pelilogiikka(int koko, int miinat) {
        this.lauta = new Pelilauta(koko, miinat);
        this.ruudut = lauta.getRuudut();
        this.ruutujaKiinni = ruudut.length * ruudut.length;
    }

    /**
     * Vaihtaa ruudun liputustilan päinvastaiseksi.
     *
     * @param ruutu saa arvona Ruutu -olion.
     */
    public void liputaRuutu(Ruutu ruutu) {
        if (ruutu.getLippu() == false) {
            ruutu.setLippu(true);
        } else {
            ruutu.setLippu(false);
        }
    }

    /**
     * Nollaa kentän alkutilanteeseen uuta peliä varten.
     *
     */
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
        this.ruutujaKiinni = ruudut.length * ruudut.length;
    }

    /**
     * Tarkistaa onko peli pelattu läpi.
     *
     * @return Palautta true, jos kaikki miinattomat ruudut ovat auki.
     */
    public boolean peliVoitettu() {
        if (this.ruutujaKiinni == lauta.getMiinojenlkm()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ruutujen avaamisen metodi. Jos ruutu on avaamaton, kutsuu metodia
     * avaaRuutu(Ruutu ruutu). Jos ruudun ympäröivien miinojen lkm on 0, avataan
     * kaikki vierekkäiset tyhjät ruudut ja niitä ympäröivät miinattomat ruudut
     * rekursiolla.
     *
     * @param ruutu metodi saa arvona Ruutu-olion.
     */
    public void avaaKenttaa(Ruutu ruutu) {

        int x = ruutu.getX();
        int y = ruutu.getY();
        int kentankoko = lauta.getKentanKoko();

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

    /**
     * Merkkaa ruudun avatuksi ja vähentää kiinni olevien ruutujen määrää
     * yhdellä.
     *
     *
     */
    public void avaaRuutu(Ruutu ruutu) {
        ruutu.setAvattu(true);
        this.ruutujaKiinni--;
    }

    public Pelilauta getPelilauta() {
        return this.lauta;
    }
}
