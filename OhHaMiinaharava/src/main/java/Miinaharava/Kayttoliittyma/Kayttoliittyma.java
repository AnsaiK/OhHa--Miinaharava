package Miinaharava.Kayttoliittyma;

import Miinaharava.logiikka.Pelilauta;
import Miinaharava.logiikka.Pelilogiikka;
import Miinaharava.logiikka.Ruutu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 * Pelin graafinen käyttöliittymä. Vielä kesken.
 *
 */
public class Kayttoliittyma extends JFrame implements Runnable, ActionListener {

    private JFrame frame;
    private Pelilauta lauta;
    private Pelilogiikka logiikka;
    private Map<Ruutu, JButton> kaikkiRuudut;
    private JLabel tekstiKentta;
    private JLabel ruutujaAvaamatta;
    private JLabel laskurikentta;
    private JPanel pelialue;
    private Timer laskuri;

    public Kayttoliittyma() {
        this.logiikka = new Pelilogiikka(10, 10);
        this.lauta = logiikka.getPelilauta();
        this.kaikkiRuudut = new HashMap();

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

    /**
     * Luo pelilaudan komponentit.
     *
     */
    private void luoKomponentit(Container container) {

        JMenuBar valikko = luoValikko();
        JPanel ylarivi = luoYlaRivi();
        JPanel pelialue = luoPelialue();
        JPanel tekstikenttaAla = luoAlaKentta();

        container.setLayout(new BorderLayout());

        frame.setJMenuBar(valikko);
        container.add(ylarivi, BorderLayout.NORTH);
        container.add(pelialue, BorderLayout.CENTER);
        container.add(tekstikenttaAla, BorderLayout.SOUTH);
    }

    /**
     * Pelin ylävalikko.
     *
     */
    private JMenuBar luoValikko() {
        JMenuBar menubar = new JMenuBar();
        JMenuItem lopetus = new JMenuItem("Lopetus");

        lopetus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menubar.add(lopetus);
        return menubar;
    }

    /**
     * Luo pelikentän yläpuolelle kentän. Kentässä uuden pelin valintanappi ja
     * kiinni olevien miinattomien ruutujen määrä.
     *
     */
    private JPanel luoYlaRivi() {
        JPanel ylarivi = new JPanel(new GridLayout(1, 3));
        ruutujaAvaamatta = new JLabel("" + logiikka.getAvaamattomatRuudut());
        ruutujaAvaamatta.setPreferredSize(new Dimension(30, 30));
        ylarivi.add(ruutujaAvaamatta);

        JButton uusiPeli = new JButton("Uusi peli");
        uusiPeli.setPreferredSize(new Dimension(30, 30));
        uusiPeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uusiPeli();
            }
        });
        ylarivi.add(uusiPeli);

        return ylarivi;
    }

    private JPanel luoPelialue() {
        Ruutu[][] ruudut = lauta.getRuudut();
        pelialue = new JPanel(new GridLayout(lauta.getKentanKoko(), lauta.getKentanKoko()));
        pelialue.setBackground(Color.GRAY);
        luoNapit(pelialue, ruudut);
        return pelialue;
    }

    /**
     * Luo pelikentän ruudut JButtoneista. Tallettaa ruudut HashMappiin.
     *
     */
    private void luoNapit(JPanel panel, Ruutu[][] ruudut) {
        for (int x = 0; x < lauta.getKentanKoko(); x++) {
            for (int y = 0; y < lauta.getKentanKoko(); y++) {

                JButton ruudunNappi = new JButton();
                ruudunNappi.setSize(30, 30);

                panel.add(ruudunNappi);

                HiiriKuuntelija kuuntelija = new HiiriKuuntelija(this.logiikka, this, ruudut[x][y], ruudunNappi);
                ruudunNappi.addMouseListener(kuuntelija);
                this.kaikkiRuudut.put(ruudut[x][y], ruudunNappi);
            }
        }
    }

    /**
     * Luo pelikentän alapuolelle tekstikentän
     *
     */
    private JPanel luoAlaKentta() {
        JPanel alaKentta = new JPanel();
        tekstiKentta = new JLabel("Etsi miinat!");
        alaKentta.add(tekstiKentta);
        return alaKentta;
    }

    /**
     * Päivittää avatut ruudut. Tyhjien ruutujen avaus bugittaa vielä....
     *
     */
    public void paivitaRuudut() {
        for (Ruutu ruutu : kaikkiRuudut.keySet()) {
            if (logiikka.getPeliHavitty() == false) {
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 0) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setBorder(null);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 1) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setText("1");
                    paivitettava.setForeground(Color.BLUE);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 2) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setText("2");
                    paivitettava.setForeground(Color.RED);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 3) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setText("3");
                    paivitettava.setForeground(Color.GREEN);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 4) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setText("4");
                    paivitettava.setForeground(Color.orange);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 5) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setText("4");
                    paivitettava.setForeground(Color.PINK);
                }
                if (logiikka.getPeliVoitettu() == true) {
                    tekstiKentta.setText("Löysit kaikki miinat, voitit!");
                }
                if (ruutu.getAvattu() && ruutu.getMiina()) {
                    Font fontti = new Font("LATIN", Font.BOLD, 20);
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setForeground(Color.BLACK);
                    paivitettava.setText("M");
                    paivitettava.setFont(fontti);
                    tekstiKentta.setText("Osuit miinaan, hävisit!");
                    logiikka.setPeliHavitty(true);
                }
                this.ruutujaAvaamatta.setText("" + logiikka.getAvaamattomatRuudut());
            }
        }
    }

    /**
     * Luo uuden pelin. Nollaa pelilogiikan parametrit alkutilanteeseen ja
     * alustaa uuden pelin.
     *
     */
    public void uusiPeli() {
        logiikka.uusiPeli();
        frame.remove(pelialue);
        luoKomponentit(frame.getContentPane());
        frame.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
