package Miinaharava.HighScore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 *
 * Tuloslistan hallinointi. Tiedoston kirjoitus ja lukeminen.
 */
public class TulosTaulu {

    private static final long serialVersionUID = 7487390213792955281L;
    private ArrayList<Tulos> tuloslista;
    private String vaikeusaste;
    private String tiedostonosoite;

    public TulosTaulu(String vaikeusaste) {
        this.vaikeusaste = vaikeusaste;
        this.tiedostonosoite = "src/tulokset/tulokset_" + vaikeusaste + ".sav";
        lueTiedosto();
    }

    /**
     *
     * Tiedoston lukeminen. Lukee tiedostosta ArrayListin.
     */
    private void lueTiedosto() {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(this.tiedostonosoite));
            tuloslista = (ArrayList<Tulos>) input.readObject();
        } catch (IOException e) {
            tuloslista = new ArrayList();
        } catch (ClassNotFoundException e) {
            System.exit(1);
        }
    }

    /**
     *
     * Tulosten lisääminen listaan. Listassa on kymmenen paikkaa, jos tulos
     * mahtuu listaan, se lisätään sinne. Huonoin tulos poistetaan. Kutsuu
     * naytaTulokset() ja kirjoitaTiedosto() -metodeja.
     *
     * @param tulos mahdollisesti listaan lisättävä tulos.
     */
    public void lisaaTulos(Tulos tulos) {

        if (tuloslista.size() < 10) {
            tuloslista.add(tulos);

        } else if (tuloslista.get(9).getAika() > tulos.getAika()) {
            tuloslista.remove(9);
            tuloslista.add(tulos);
        }
        Collections.sort(tuloslista);
        naytaTulokset();
        kirjoitaTiedosto();
    }

    /**
     *
     * Kirjoittaa ArrayListin tiedostoon.
     */
    private void kirjoitaTiedosto() {
        try {
            FileOutputStream fos = new FileOutputStream(this.tiedostonosoite);
            ObjectOutputStream output = new ObjectOutputStream(fos);
            output.writeObject(this.tuloslista);
            output.close();
        } catch (IOException e) {
            System.err.println("Tiedostoa ei löydy, tai sinulla ei ole oikeuksia siihen.");
        }
    }

    /**
     *
     * Antaa ArrayListin tekstimuodossa.
     */
    public String tulostaKaikki() {
        String tulostus = "Parhaat tulokset:" + "\n";
        for (Tulos tulos : tuloslista) {
            tulostus += ("Aika " + tulos.getAika() + "s - " + "klikkaukset: " + tulos.getKlikkaukset()) + "\n";
        }
        return tulostus;
    }

    /**
     *
     * Tulosten näyttäminen. Avaa ponnahdusikkunassa tulokset. Jos listalla ei
     * ole tuloksia, ilmoittaa asiasta.
     */
    public void naytaTulokset() {
        if (tuloslista.size() > 0) {
            JOptionPane.showMessageDialog(null, tulostaKaikki());
        } else {
            JOptionPane.showMessageDialog(null, "Ei vielä tuloksia!");
        }
    }
}
