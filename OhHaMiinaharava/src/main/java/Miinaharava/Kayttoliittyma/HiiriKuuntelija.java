/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miinaharava.Kayttoliittyma;

import Miinaharava.logiikka.Pelilogiikka;
import Miinaharava.logiikka.Ruutu;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * Hiiren vasemman ja oikean napin kuuntelija. Vielä kesken.
 */
public class HiiriKuuntelija implements MouseListener {

    private Pelilogiikka logiikka;
    private Kayttoliittyma UI;
    private Ruutu ruutu;
    private JButton nappi;

    public HiiriKuuntelija(Pelilogiikka logiikka, Kayttoliittyma UI, Ruutu ruutu, JButton ruutuNappi) {
        this.logiikka = logiikka;
        this.UI = UI;
        this.ruutu = ruutu;
        this.nappi = ruutuNappi;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.logiikka.avaaKenttaa(ruutu);
            this.nappi.setBackground(Color.red);
            this.nappi.setForeground(Color.red);
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            this.logiikka.liputaRuutu(ruutu);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
