
public class A7 {
	public static void main(String[] args) {
		int input = 0;
		do {
			input = IO.readInt("Anzahl Downloads (>=0): ");
		} while (input < 0);
		long cents = 0;
		for (int i = 1; i <= input; i++) {
			if (i <= 5) {
				cents += 0;
			} else if (i <= 35) {
				cents += 20;
			} else {
				cents += 15;
			}
			// IO.println(cents);
		}
		long euros = cents / 100;
		cents = cents % 100;

		IO.println("Gebuehren = " + euros + "." + cents + " EURO ");
	}
}
