
public class Mathetrainer {
	public static void main(String[] args) {
		int korrekt = 0;
		IO.println("Start des Mathetrainers");
		while (korrekt < 10) {
			if (frageBearbeiten()) {
				korrekt++;
			}
			IO.println("Korrekte Antworten: " + korrekt);
		}
		IO.println("Ende des Mathetrainers");
	}

	public static boolean frageBearbeiten() {
		int z1 = generiereZahl();
		int z2 = generiereZahl();
		char op = generiereOperator();
		int lsg;
		while (op == '/' && z2 == 0) {
			z2 = generiereZahl();
		}
		if (op == '*') {
			lsg = z1 * z2;
		} else {
			lsg = z1 / z2;
		}
		int input = IO.readInt("" + z1 + " " + op + " " + z2 + " = ");
		if (input == lsg) {
			IO.println("Richtig!");
			return true;
		}
		IO.println("Leider falsch! Korrektes Ergebnis ist " + lsg);
		return false;

	}

	public static int generiereZahl() {
		return (int) (Math.random() * 10);
	}

	public static char generiereOperator() {
		if (Math.random() > 0.5) {
			return '*';
		}
		return '/';
	}
}
