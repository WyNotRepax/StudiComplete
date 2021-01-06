package entity;

public class Abteilungswhiteboard extends Whiteboard {
	private int abteilungsnummer;
	private String abteilungsname;

	public Abteilungswhiteboard(int abteilungsnummer, String abteilungsname) {
		super();
		this.abteilungsnummer = abteilungsnummer;
		this.abteilungsname = abteilungsname;
	}

	public void neueInformation(String information) {
		this.observerInformieren(information);
	}

	@Override
	public String toString() {
		return String.format("Abteilung %s(%d): [%s]", this.abteilungsname, this.abteilungsnummer, super.toString());
	}
}
