package Miinaharava.logiikka;

/**
 * Pelin yksittäiset ruudut. Määrittelee ruutujen tilan.
 *
 */
public class Ruutu {

    private int x;
    private int y;
    private int ymparoivatMiinatLkm;
    private boolean pommi;
    private boolean avattu;
    private boolean lippu;

    /**
 * Konstruktori. Määrittelee miinan, avatun ruudun liputuksen tilaksi false. 
 *
 */
    public Ruutu(int x, int y) {
        this.x = x;
        this.y = y;
        this.pommi = false;
        this.avattu = false;
        this.lippu = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getYmparoivatMiinatLkm() {
        return ymparoivatMiinatLkm;
    }

    public void setMiina(boolean b) {
        pommi = b;
    }

    public void setAvattu(boolean b) {
        this.avattu = b;
    }

    public void setLippu(boolean b) {
        this.lippu = b;
    }

    public boolean getMiina() {
        return this.pommi;
    }

    public boolean getLippu() {
        return this.lippu;
    }

    public boolean getAvattu() {
        return this.avattu;
    }

    /**
     * Metodi asettaa ympäroivien miinojen lukumäärän. Jos ruudussa on pommi, se
     * saa arvoksi 99.
     *
     * @param lkm miinojen määrä
     */
    public void setYmparoivienLkm(int lkm) {
        if (pommi == true) {
            this.ymparoivatMiinatLkm = 99; // pommi
        } else {
            this.ymparoivatMiinatLkm = lkm;
        }
    }

    /**
     * Testikäyttöön tekstipohjaista pelilautaa varten.
     *
     */
    @Override
    public String toString() {
        if (this.pommi == true) {
            return "*";
        } else {
            return "" + ymparoivatMiinatLkm;
        }
    }
}
