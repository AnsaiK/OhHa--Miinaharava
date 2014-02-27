/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miinaharava.GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 *
 * Pelin yläosan paneeli. Sisältää uuden pelin aloittamisen JButtonin, pelatun
 * ajan laskurin Timer ja avaamattomien ruutujen JLabel.
 */
public class YlaosaPaneeli {

    private JPanel ylarivi;
    private Kayttoliittyma UI;
    private JLabel ruutujaAvaamattaKentta, aikaKentta;
    private int alustanLeveys;
    private Timer timer;
    private int aika;
    private Border reunat;

    /**
     * Luokan konstruktori. Määrittelee paneelin koon. Kutsuu metodeja
     * luoAikaKentta(), uusiPeliNappi() ja ruutujaAvaamattaKentta().
     *
     */
    public YlaosaPaneeli(int alustanleveys, Kayttoliittyma UI) {
        this.UI = UI;
        this.alustanLeveys = alustanleveys;
        ylarivi = new JPanel(new GridLayout(1, 3));
        this.reunat = BorderFactory.createLoweredBevelBorder();
        luoAikaKentta();
        uusiPeliNappi();
        ruutujaAvaamattaKentta();

    }

    /**
     * Uuden pelin aloittamisen JButton. Lisää JPaneliin JButtonin. Kuuntelija
     * kutsuu Käyttöliittymäluokan metodia uusiPeli().
     *
     */
    private void uusiPeliNappi() {
        JButton uusiPeli = new JButton("Uusi peli");
        uusiPeli.setPreferredSize(new Dimension(this.alustanLeveys / 3, 30));
        uusiPeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UI.uusiPeli();
            }
        }
        );
        ylarivi.add(uusiPeli);
    }

    /**
     * Luo JLabelin avaamattomien ruutujen lukumäärän näyttämistä varten.
     *
     */
    private void ruutujaAvaamattaKentta() {
        ruutujaAvaamattaKentta = new JLabel("Avaamatta: " , JLabel.CENTER);
        ruutujaAvaamattaKentta.setPreferredSize(new Dimension(this.alustanLeveys / 3, 30));
        ruutujaAvaamattaKentta.setBorder(reunat);
        ylarivi.add(ruutujaAvaamattaKentta);
    }

    /**
     * Luo JLabelin kuluneen peliajan näyytämistä varten. Kutsuu metodia
     * luoKello().
     *
     */
    private void luoAikaKentta() {
        aikaKentta = new JLabel("Aika: 0", JLabel.CENTER);
        aikaKentta.setBorder(reunat);
        aikaKentta.setPreferredSize(new Dimension(this.alustanLeveys / 3, 30));
        luoKello();
        ylarivi.add(aikaKentta);
    }

    /**
     * Luo Timerin. Timer päivittää aikaKenttaa sekunnin välein kuluneen ajan.
     *
     */
    private void luoKello() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aika++;
                aikaKentta.setText("Aika: " + aika);
            }
        });
        timer.start();
    }

    /**
     * Pysäyttää Timerin.
     *
     */
    public void pysaytaKello() {
        timer.stop();
    }

    public JPanel getYlaosanPaneeli() {
        return ylarivi;
    }

    public JLabel getRuutujaAvaamatta() {
        return ruutujaAvaamattaKentta;
    }

    public int getAika() {
        return aika;
    }
}
