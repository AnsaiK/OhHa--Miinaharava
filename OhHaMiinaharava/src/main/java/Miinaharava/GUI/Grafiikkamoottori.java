package Miinaharava.GUI;

import Miinaharava.logiikka.Pelilauta;
import Miinaharava.logiikka.Pelilogiikka;
import Miinaharava.logiikka.Ruutu;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * Yhdistää pelilogiikan ja graafisen käyttöliittymän.
 */
public class Grafiikkamoottori {

    private Pelilauta lauta;
    private Pelilogiikka logiikka;
    private Kayttoliittyma UI;
    private JPanel pelialue;
    private Map<Ruutu, JButton> kaikkiRuudut;
    private int PainallusLkm;
    private int miinat;
    private int koko;

    public Grafiikkamoottori(Kayttoliittyma kali) {
        this.UI = kali;
        this.koko = 10;
        this.miinat = 10;
        this.logiikka = new Pelilogiikka(koko, miinat);
        this.lauta = logiikka.getPelilauta();
        this.kaikkiRuudut = new HashMap();
        this.PainallusLkm = 0;
    }

    /**
     *
     * Päivittää graafisten ruutujen tilan logiikkaluokan mukaiseksi. Voiton tai
     * häviön tapahtuessa kutsuu peliHavitty() tai peliVoitettu() -metodia.
     * Päivittää yläpaneelissa näkyvien avattujen ruutujen tilan oikeaksi.
     */
    public void paivitaRuudut() {
        for (Ruutu ruutu : kaikkiRuudut.keySet()) {
            JButton paivitettava = kaikkiRuudut.get(ruutu);

            if (this.logiikka.getPeliHavitty() == false && ruutu.getAvattu()) {
                paivitaAlakentta("Klikkauksia tähän mennessä: " + this.PainallusLkm);
                if (ruutu.getMiina()) {
                    paivitettava.setBackground(Color.RED);
                    peliHavitty();
                    break;
                } else if (ruutu.getYmparoivatMiinatLkm() == 1) {
                    paivitettava.setText("1");
                    paivitettava.setForeground(Color.BLUE);
                } else if (ruutu.getYmparoivatMiinatLkm() == 2) {
                    paivitettava.setText("2");
                    paivitettava.setForeground(new Color(70, 110, 0));
                } else if (ruutu.getYmparoivatMiinatLkm() == 3) {
                    paivitettava.setText("3");
                    paivitettava.setForeground(Color.RED);
                } else if (ruutu.getYmparoivatMiinatLkm() == 4) {
                    paivitettava.setText("4");
                    paivitettava.setForeground(Color.MAGENTA);
                } else if (ruutu.getYmparoivatMiinatLkm() == 5) {
                    paivitettava.setText("5");
                    paivitettava.setForeground(Color.PINK);
                } else if (ruutu.getYmparoivatMiinatLkm() == 6) {
                    paivitettava.setText("6");
                    paivitettava.setForeground(Color.GREEN);
                } else if (ruutu.getYmparoivatMiinatLkm() == 7) {
                    paivitettava.setText("7");
                    paivitettava.setForeground(Color.BLUE);
                } else if (ruutu.getYmparoivatMiinatLkm() == 8) {
                    paivitettava.setText("8");
                    paivitettava.setForeground(Color.YELLOW);
                } else if (ruutu.getYmparoivatMiinatLkm() == 0) {
                    paivitettava.setBackground(Color.GRAY);
                    paivitettava.setBorder(null);
                    paivitettava.setText("");

                    UI.getYlaPaneeli().getRuutujaAvaamatta().setText("Avaamatta: " + this.logiikka.getAvaamattomatRuudut());
                }
            }
        }
        if (this.logiikka.getPeliVoitettu()
                == true) {
            peliVoitettu();
        }
    }

    /**
     *
     * Nollaa pelitilanteen uutta peliä varten. Kutsuu logiikkaluokan metodia
     * uusiPeli(). Palauttaa uuden pelilaudan ja nollaa hiirennapin painallusten määrän.
     */
    public void uusiPeli() {
        this.logiikka.uusiPeli();
        this.logiikka = new Pelilogiikka(koko, miinat);
        this.lauta = logiikka.getPelilauta();
        this.PainallusLkm = 0;

    }

    /**
     *
     * Luo pelialueen Paneelin. Kutsuu metodia luoNapit().
     */
    public JPanel luoPelialue() {
        Ruutu[][] ruudut = lauta.getRuudut();
        pelialue = new JPanel(new GridLayout(lauta.getKentanKoko(), lauta.getKentanKoko()));
        pelialue.setBackground(Color.GRAY);
        luoNapit(pelialue, ruudut);
        return pelialue;
    }

    /**
     * Luo pelikentän ruudut JButtoneista. Tallettaa ruudut HashMappiin.
     * Kiinnittää ruutuihin kuuntelijat.
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
     *
     * Päivittää pelin tilanteen häviöksi.
     */
    public void peliHavitty() {
        UI.getYlaPaneeli().pysaytaKello();
        paivitaMiinat("M");
        paivitaAlakentta("Osuit miinaan, hävisit!");
        this.logiikka.setPeliHavitty(true);
    }

    /**
     *
     * Lisää painnalusten määrää yhdellä.
     */
    public void setPainallusLkm() {
        this.PainallusLkm++;
    }

    /**
     *
     * Päivittää pelin tilanteen voitoksi.
     */
    private void peliVoitettu() {
        UI.getYlaPaneeli().pysaytaKello();
        paivitaMiinat("V");
        paivitaAlakentta("Löysit kaikki miinat, voitit!");
    }

    /**
     *
     * Päivittää miinaruutujen tekstin halutuksi.
     *
     * @param teksti miinaruuduissa näkyvä teksti.
     */
    private void paivitaMiinat(String teksti) {
        Font fontti = new Font("LATIN", Font.BOLD, 10);

        for (Ruutu ruutu : kaikkiRuudut.keySet()) {
            JButton paivitettava = kaikkiRuudut.get(ruutu);
            if (ruutu.getMiina()) {
                paivitettava.setText(teksti);
                paivitettava.setFont(fontti);
            }
        }
    }

    /**
     *
     * Päivittää Alapaneelin tekstikentän.
     *
     * @param teksti pelin alaosassa näkyvä teksti.
     */
    public void paivitaAlakentta(String teksti) {
        UI.getAlapaneeli().getPelitilanneTekstikentta().setText(teksti);
    }

    public void setMiinat(int miinat) {
        this.miinat = miinat;
    }

    public void setKoko(int koko) {
        this.koko = koko;
    }

    public Pelilogiikka getLogiikka() {
        return logiikka;
    }
}
