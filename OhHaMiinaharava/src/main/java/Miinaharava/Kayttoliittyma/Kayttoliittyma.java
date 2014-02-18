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
    private Map<Ruutu, JButton> kaikkiRuudut;
    private JLabel tekstiKentta;
    private JLabel ruutujaAvaamatta;
    private JPanel pelialue;
    private int koko;
    private int miinat;

    public Kayttoliittyma() {
        this.koko = 10;
        this.miinat = 10;
        this.logiikka = new Pelilogiikka(koko, miinat);
        this.lauta = logiikka.getPelilauta();
        this.kaikkiRuudut = new HashMap();

    }

    public void run() {

        frame = new JFrame("Miinaharava");

        frame.setPreferredSize(new Dimension(300, 350));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(frame.getContentPane(), this.koko, this.miinat);

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
        JMenu vaikeusaste = new JMenu("Vaikeusaste");
        JMenuItem helppo = new JMenuItem("Helppo");
        JMenuItem normaali = new JMenuItem("Normaali");
        JMenuItem vaikea = new JMenuItem("Vaikea");

        JMenuItem lopetus = new JMenuItem("Lopetus");

//        helppo.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
//        normaali.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
//        vaikea.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
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
        uusiPeli.setPreferredSize(new Dimension(30, 30));
        uusiPeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uusiPeli();
            }
        });
        ylarivi.add(uusiPeli);

        ruutujaAvaamatta = new JLabel(" Ruutuja avaamatta: " + logiikka.getAvaamattomatRuudut());
        ruutujaAvaamatta.setPreferredSize(new Dimension(30, 30));
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
                Font ruudunfontti = new Font("LATIN", Font.BOLD, 15);
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
        JPanel alaKentta = new JPanel();
        alaKentta.setPreferredSize(new Dimension(300, 30));
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
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 1) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setText("1");
                    paivitettava.setForeground(Color.BLUE);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 2) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setText("2");
                    paivitettava.setForeground(new Color(40,100,0));
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 3) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setText("3");
                    paivitettava.setForeground(Color.red);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 4) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setText("4");
                    paivitettava.setForeground(Color.magenta);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 5) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setText("5");
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
                    tekstiKentta.setFont(fontti);
                    tekstiKentta.setForeground(Color.red);
                    tekstiKentta.setText("Osuit miinaan, hävisit!");
                    logiikka.setPeliHavitty(true);
                }
                if (ruutu.getAvattu() && ruutu.getYmparoivatMiinatLkm() == 0) {
                    JButton paivitettava = kaikkiRuudut.get(ruutu);
                    paivitettava.setBorder(null);
                    paivitettava.setText("");
                }
                this.ruutujaAvaamatta.setText(" Ruutuja avaamatta: " + logiikka.getAvaamattomatRuudut());
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
        luoKomponentit(frame.getContentPane(), koko, miinat);
        frame.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
