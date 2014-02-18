package Miinaharava.logiikka;

/**
 * Pitää sisällään pelin toimintalogiikan.
 *
 */
public class Pelilogiikka {

    private Pelilauta lauta;
    private Ruutu[][] ruudut;
    private int ruutujaKiinni;
    private int avaamattomiaRuutujaJäljellä;
    private Boolean peliVoitettu;
    private Boolean peliHavitty;

    /**
     * Alustaa pelin. Konstruktori luo pelilaudan
     *
     * @param koko pelilaudan koko
     * @param miinat miinojen määrä
     */
    public Pelilogiikka(int koko, int miinat) {
        this.lauta = new Pelilauta(koko, miinat);
        this.ruudut = lauta.getRuudut();
        this.ruutujaKiinni = ruudut.length * ruudut.length;
        this.peliVoitettu = false;
        this.peliHavitty = false;
        this.avaamattomiaRuutujaJäljellä = this.ruutujaKiinni - lauta.getMiinojenlkm();
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
     * Nollaa kentän alkutilanteeseen uutta peliä varten. Asettaa ruutujen
     * boolean-arvot falseksi, nollaa pelin hävitty- ja voitettu tilan. Lisäksi
     * arvotaan miinat, lasketaan ruutuja ympäröivät miinat ja merkataan kiinni
     * olevien ruutujen määrä.
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
        this.peliHavitty = false;
        this.peliVoitettu = false;
        lauta.arvoMiinat();
        lauta.laskeRuutujenYmparoivatMiinat();
        this.ruutujaKiinni = ruudut.length * ruudut.length;
        this.avaamattomiaRuutujaJäljellä = this.ruutujaKiinni - lauta.getMiinojenlkm();
    }

    /**
     * Tarkistaa onko peli pelattu läpi.
     *
     * @return asettaa peliVoitettu-arvoksi true, jos kaikki miinattomat ruudut
     * ovat auki.
     */
    private void onkoPeliVoitettu() {
        if (this.avaamattomiaRuutujaJäljellä == 0) {
            this.peliVoitettu = true;
        }
    }

    public boolean getPeliVoitettu() {
        return this.peliVoitettu;
    }

    public boolean getPeliHavitty() {
        return this.peliHavitty;
    }

    public void setPeliHavitty(boolean havitty) {
        this.peliHavitty = havitty;
    }

    public int getAvaamattomatRuudut() {
        return this.avaamattomiaRuutujaJäljellä;
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

        int kentankoko = lauta.getKentanKoko();

        int x = ruutu.getX();
        int y = ruutu.getY();

        if (ruutu.getAvattu() == true) {
            return;
        }
        avaaRuutu(ruutu);

        if (ruutu.getYmparoivatMiinatLkm() > 0) {
            return;
        }

                for (int i = -1;
                i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + i >= 0 && y + j >= 0 && x + i < kentankoko && y + j < kentankoko) {
                    avaaKenttaa(ruudut[x + i][y + j]);
                }
            }
        }
    }
    
    /**
     * Merkkaa ruudun avatuksi ja vähentää kiinni olevien ruutujen määrää
     * yhdellä. Tarkistaa onko ruudussa miina. Jos ruudussa ei ole miinaa,
     * vähennettään yksi miinattomiaRuutujaAvaamatta -arvosta. Lopuksi
     * tarkistetaan onko peli voitettu kutsumalla onkoPeliVoitettu -metodia.
     *
     * @param ruutu avaa parametrinä olevan ruudun.
     *
     */
    public void avaaRuutu(Ruutu ruutu) {
        ruutu.setAvattu(true);
        this.ruutujaKiinni--;
        if (ruutu.getMiina() == false) {
            this.avaamattomiaRuutujaJäljellä--;
            onkoPeliVoitettu();
        }
    }

    public Pelilauta getPelilauta() {
        return this.lauta;
    }
}
