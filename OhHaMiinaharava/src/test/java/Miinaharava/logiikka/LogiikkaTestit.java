/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miinaharava.logiikka;

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
public class LogiikkaTestit {

    Pelilogiikka logiikka;
    Pelilogiikka logiikkaMiinaton;
    Pelilauta lauta;
    Pelilauta lautaMiinaton;
    Ruutu[][] ruudut;
    Ruutu[][] ruudutMiinaton;

    public LogiikkaTestit() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        logiikka = new Pelilogiikka(10, 10);
        lauta = logiikka.getPelilauta();
        ruudut = lauta.getRuudut();

        logiikkaMiinaton = new Pelilogiikka(10, 0);
        lautaMiinaton = logiikkaMiinaton.getPelilauta();
        ruudutMiinaton = lautaMiinaton.getRuudut();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void liputaRuutuTrue() {
        logiikka.liputaRuutu(ruudut[1][1]);
        assertEquals(true, ruudut[1][1].getLippu());
    }

    @Test
    public void liputaRuutuFalse() {
        logiikka.liputaRuutu(ruudut[1][1]);
        logiikka.liputaRuutu(ruudut[1][1]);
        assertEquals(false, ruudut[1][1].getLippu());
    }

    @Test
    public void uusiPeliRuudutKiinni() {
        int auki = 0;
        for (int i = 0; i < ruudut.length; i++) {
            for (int j = 0; j < ruudut.length; j++) {
                ruudutMiinaton[i][j].setAvattu(true);
            }
        }
        logiikkaMiinaton.uusiPeli();

        for (int i = 0; i < ruudut.length; i++) {
            for (int j = 0; j < ruudut.length; j++) {
                if (ruudutMiinaton[i][j].getAvattu() == true) {
                    auki++;
                }
            }
        }
        assertEquals(0, auki);
    }

    @Test
    public void uusiPeliRuudutLiputtamatta() {
        int lippu = 0;
        for (int i = 0; i < ruudut.length; i++) {
            for (int j = 0; j < ruudut.length; j++) {
                ruudutMiinaton[i][j].setLippu(true);
            }
        }
        logiikkaMiinaton.uusiPeli();

        for (int i = 0; i < ruudut.length; i++) {
            for (int j = 0; j < ruudut.length; j++) {
                if (ruudutMiinaton[i][j].getLippu() == true) {
                    lippu++;
                }
            }
        }
        assertEquals(0, lippu);
    }

    @Test
    public void uusiPeliMiinatOlemassa() {
        int miinat = 0;

        logiikka.uusiPeli();

        for (int i = 0; i < ruudut.length; i++) {
            for (int j = 0; j < ruudut.length; j++) {
                if (ruudut[i][j].getMiina() == true) {
                    miinat++;
                }
            }
        }
        assertEquals(miinat, lauta.getMiinojenlkm());
    }

    @Test
    public void uusiPeliAvaamattomatRuudutOk() {
        logiikka.avaaKenttaa(ruudut[1][1]);
        logiikka.uusiPeli();
        assertEquals(90, logiikka.getAvaamattomatRuudut());
    }

    @Test
    public void avaaKenttaaTyhjatAuki() {
        int avattu = 0;
        logiikkaMiinaton.avaaKenttaa(ruudutMiinaton[2][2]);

        for (int i = 0; i < ruudutMiinaton.length; i++) {
            for (int j = 0; j < ruudutMiinaton.length; j++) {
                if (ruudutMiinaton[i][j].getAvattu() == true) {
                    avattu++;
                }
            }
        }
        assertEquals(100, avattu);

    }

    @Test
    public void avaaKenttaaValittuAuki() {
        logiikkaMiinaton.avaaKenttaa(ruudutMiinaton[2][2]);
        assertEquals(true, ruudutMiinaton[3][2].getAvattu());
    }

    @Test
    public void ruutuAukiTrue() {
        logiikkaMiinaton.avaaRuutu(ruudutMiinaton[3][3]);
        assertEquals(true, ruudutMiinaton[3][3].getAvattu());
    }

    @Test
    public void ruutuAukiFalse() {
        logiikkaMiinaton.avaaRuutu(ruudutMiinaton[3][3]);
        assertEquals(false, ruudutMiinaton[4][3].getAvattu());
    }

    @Test
    public void peliVoitettu() {
        logiikkaMiinaton.avaaKenttaa(ruudutMiinaton[2][1]);
        assertEquals(true, logiikkaMiinaton.getPeliVoitettu());
    }

    @Test
    public void peliVoitettuFalse() {
        logiikka.avaaKenttaa(ruudutMiinaton[2][1]);
        assertEquals(false, logiikka.getPeliVoitettu());
    }
}
