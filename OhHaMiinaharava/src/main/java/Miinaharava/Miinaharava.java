package Miinaharava;

import Miinaharava.Kayttoliittyma.Kayttoliittyma;
import javax.swing.SwingUtilities;

public class Miinaharava {

    public static void main(String[] args) {

        Kayttoliittyma kali = new Kayttoliittyma();
        SwingUtilities.invokeLater(kali);

    }
}
