package visitor;

import entity.Feststoff;
import entity.Fluessigkeit;
import interfaces.Visitor;
import interfaces.Zutat;

public class VisitorSI implements Visitor {

	@Override
	public String visit(Zutat zutat) {
		if (zutat instanceof Feststoff) {
			return this.visit((Feststoff) zutat);
		}
		if (zutat instanceof Fluessigkeit) {
			return this.visit((Fluessigkeit) zutat);
		}
		System.err.printf("VisitorSI unterstützt nur Feststoffe (%s), und Fluessigkeiten (%s)\n",
				Feststoff.class.getCanonicalName(), Fluessigkeit.class.getCanonicalName());
		return "";
	}

	private String visit(Fluessigkeit fluessigkeit) {
		return String.format("%s: %d ml",fluessigkeit.getName(),fluessigkeit.getMl());

	}

	private String visit(Feststoff feststoff) {
		return String.format("%s: %d g",feststoff.getName(),feststoff.getGramm());
	}

}
