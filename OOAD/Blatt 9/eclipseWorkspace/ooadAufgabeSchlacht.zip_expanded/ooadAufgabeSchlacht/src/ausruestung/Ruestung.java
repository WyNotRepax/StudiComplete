/*
 * Author: Benno Steinkamp
 * Date: 14.12.2020
 */
package ausruestung;

public class Ruestung extends Ausruestung {
	private int schutz;

	public Ruestung(String name, int schutz, int preis) {
		super(name, preis);
		this.setSchutz(schutz);
	}

	public int abwehr(int angriff) {
		return Math.max(0, angriff - this.getSchutz());
	}

	public int getSchutz() {
		return schutz;
	}

	public void setSchutz(int schutz) {
		this.schutz = Math.max(0, Math.min(schutz, 10));
	}

}
