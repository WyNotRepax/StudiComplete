package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mvc.Controller;
import mvc.Model;
import mvc.View;

public class Main {

    private Model model;
    private List<Controller> controller;
    private EinUndAusgabe io;

    public Main() {
        //Locale.setDefault(new Locale("en","US")); 
        this.model = new Model(1);
        this.controller = new ArrayList<>();
        this.io = new EinUndAusgabe();
        int eingabe = -1;
        while (eingabe != 0) {
            System.out.println(Messages.getString("Main.0") //$NON-NLS-1$
                    + Messages.getString("Main.1") //$NON-NLS-1$
                    + Messages.getString("Main.2") //$NON-NLS-1$
                    + Messages.getString("Main.3") //$NON-NLS-1$
                    + Messages.getString("Main.4")); //$NON-NLS-1$
            eingabe = this.io.leseInteger();
            switch (eingabe) {
                case 1: {
                    this.neuerView();
                    break;
                }
                case 2: {
                    this.neuerController();
                    break;
                }
                case 3: {
                    this.controllerNutzen();
                    break;
                }
            }
        }
    }

    private void neuerView() {
        System.out.print(Messages.getString("Main.6")); //$NON-NLS-1$
        new View(this.model, this.io.leseString().charAt(0));
    }

    private void neuerController() {
        System.out.print(Messages.getString("Main.8")); //$NON-NLS-1$
        String aufforderung = this.io.leseString();
        this.controller.add(new Controller(this.model, aufforderung, this.io));
    }

    private void controllerNutzen() {
        if (!this.controller.isEmpty()) {
            System.out.print(Messages.getString("Main.9") //$NON-NLS-1$
                    + this.controller.size() + ")? "); //$NON-NLS-1$
            this.controller.get((this.io.leseInteger()) - 1)
                      .eingabeSteuern();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
