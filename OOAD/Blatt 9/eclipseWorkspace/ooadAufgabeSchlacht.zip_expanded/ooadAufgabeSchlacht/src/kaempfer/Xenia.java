/**
 * Author: Benno Steinkamp
 * Date: 14.12.2020
 */
package kaempfer;

import ausruestung.Ruestung;
import ausruestung.Waffe;

public class Xenia extends Kaempfer {

	private Waffe waffe;
	private Ruestung ruestung;

	public Xenia(int gesundheit, int geschick, int sold) {
		super(gesundheit, geschick, sold);
	}

	@Override
	public void nimmWaffe(Waffe w) {
		this.waffe = w;
	}

	@Override
	public void nimmRuestung(Ruestung r) {
		this.ruestung = r;

	}

	public Waffe getWaffe() {
		return waffe;
	}

	public void setWaffe(Waffe waffe) {
		this.waffe = waffe;
	}

	public Ruestung getRuestung() {
		return ruestung;
	}

	public void setRuestung(Ruestung ruestung) {
		this.ruestung = ruestung;
	}

	@Override
	public int kaempfen() {
		System.out.println("Xenia haut zu");
		if (waffe == null) {
			return this.getGeschick();
		}
		return this.waffe.zuhauen(this.getGeschick());
	}

	@Override
	public int abwehren(int angriff) {
		if (this.ruestung != null) {
			angriff = ruestung.abwehr(angriff);
		}
		return super.abwehren(angriff);
	}

	@Override
	public String toString() {
		return String.format("Xenia (Sold:%d)", this.getSold());
	}

}
