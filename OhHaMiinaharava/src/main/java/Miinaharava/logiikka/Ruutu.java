/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Miinaharava.logiikka;

/**
 *
 * @author Anssi
 */
public class Ruutu {

    private int x;
    private int y;
    private boolean pommi;
    private int pommienLkm;
    private boolean avattu;

    public Ruutu(int x, int y) {
        this.x = x;
        this.y = y;
        this.pommi = false;
        this.avattu = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPommienLkm() {
        return pommienLkm;
    }
        
    public void setPommi() {
        pommi = true;
    }
    
    public void poistaPommi() {  // uutta peliä varten
        pommi = false;
    }
    
    
    

    
//    ruutujen avaamista varten seuraavat kommentoinnilla poistetut metodit
    
//    public void setAvattu() {
//        this.avattu = true;
//    }
//    
//    public boolean getAvattu() {
//        return this.avattu;
//    }
    
    public void setYmparoivienLkm(int lkm) {
        if (pommi == true) {
            this.pommienLkm = 99;
        } else {
        this.pommienLkm = lkm;
        }
    }

    public boolean onkoPommi() {
        return this.pommi;
    }

    @Override
    public String toString() {          
        if (this.pommi == true) {
            return "*";
        } else {
            return "" + pommienLkm;
        }
    }

//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 53 * hash + this.x;
//        hash = 53 * hash + this.y;
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Ruutu other = (Ruutu) obj;
//        if (this.x != other.x) {
//            return false;
//        }
//        if (this.y != other.y) {
//            return false;
//        }
//        return true;
//    }
    
    
}
