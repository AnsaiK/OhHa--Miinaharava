package Miinaharava.GUI;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * Pelin alaosan tekstikenttä.
 */
public class AlaosaPaneeli {

    private JPanel alaPaneeli;
    private JLabel pelitilanneTekstikentta;

    public AlaosaPaneeli(int alustanleveys) {
        this.alaPaneeli = new JPanel();
        this.alaPaneeli.setPreferredSize(new Dimension(alustanleveys, 30));
        luoTekstikentta();
    }

    /**
     *
     * Luo JLabelin. Pelitilanteen viestejä varten.
     */
    private void luoTekstikentta() {
        pelitilanneTekstikentta = new JLabel("Etsi miinat!");
        alaPaneeli.add(pelitilanneTekstikentta);
    }

    public JPanel getAlaPaneeli() {
        return alaPaneeli;
    }

    public JLabel getPelitilanneTekstikentta() {
        return pelitilanneTekstikentta;
    }
}
