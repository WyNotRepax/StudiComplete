package de.hsos.prog3.ab1;

public class App {
    public static void main(String[] args) {
        Orchester orchester = new Orchester("testOrchester","/All_Together.wav");
        DirigentIn dirigentIn = new DirigentIn("testDirigentIn");
        orchester.addDirigentIn(dirigentIn);

        MusikerIn musikerIn1 = new MusikerIn("testMusikerInAkkordion",Instrument.AKKORDION);
        MusikerIn musikerIn2 = new MusikerIn("testMusikerInSaxophon",Instrument.SAXOPHON);
        MusikerIn musikerIn3 = new MusikerIn("testMusikerInSchlagzeug1",Instrument.SCHLAGZEUG);
        MusikerIn musikerIn4 = new MusikerIn("testMusikerInSchlagzeug2",Instrument.SCHLAGZEUG);

        orchester.addMusikerIn(musikerIn1);
        orchester.addMusikerIn(musikerIn2);
        orchester.addMusikerIn(musikerIn3);
        orchester.addMusikerIn(musikerIn4);

        orchester.proben();
        orchester.spielen();

        orchester.auftreten();
        orchester.spielen();

    }
}
