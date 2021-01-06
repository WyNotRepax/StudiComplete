
public class A9 {
	public static void main(String[] args) {
		int a = 5;
		int b = 0;
		System.out.println(a + " + " + b + " = " + addiere(a, b));
		System.out.println(a + " - " + b + " = " + subtrahiere(a, b));
		System.out.println(a + " * " + b + " = " + multipliziere(a, b));
		System.out.println(a + " / " + b + " = " + dividiere(a, b) + " Rest " + modulo(a, b));
	}

	static int addiere(int a, int b) {
		return a + b;
	}

	static int subtrahiere(int a, int b) {
		return a - b;
	}

	static int multipliziere(int a, int b) {
		return a * b;
	}

	static int dividiere(int a, int b) {
		return (b != 0) ? a / b : 0;
	}

	static int modulo(int a, int b) {
		if (b == 0) {
			return 0;
		}
		return a % b;
	}
}
