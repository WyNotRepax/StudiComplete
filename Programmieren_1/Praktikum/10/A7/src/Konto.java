
public class Konto {
	private double kontostand;
	private double kreditrahmen;

	public Konto() {
		this.kontostand = 0;
		this.kreditrahmen = 0;
	}

	public Konto(Konto k) {
		if(k == null){
			throw new NullPointerException();
		}
		this.kontostand = k.kontostand;
		this.kreditrahmen = k.kreditrahmen;
	}
	
	public Konto clone(){
		return new Konto(this);
	}

	public boolean equals(Konto k) {
		if (k == null) {
			return false;
		}
		return this.kontostand == k.kontostand;
	}

	@Override
	public String toString() {
		return String.valueOf(this.kontostand);
	}

	public void abheben(double d) {
		if (d > kontostand) {
			// Fehlermeldung
		}
		else if(d <= 0){
			// Fehlermeldung
		}
		else{
			kontostand -= d;
		}
	}
	
	public void einzahlen(double d){
		if(d <= 0){
			// Fehlermeldung
		}
		else{
			kontostand += d;
			if(kontostand > 10000){
				kreditrahmen = 3000;
			}
		}
	}
	
	public double getKontostand(){
		return this.kontostand;
	}
	
	public void überweisen(Konto k, double betrag){
		this.abheben(betrag);
		k.einzahlen(betrag);
	}
}
