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
import javax.swing.WindowConstants;

/**
 * Pelin graafinen käyttöliittymä. Vielä kesken.
 *
 */
public class Kayttoliittyma extends JFrame implements Runnable, ActionListener {

    private JFrame frame;
    private Pelilauta lauta;
    private Pelilogiikka logiikka;
    final Map<Ruutu, JButton> kaikkiRuudut;
    private JLabel pelitilanneTekstikentta;
    private JLabel ruutujaAvaamatta;
    private JPanel pelialue;
    private JPanel ylaosanPaneeli;
    private JPanel alaosanPaneeli;
    private int ruutujaPerSivu;
    private int miinat;
    private int alustanLeveys;
    private int alustanKorkeus;

    public Kayttoliittyma() {
        this.ruutujaPerSivu = 10;
        this.miinat = 10;
        this.alustanLeveys = 500;
        this.alustanKorkeus = 570;
        this.logiikka = new Pelilogiikka(ruutujaPerSivu, miinat);
        this.lauta = logiikka.getPelilauta();
        this.kaikkiRuudut = new HashMap();

    }

    public void run() {

        frame = new JFrame("Miinaharava");

        frame.setPreferredSize(new Dimension(this.alustanLeveys, this.alustanKorkeus));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(frame.getContentPane(), this.ruutujaPerSivu, this.miinat);

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(this);
    }

    /**
     * Luo pelilaudan komponentit.
     *
     */
    private void luoKomponentit(Container container, int koko, int miinat) {

        this.logiikka = new Pelilogiikka(koko, miinat);
        this.lauta = logiikka.getPelilauta();
        JMenuBar valikko = luoValikko();
        ylaosanPaneeli = luoYlaRivi();
        pelialue = luoPelialue();
        alaosanPaneeli = luoAlaKentta();

        container.setLayout(new BorderLayout());

        frame.setJMenuBar(valikko);
        container.add(ylaosanPaneeli, BorderLayout.NORTH);
        container.add(pelialue, BorderLayout.CENTER);
        container.add(alaosanPaneeli, BorderLayout.SOUTH);
    }

    /**
     * Pelin ylävalikko.
     *
     */
    private JMenuBar luoValikko() {
        JMenuBar menubar = new JMenuBar();

        JMenu vaikeusaste = new JMenu("Vaikeusaste");
        JMenuItem helppo = new JMenuItem("Helppo");
        JMenuItem normaali = new JMenuItem("Normaali");
        JMenuItem vaikea = new JMenuItem("Vaikea");

        JMenuItem lopetus = new JMenuItem("Lopetus");

        helppo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ruutujaPerSivu = 10;
                miinat = 10;
                alustanLeveys = 50 * ruutujaPerSivu;
                alustanKorkeus = alustanLeveys + 50;
                uusiPeli();
            }
        });
        normaali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ruutujaPerSivu = 16;
                miinat = 40;
                alustanLeveys = 50 * ruutujaPerSivu;
                alustanKorkeus = alustanLeveys + 50;
                uusiPeli();

            }
        });
        vaikea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ruutujaPerSivu = 20;
                miinat = 40;
                alustanLeveys = 50 * ruutujaPerSivu;
                alustanKorkeus = alustanLeveys + 50;
                uusiPeli();

            }
        });
        lopetus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menubar.add(vaikeusaste);
        vaikeusaste.add(helppo);
        vaikeusaste.add(normaali);
        vaikeusaste.add(vaikea);
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

        JButton uusiPeli = new JButton("Uusi peli");
        uusiPeli.setPreferredSize(new Dimension(this.alustanLeveys / 2, 30));
        uusiPeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uusiPeli();
            }
        });
        ylarivi.add(uusiPeli);

        ruutujaAvaamatta = new JLabel(" Ruutuja avaamatta: " + logiikka.getAvaamattomatRuudut());
        ruutujaAvaamatta.setPreferredSize(new Dimension(this.alustanLeveys / 2, 30));
        ylarivi.add(ruutujaAvaamatta);

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
                Font ruudunfontti = new Font("LATIN", Font.BOLD, 10);
                ruudunNappi.setFont(ruudunfontti);

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
        alaosanPaneeli = new JPanel();
        alaosanPaneeli.setPreferredSize(new Dimension(this.alustanLeveys, 30));
        pelitilanneTekstikentta = new JLabel("Etsi miinat!");
        alaosanPaneeli.add(pelitilanneTekstikentta);
        return alaosanPaneeli;
    }

    /**
     * Päivittää avatut ruudut. Tyhjien ruutujen avaus bugittaa vielä....
     *
     */
    public void paivitaRuudut() {
        for (Ruutu ruutu : kaikkiRuudut.keySet()) {
            JButton paivitettava = kaikkiRuudut.get(ruutu);

            if (logiikka.getPeliHavitty() == false) {
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 1) {
                    paivitettava.setText("1");
                    paivitettava.setBackground(Color.GRAY);
                    paivitettava.setForeground(Color.BLUE);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 2) {
                    paivitettava.setText("2");
                    paivitettava.setForeground(new Color(70, 110, 0));
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 3) {
                    paivitettava.setText("3");
                    paivitettava.setForeground(Color.RED);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 4) {
                    paivitettava.setText("4");
                    paivitettava.setForeground(Color.MAGENTA);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 5) {
                    paivitettava.setText("5");
                    paivitettava.setForeground(Color.PINK);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 6) {
                    paivitettava.setText("6");
                    paivitettava.setForeground(Color.GREEN);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 7) {
                    paivitettava.setText("7");
                    paivitettava.setForeground(Color.BLUE);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 8) {
                    paivitettava.setText("8");
                    paivitettava.setForeground(Color.YELLOW);
                }
                if (logiikka.getPeliVoitettu() == true) {
                    pelitilanneTekstikentta.setText("Löysit kaikki miinat, voitit!");
                }
                if (ruutu.getAvattu() && ruutu.getMiina()) {
                    paivitettava.setForeground(Color.RED);
                    peliHavitty();
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 0) {
                    paivitettava.setBackground(Color.GRAY);
                    paivitettava.setBorder(null);
                    paivitettava.setText("");
                }
                this.ruutujaAvaamatta.setText(" Ruutuja avaamatta: " + logiikka.getAvaamattomatRuudut());
            }
        }
    }

    /**
     * Näyttää kaikki miinat ja päivittää tekstikentän.
     *
     */
    public void peliHavitty() {
        Font fontti = new Font("LATIN", Font.BOLD, 10);

        for (Ruutu ruutu : kaikkiRuudut.keySet()) {
            JButton paivitettava = kaikkiRuudut.get(ruutu);
            if (ruutu.getMiina()) {
                paivitettava.setText("M");
                paivitettava.setFont(fontti);
            }
        }
        pelitilanneTekstikentta.setFont(fontti);
        pelitilanneTekstikentta.setForeground(Color.red);
        pelitilanneTekstikentta.setText("Osuit miinaan, hävisit!");
        logiikka.setPeliHavitty(true);
    }

    /**
     * Luo uuden pelin. Nollaa pelilogiikan parametrit alkutilanteeseen ja
     * alustaa uuden pelin.
     *
     */
    public void uusiPeli() {
        logiikka.uusiPeli();
        frame.remove(alaosanPaneeli);
        frame.remove(pelialue);
        frame.remove(ylaosanPaneeli);
        frame.setPreferredSize(new Dimension(this.alustanLeveys, this.alustanKorkeus));
        luoKomponentit(frame.getContentPane(), ruutujaPerSivu, miinat);

        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
