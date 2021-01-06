package de.hsos.prog3.ab1;


public class MusikerIn extends Mitglied {
    private Instrument instrument;

    MusikerIn(String name, Instrument instrument) {
        super(name);
        this.instrument = instrument;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof MusikerIn)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        MusikerIn musikerIn = (MusikerIn) o;
        return (this.instrument == musikerIn.instrument) && (super.equals(o));
    }
}
