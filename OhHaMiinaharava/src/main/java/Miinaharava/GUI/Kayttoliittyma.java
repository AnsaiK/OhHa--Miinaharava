package Miinaharava.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Pelin graafinen käyttöliittymä.
 *
 */
public class Kayttoliittyma extends JFrame implements Runnable {

    private JFrame frame;
    private Grafiikkamoottori gMoottori;
    private YlaosaPaneeli ylapaneeli;
    private AlaosaPaneeli alapaneeli;
    private JPanel alaosanPaneeli, ylaosanPaneeli, pelialue;
    private int alustanLeveys;
    private int alustanKorkeus;

    public Kayttoliittyma() {
        this.alustanLeveys = 500;
        this.alustanKorkeus = 570;
        this.gMoottori = new Grafiikkamoottori(this);

    }

    public void run() {
        frame = new JFrame("Miinaharava");
        frame.setPreferredSize(new Dimension(this.alustanLeveys, this.alustanKorkeus));
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
        ylaosanPaneeli = luoYlaPaneeli();

        pelialue = gMoottori.luoPelialue();
        alaosanPaneeli = luoAlaKentta();

        container.setLayout(new BorderLayout());

        frame.setJMenuBar(valikko);
        container.add(ylaosanPaneeli, BorderLayout.NORTH);
        container.add(pelialue, BorderLayout.CENTER);
        container.add(alaosanPaneeli, BorderLayout.SOUTH);
    }

    /**
     * Pelin menuvalikko. Sisältää kolme vaikeustasoa ja pelin lopettamisen.
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
                gMoottori.setKoko(10);
                gMoottori.setMiinat(10);
                alustanLeveys = 50 * 10;
                alustanKorkeus = alustanLeveys + 50;
                uusiPeli();
            }
        });
        normaali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gMoottori.setKoko(16);
                gMoottori.setMiinat(35);
                alustanLeveys = 50 * 16;
                alustanKorkeus = alustanLeveys + 50;
                uusiPeli();

            }
        });
        vaikea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gMoottori.setKoko(20);
                gMoottori.setMiinat(80);
                alustanLeveys = 50 * 20;
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
     * Luo pelikentän yläpuolelle kentän. Kutsuu luokkaa YlaosaPaneeli, joka luo
     * paneelin ja sen toiminnot.
     */
    private JPanel luoYlaPaneeli() {
        ylapaneeli = new YlaosaPaneeli(this.alustanLeveys, this);
        return ylapaneeli.getYlaosanPaneeli();
    }

    /**
     * Luo pelikentän alapuolelle tekstikentän. Kutsuu Luokka AlaosaPaneeli,
     * joka luo paneelin ja sen toiminnot.
     */
    private JPanel luoAlaKentta() {
        alapaneeli = new AlaosaPaneeli(this.alustanLeveys);
        return alapaneeli.getAlaPaneeli();
    }

    /**
     * Luo uuden pelin. Poistaa framesta vanhat paneelit, kutsuu
     * Grafiikkamoottorin pelinnollaus-metodia uusiPeli() ja luo uudet
     * komponentit. Alustaa uuden pelin.
     *
     */
    public void uusiPeli() {
        frame.remove(alaosanPaneeli);
        frame.remove(pelialue);
        frame.remove(ylaosanPaneeli);
        frame.setPreferredSize(new Dimension(this.alustanLeveys, this.alustanKorkeus));
        gMoottori.uusiPeli();

        luoKomponentit(frame.getContentPane());

        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.validate();
    }

    public YlaosaPaneeli getYlaPaneeli() {
        return ylapaneeli;
    }

    public AlaosaPaneeli getAlapaneeli() {
        return alapaneeli;
    }
}
