package entities;

public class Modul {

    private int modulnr;
    private String titel;

    public Modul() {
    }

    public Modul(int modulnr, String titel) {
        super();
        this.modulnr = modulnr;
        this.titel = titel;
    }

    public int getModulnr() {
        return modulnr;
    }

    public void setModulnr(int modulnr) {
        this.modulnr = modulnr;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    @Override
    public int hashCode() {
        return this.modulnr;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Modul other = (Modul) obj;
        if (modulnr != other.modulnr) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modul [modulnr=" + modulnr + ", titel=" + titel + "]";
    }

}
