package Miinaharava;

import Miinaharava.Kayttoliittyma.Kayttoliittyma;
import Miinaharava.logiikka.Pelilauta;
import Miinaharava.logiikka.Pelilogiikka;
import Miinaharava.logiikka.Ruutu;

public class Miinaharava {

    public static void main(String[] args) {

        Pelilogiikka logi = new Pelilogiikka(10, 10);
        Kayttoliittyma kali = new Kayttoliittyma(logi);
        kali.run();

//        lauta.PiirraTekstiLauta(); // testausta varten
    }
}
