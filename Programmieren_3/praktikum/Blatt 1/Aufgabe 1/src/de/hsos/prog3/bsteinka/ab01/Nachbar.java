package de.hsos.prog3.bsteinka.ab01;

import java.util.Objects;

public class Nachbar {
    private String vorname;
    private String nachname;

    Nachbar(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    @Override
    public String toString() {
        return this.vorname + " " + this.nachname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Nachbar)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        Nachbar nachbar = (Nachbar) o;
        return (this.vorname.equals(nachbar.vorname) && this.nachname.equals(nachbar.nachname));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.vorname, this.nachname);
    }
}
