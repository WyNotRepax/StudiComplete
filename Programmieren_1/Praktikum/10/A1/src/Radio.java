
public class Radio extends RundFunkEmpfangsGeraet{
	double frequenz;
	
	public Radio() {
		super();
		frequenz = 87.5;
	}
	
	void waehleSender(double newFrequenz){
		if(eingeschaltet) {
			frequenz = newFrequenz;
		}
	}
}
