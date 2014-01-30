package Miinaharava.logiikka;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Miinaharava.logiikka.Pelilauta;
import Miinaharava.logiikka.Ruutu;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anssi
 */
public class PelilautaTestit {

    Pelilauta lauta;
    Ruutu[][] ruudukko;

    public PelilautaTestit() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        lauta = new Pelilauta(10, 10);
        ruudukko = lauta.getRuudut();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void kaikkiRuudutOlemassa() {
        Ruutu[][] ruudukko = lauta.getRuudut();
        int lkm = 0;

        for (int i = 0; i < ruudukko.length; i++) {
            for (int j = 0; j < ruudukko.length; j++) {
                lkm++;
            }
        }
        assertEquals(100, lkm);
    }

    @Test
    public void kaikkiPommitOlemassa() {
        int lkm = 0;

        for (int i = 0; i < ruudukko.length; i++) {
            for (int j = 0; j < ruudukko.length; j++) {
                if (ruudukko[i][j].getMiina() == true) {
                    lkm++;
                }
            }
        }
        assertEquals(10, lkm);
    }

    @Test
    public void ylakulmaRuutuVasen3PommiaYmparilla() {

        poistaPommit(0, 2, ruudukko);
        nollaaLkm(0, 2, ruudukko);

        ruudukko[0][1].setMiina(true);
        ruudukko[1][0].setMiina(true);
        ruudukko[1][1].setMiina(true);

        Ruutu testattava = ruudukko[0][0];
        testattava.setYmparoivienLkm(lauta.haravoiYmparoivatMiinat(testattava));

        assertEquals("3", testattava.toString());
    }

    @Test
    public void ylakulmaRuutuVasen2PommiaYmparilla() {

        poistaPommit(0, 2, ruudukko);
        nollaaLkm(0, 2, ruudukko);

        ruudukko[0][1].setMiina(true);
        ruudukko[1][1].setMiina(true);

        Ruutu testattava = ruudukko[0][0];
        testattava.setYmparoivienLkm(lauta.haravoiYmparoivatMiinat(testattava));

        assertEquals("2", testattava.toString());
    }

    @Test
    public void ylakulmaRuutuVasen1PommiYmparilla() {

        poistaPommit(0, 2, ruudukko);
        nollaaLkm(0, 2, ruudukko);

        ruudukko[1][0].setMiina(true);

        Ruutu testattava = ruudukko[0][0];
        testattava.setYmparoivienLkm(lauta.haravoiYmparoivatMiinat(testattava));

        assertEquals("1", testattava.toString());
    }

    @Test
    public void ylakulmaRuutuOikea3PommiaYmparilla() {

        poistaPommit(8, 10, ruudukko);
        nollaaLkm(8, 10, ruudukko);

        ruudukko[9][8].setMiina(true);
        ruudukko[8][9].setMiina(true);
        ruudukko[8][8].setMiina(true);

        Ruutu testattava = ruudukko[9][9];
        testattava.setYmparoivienLkm(lauta.haravoiYmparoivatMiinat(testattava));

        assertEquals("3", testattava.toString());
    }

    @Test
    public void ylakulmaRuutuOikea2PommiaYmparilla() {

        poistaPommit(8, 10, ruudukko);
        nollaaLkm(8, 10, ruudukko);

        ruudukko[8][9].setMiina(true);
        ruudukko[8][8].setMiina(true);

        Ruutu testattava = ruudukko[9][9];
        testattava.setYmparoivienLkm(lauta.haravoiYmparoivatMiinat(testattava));

        assertEquals("2", testattava.toString());
    }

    @Test
    public void ylakulmaRuutuOikea1PommiaYmparilla() {

        poistaPommit(8, 10, ruudukko);
        nollaaLkm(8, 10, ruudukko);
        ruudukko[9][8].setMiina(true);

        Ruutu testattava = ruudukko[9][9];
        testattava.setYmparoivienLkm(lauta.haravoiYmparoivatMiinat(testattava));

        assertEquals("1", testattava.toString());
    }

    @Test
    public void keskella8Pommia() {

        poistaPommit(4, 7, ruudukko);
        nollaaLkm(4, 7, ruudukko);
        setPommit(4, 7, ruudukko);

        Ruutu testattava = ruudukko[5][5];
        testattava.setMiina(false);
        testattava.setYmparoivienLkm(lauta.haravoiYmparoivatMiinat(testattava));

        assertEquals("8", testattava.toString());
    }

    @Test
    public void keskellaEiPommeja() {

        poistaPommit(4, 7, ruudukko);
        nollaaLkm(4, 7, ruudukko);

        Ruutu testattava = ruudukko[5][5];
        testattava.setYmparoivienLkm(lauta.haravoiYmparoivatMiinat(testattava));

        assertEquals("0", testattava.toString());
    }

    private Ruutu[][] poistaPommit(int alaraja, int ylaraja, Ruutu[][] ruudukko) {
        for (int i = alaraja; i < ylaraja; i++) {
            for (int j = alaraja; j < ylaraja; j++) {
                ruudukko[i][j].setMiina(false);
            }
        }
        return ruudukko;
    }

    private Ruutu[][] setPommit(int alaraja, int ylaraja, Ruutu[][] ruudukko) {
        for (int i = alaraja; i < ylaraja; i++) {
            for (int j = alaraja; j < ylaraja; j++) {
                ruudukko[i][j].setMiina(true);
            }
        }
        return ruudukko;
    }

    private Ruutu[][] nollaaLkm(int alaraja, int ylaraja, Ruutu[][] ruudukko) {
        for (int i = alaraja; i < ylaraja; i++) {
            for (int j = alaraja; j < ylaraja; j++) {
                ruudukko[i][j].setYmparoivienLkm(0);
            }
        }
        return ruudukko;
    }

}
