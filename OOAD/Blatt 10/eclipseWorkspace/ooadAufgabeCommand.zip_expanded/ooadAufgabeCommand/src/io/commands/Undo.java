package io.commands;

import business.Rechner;

public class Undo implements Command {
	
	private final int altAnzeige;
	private final int altSpeicher;
	
	private Rechner rechner;
	
	public Undo(Rechner rechner) {
		this.altAnzeige = rechner.getAnzeige();
		this.altSpeicher = rechner.getSpeicher();
		this.rechner = rechner;
	}

	@Override
	public Command execute() {
		Command ret = new  Undo(this.rechner);
		rechner.setAnzeige(this.altAnzeige);
		rechner.setSpeicher(this.altSpeicher);
		return ret;
	}

}
