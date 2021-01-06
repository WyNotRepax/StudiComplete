public class RundFunkEmpfangsGeraet {
	int lautstaerke;
	boolean eingeschaltet;

	RundFunkEmpfangsGeraet() {
		eingeschaltet = false;
		lautstaerke = 0;
	}

	/**
	 * verändere Lautstaerke um x nach oben oder unten, je nach Vorzeichen von x
	 */
	void volume(int x) {
		if (eingeschaltet) {
			if (x <= lautstaerke) {
				lautstaerke += x;
			}
		}
	}

	/** Erhöhe Lautstaerke um 1 */
	void lauter() {
		if (eingeschaltet) {
			lautstaerke++;
		}
	}

// getter und setter
	/** Vermindere Lautstaerke um 1. */
	void leiser() {
		if (eingeschaltet) {
			if (lautstaerke > 0) {
				lautstaerke--;
			}
		}
	}

	/** Schalte ein. */
	void an() {
		eingeschaltet = true;
	}

	/** Schalte aus */
	void aus() {
		eingeschaltet = false;
	}

	/** an oder aus? */
	boolean istAn() {
		return eingeschaltet;
	}
}