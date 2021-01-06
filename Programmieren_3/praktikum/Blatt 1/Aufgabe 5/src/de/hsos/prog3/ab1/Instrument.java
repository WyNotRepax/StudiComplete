package de.hsos.prog3.ab1;

public enum Instrument {
    SAXOPHON("/Baritone.wav"), SCHLAGZEUG("/Drum.wav"), AKKORDION("/Accordion.wav");

    private String audiodatei;

    Instrument(String audiodatei) {
        this.audiodatei = audiodatei;
    }

    public String getAudiodatei() {
        return audiodatei;
    }
}
