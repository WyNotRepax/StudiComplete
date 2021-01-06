
public class Taschenrechner {

	int addiere(int a, int b) {
		return a + b;
	}

	int subtrahiere(int a, int b) {
		return a - b;
	}

	int multipliziere(int a, int b) {
		return a * b;
	}

	int dividiere(int a, int b) {
		return (b != 0) ? a / b : 0;
	}

	int modulo(int a, int b) {
		if (b == 0) {
			return 0;
		}
		return a % b;
	}
}
