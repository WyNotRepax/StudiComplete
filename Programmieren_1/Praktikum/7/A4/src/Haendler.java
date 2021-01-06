
public class Haendler {
	private double preis;
	private double einnahmen;
	
	public Haendler(double preis) {
		this.preis = preis;
		this.einnahmen = 0;
	}
	
	public void kaufen(int anzahl){
		einnahmen += anzahl * preis;
	}
	
	public double liefereEinnahmen(){
		return einnahmen;
	}
}
