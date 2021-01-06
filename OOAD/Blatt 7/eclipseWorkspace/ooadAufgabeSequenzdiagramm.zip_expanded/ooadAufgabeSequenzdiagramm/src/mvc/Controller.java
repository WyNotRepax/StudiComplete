package mvc;

import main.EinUndAusgabe;

public class Controller {

    private Model model;
    private String aendern;
    private EinUndAusgabe io;

    public Controller(Model model, String aendern, EinUndAusgabe io) {
        this.model = model;
        this.aendern = aendern;
        this.io = io;
    }

    public void eingabeSteuern() {
      System.out.print(this.aendern + ": ");
      this.model.wertAendern(this.io.leseInteger());
    }
}
