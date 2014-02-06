package Miinaharava.Kayttoliittyma;

import Miinaharava.logiikka.Pelilauta;
import Miinaharava.logiikka.Pelilogiikka;
import Miinaharava.logiikka.Ruutu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.HashMap;
//import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Pelin graafinen käyttöliittymä. Vielä täysin vaiheessa..
 *
 */
public class Kayttoliittyma extends JFrame implements Runnable {

    private JFrame frame;
    private Pelilauta lauta;
    private Pelilogiikka logiikka;
//    private Map<Ruutu, JButton> kaikkiRuudut;

    public Kayttoliittyma(Pelilogiikka logiikka) {
        this.logiikka = logiikka;
        this.lauta = logiikka.getPelilauta();
//        this.kaikkiRuudut = new HashMap();
    }

    public void run() {
        frame = new JFrame("Miinaharava");

        frame.setPreferredSize(new Dimension(300, 350));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(this);
    }

    private void luoKomponentit(Container container) {

        JMenuBar valikko = luoValikko();
        JPanel pelialue = luoPelialue();
        JPanel tekstiKenttaAla = luoAlaKentta();

        container.setLayout(new BorderLayout());

        frame.setJMenuBar(valikko);
        container.add((pelialue), BorderLayout.CENTER);
        container.add((tekstiKenttaAla), BorderLayout.SOUTH);

    }

    /**
     * Pelin ylävalikko.
     *
     */
    private JMenuBar luoValikko() {
        JMenuBar menubar = new JMenuBar();
        JMenu peli = new JMenu("Peli");
        JMenuItem uusipeli = new JMenuItem("Uusi peli");
        JMenuItem lopetus = new JMenuItem("Lopetus");

        uusipeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                uusiPeli();
            }
        });
        lopetus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menubar.add(peli);
        peli.add(uusipeli);
        menubar.add(lopetus);

        return menubar;
    }

    private JPanel luoPelialue() {
        Ruutu[][] ruudut = lauta.getRuudut();
        JPanel panel = new JPanel(new GridLayout(lauta.getKentanKoko(), lauta.getKentanKoko()));
        panel.setBackground(Color.GRAY);

        for (int x = 0; x < lauta.getKentanKoko(); x++) {
            for (int y = 0; y < lauta.getKentanKoko(); y++) {

                JButton ruudunNappi = new JButton(ruudut[x][y].toString());
                ruudunNappi.setSize(25, 25);
//                ruudunNappi.setBorder(null);

                panel.add(ruudunNappi);

                HiiriKuuntelija kuuntelija = new HiiriKuuntelija(this.logiikka, this, ruudut[x][y], ruudunNappi);
                ruudunNappi.addMouseListener(kuuntelija);
//                this.kaikkiRuudut.put(ruudut[x][y], ruudunNappi);
            }
        }
        return panel;
    }

    private JPanel luoAlaKentta() {
        JPanel alaKentta = new JPanel();
        JLabel tekstiKentta = new JLabel("tekstikenttä");
        alaKentta.add(tekstiKentta);
        return alaKentta;
    }
}
