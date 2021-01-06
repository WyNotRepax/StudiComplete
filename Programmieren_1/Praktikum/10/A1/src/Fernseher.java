
public class Fernseher extends RundFunkEmpfangsGeraet{
	int kanal;
	
	public Fernseher() {
		super();
		kanal = 1;
	}
	
	void waehleKanal(int newKanal) {
		if(eingeschaltet) {
			kanal = newKanal;
		}
	}
}
