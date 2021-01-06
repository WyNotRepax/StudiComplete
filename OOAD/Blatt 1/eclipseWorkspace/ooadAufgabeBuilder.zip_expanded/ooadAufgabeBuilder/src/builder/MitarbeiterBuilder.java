package builder;

import java.util.HashSet;
import java.util.Set;

import entity.Fachgebiet;
import entity.Mitarbeiter;

public class MitarbeiterBuilder {

	public static MitarbeiterBuilder createBuilder() {
		return new MitarbeiterBuilder();
	}

	private String mitarbeiterVorname;
	private String mitarbeiterNachname;
	private Set<Fachgebiet> mitarbeiterFachgebiete;

	private MitarbeiterBuilder() {
		mitarbeiterVorname = "Eva";
		mitarbeiterNachname = "Mustermann";
		mitarbeiterFachgebiete = new HashSet<Fachgebiet>();
	}

	public MitarbeiterBuilder vorname(String vorname) {
		mitarbeiterVorname = vorname;
		return this;
	}

	public MitarbeiterBuilder nachname(String nachname) {
		mitarbeiterNachname = nachname;
		return this;
	}

	public MitarbeiterBuilder mitFachgebiet(Fachgebiet fachgebiet) {
		if (!mitarbeiterFachgebiete.contains(fachgebiet)) {
			mitarbeiterFachgebiete.add(fachgebiet);
			if (mitarbeiterFachgebiete.size() > 3) {
				mitarbeiterFachgebiete.remove(fachgebiet);
				throw new IllegalArgumentException("Maximal 3 Fachgebiete");
			}
		}
		return this;
	}

	public Mitarbeiter build() {
		Mitarbeiter mitarbeiter = new Mitarbeiter(mitarbeiterVorname, mitarbeiterNachname);
		mitarbeiter.setFachgebiete(mitarbeiterFachgebiete);
		return mitarbeiter;
	}
}
