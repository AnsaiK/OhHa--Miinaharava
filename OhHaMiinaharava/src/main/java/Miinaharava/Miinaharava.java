package Miinaharava;

import Miinaharava.GUI.Kayttoliittyma;
import javax.swing.SwingUtilities;

public class Miinaharava {

    public static void main(String[] args) {

        Kayttoliittyma kali = new Kayttoliittyma();
        SwingUtilities.invokeLater(kali);
    }
}
