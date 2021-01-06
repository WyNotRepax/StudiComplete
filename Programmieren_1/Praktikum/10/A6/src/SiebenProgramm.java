
public class SiebenProgramm extends SiebenSpieler {

	public SiebenProgramm(String name) {
		super(name);
	}
	
	@Override
	public int naechsteZahl(int vorherigeZahl){
		int rightAns = vorherigeZahl + 1;
		while (rightAns % 7 == 0 || Integer.valueOf(rightAns).toString().contains("7")) {
			rightAns++;
		}
		IO.println(this.toString() +  ": Zahl nach " + vorherigeZahl + ": " + rightAns);
		return rightAns;
	}

}
