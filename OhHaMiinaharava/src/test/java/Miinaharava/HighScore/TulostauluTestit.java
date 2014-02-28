/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miinaharava.HighScore;

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
public class TulostauluTestit {

    TulosTaulu t;
    TulosTaulu tyhja;

    public TulostauluTestit() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        t = new TulosTaulu("testi1");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void tulostaTyhjaLista() {
        tyhja = new TulosTaulu("tyhjaTesti");
        String tulostus = tyhja.tulostaKaikki();
        assertEquals(tulostus, "Parhaat tulokset:" + "\n");
    }

    @Test
    public void lisaaYksiTulos() {
        t.lisaaTulos(new Tulos(1, 2));
        String tulostus = t.tulostaKaikki();
        assertEquals(tulostus, "Parhaat tulokset:" + "\n" + "Aika 1s - klikkaukset: 2" + "\n");
    }

    @Test
    public void lisaaToinenTulosOikeaJarjestys() {
        t.lisaaTulos(new Tulos(2, 4));
        String tulostus = t.tulostaKaikki();
        assertEquals(tulostus, "Parhaat tulokset:" + "\n" + "Aika 1s - klikkaukset: 2" + "\n" + "Aika 2s - klikkaukset: 4" + "\n");
    }

    @Test
    public void lisaaYliListanKoon() {
        t.lisaaTulos(new Tulos(2, 4));
        t.lisaaTulos(new Tulos(2, 4));
        t.lisaaTulos(new Tulos(2, 4));
        t.lisaaTulos(new Tulos(2, 4));
        t.lisaaTulos(new Tulos(2, 4));
        t.lisaaTulos(new Tulos(2, 4));
        t.lisaaTulos(new Tulos(2, 4));
        t.lisaaTulos(new Tulos(1, 4));
        t.lisaaTulos(new Tulos(5, 4));

        String tulostus = t.tulostaKaikki();
        assertEquals(tulostus, "Parhaat tulokset:" + "\n"
                + "Aika 1s - klikkaukset: 2" + "\n"
                + "Aika 1s - klikkaukset: 4" + "\n"
                + "Aika 2s - klikkaukset: 4" + "\n"
                + "Aika 2s - klikkaukset: 4" + "\n"
                + "Aika 2s - klikkaukset: 4" + "\n"
                + "Aika 2s - klikkaukset: 4" + "\n"
                + "Aika 2s - klikkaukset: 4" + "\n"
                + "Aika 2s - klikkaukset: 4" + "\n"
                + "Aika 2s - klikkaukset: 4" + "\n"
                + "Aika 2s - klikkaukset: 4" + "\n");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
