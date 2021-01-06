package aktion;

import java.util.Set;

import zustand.Zustand;

public class Ausfuehrung {
	private Zustand zustand;
	private Set<Zustand> endzustaende;

	public Ausfuehrung(Zustand start, Set<Zustand> ende) {
		this.zustand = start;
		this.endzustaende = ende;
	}

	public boolean schritt(char ein) {
		boolean ok = true;
		switch (ein) {
		case 'a': {
			zustand = zustand.a();
			break;
		}
		case 'b': {
			zustand = zustand.b();
			break;
		}
		default: {
			System.out.println("nur a und b in Eingabe erlaubt");
			ok = false;
		}
		}
		// System.out.println(this + " " + ein + " " + ok);
		return ok;
	}

	public boolean imEndzustand() {
		return this.endzustaende.contains(this.zustand);
	}

	@Override
	public String toString() {
		return "Ausfuehrung [zustand=" + zustand + ", endzustaende=" + endzustaende + "]";
	}

}
