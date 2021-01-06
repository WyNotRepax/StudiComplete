
public class Reaktionsspiel {
	public static void main(String[] args) {
		int roundNum = getRandomNumber(5) + 5;
		int mistakes = 0;
		long slowest = 0;
		long fastest = getMaxLongNumber();
		long avg = 0;
		IO.println("Achtung: Start!");
		for (int round = 1; round <= roundNum; round++) {
			long time = playRound();
			if (time == -1) {
				mistakes++;
			} else {
				if (time < fastest) {
					fastest = time;
				}
				if (time > slowest) {
					slowest = time;
				}
				avg = ((round - 1) * avg + time) / round;
			}
			//IO.println("t: " + time + " f: " + fastest + " s: " + slowest + " avg: " + avg);
		}
		IO.println("Geschafft: Ende!");
		IO.println("Fehlversuche: " + mistakes + " von " + roundNum);
		IO.println("Reaktionszeit-Mittelwert: " + (avg / 1000.0) + " Sekunden");
		IO.println("Langsamster Versuch: " + (slowest / 1000.0) + " Sekunden");
		IO.println("Schnellster Versuch: " + (fastest / 1000.0) + " Sekunden");

	}

	public static long playRound() {
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		// char[] alphabet = "1234567890".toCharArray();
		wait(getRandomNumber(3) + 2);
		char letter = alphabet[getRandomNumber(alphabet.length - 1)];
		IO.println(letter);
		long starttime = getMilliSeconds();
		char pressed = IO.readChar();
		long endtime = getMilliSeconds();
		if (letter != pressed) {
			return -1;
		}
		return endtime - starttime;
	}

	static long getMilliSeconds() {
		return new java.util.Date().getTime();
	}

	// liefert eine Zufallszahl zwischen 0 und max (einschlie�lich)
	static int getRandomNumber(int max) {
		return new java.util.Random().nextInt(max + 1);
	}

	// h�lt das Programm seconds Sekunden an
	static void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception exc) {
		}
	}

	// liefert den gr��t-m�glichen long-Wert
	static long getMaxLongNumber() {
		return Long.MAX_VALUE;
	}
}
