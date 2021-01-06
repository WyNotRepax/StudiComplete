package mvc;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private int wert;
    private List<ModelListener> listener;

    public Model(int wert) {
        this.wert = wert;
        this.listener = new ArrayList<>();
    }

    public void listenerHinzufuegen(ModelListener m) {
        this.listener.add(m);
    }

    public int getWert() {
        return this.wert;
    }

    public void wertAendern(int wert) {
        this.wert = wert;
        for (ModelListener m : this.listener) {
            m.wertaenderung();
        }
    }
}
