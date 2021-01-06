
public class Survey {
	private int qNum;
	private Question[] questions;
	private String titel;

	public Survey() {
		IO.println("Fragen eingeben");
		IO.println("---------------");
		titel = IO.readString("Titel der Umfrage:");
		qNum = IO.readInt("Anzahl Fragen: ");
		questions = new Question[qNum];
		for (int i = 0; i < qNum; i++) {
			questions[i] = new Question(IO.readString("Frage " + (i + 1)));
		}
	}

	public void start() {
		for (int i = 0; i < qNum; i++) {
			IO.println("Frage " + (i + 1) + ":");
			IO.println(questions[i].ask());
			while (!questions[i].answer(IO.readString("ja/nein (j/n)?")))
				;
		}
		String next = IO.readString("Weiterer Teilnehmer (j/n)?");
		switch (next.toLowerCase()) {
		case "j":
		case "ja":
		case "y":
		case "yes":
			this.start();
			break;
		default:
			break;
		}
	}

	public void evaluate() {
		IO.println("Umfrageergebnisse");
		IO.println("-----------------");
		IO.println("Umfrage: " + titel);
		for (int i = 0; i < qNum; i++) {
			IO.println("Frage: " + questions[i].ask());
			IO.println("ja-Antworten: " + questions[i].getYesNum() + " = " + questions[i].getYesPercent() + "%");
			IO.println("nein-Antworten: " + questions[i].getNoNum() + " = " + questions[i].getNoPercent() + "%");
		}
	}

	// Moeglicherweise in eigene Datei verschieben?
	public static void main(String[] args) {
		Survey s = new Survey();
		s.start();
		s.evaluate();
	}

}
