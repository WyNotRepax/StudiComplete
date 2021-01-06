
public class A1 {
	public static void main(String[] args) {
		for(int i = -2; i < 10; i++) {
			funcIf(i);
			funcSwitch(i);
			System.out.println("");
		}
	}

	static void funcIf(int zahl) {
		if (zahl > 3 && zahl < 7) {
			if (zahl > 4 && zahl < 10) {
				System.out.println("ja");
			} else {
				System.out.println("weiss nicht");
			}
		} else if (zahl >= -1 && zahl <= 1) {
			System.out.println("nein");
		} else {
			System.out.println("vielleicht");
		}
	}

	static void funcSwitch(int zahl) {
		switch (zahl) {
		case -1:
		case 0:
		case 1:
			System.out.println("nein");
			break;
		case 4:
			System.out.println("weiss nicht");
			break;
		case 5:
		case 6:
			System.out.println("ja");
			break;
		default:
			System.out.println("vielleicht");
			break;
		}
	}
}
