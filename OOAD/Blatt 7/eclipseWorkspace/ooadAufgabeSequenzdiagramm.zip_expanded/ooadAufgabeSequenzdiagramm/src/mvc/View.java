package mvc;

public class View implements ModelListener {

    private Model model;
    private char zeichen;

    public View(Model model, char zeichen) {
        this.model = model;
        this.zeichen = zeichen;
        model.listenerHinzufuegen(this);
    }

    @Override
    public void wertaenderung() {
        int wert = this.model.getWert();
        for (int i = 0; i < wert; i++) {
            System.out.print(this.zeichen);
        }
        System.out.println();
    }

}
