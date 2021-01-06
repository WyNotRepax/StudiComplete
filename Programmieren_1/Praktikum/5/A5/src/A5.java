
public class A5 {
	static String[] cities;
	static int[][][] connections;

	public static void main(String[] args) {
		IO.println("Vorbereitung:");
		int num = IO.readInt("Anzahl Städte: ");
		cities = new String[num];
		for (int i = 0; i < cities.length; i++) {
			cities[i] = IO.readString("Stadt " + (i + 1) + "\n");
		}
		int connectionNum = IO.readInt("Anzahl Direktverbindungen: ");
		connections = new int[num][num][num];
		for (int i = 0; i < connectionNum; i++) {
			IO.println("Direktverbindung " + (i + 1));
			int startIndex = -1;
			do {
				startIndex = indexOf(IO.readString("Start: "));
			} while (startIndex < 0);
			int endIndex = -1;
			do {
				endIndex = indexOf(IO.readString("Ziel: "));
			} while (endIndex < 0);
			connections[0][startIndex][endIndex] = 1;
			connections[0][endIndex][startIndex] = 1;
		}
		for (int n = 1; n < num; n++) {
			connections[n] = mtrxMult(connections[n - 1], connections[0]);
		}
		while (true) {
			IO.println("Auskunft");
			int startIndex = -1;
			do {
				startIndex = indexOf(IO.readString("Start: "));
			} while (startIndex < 0);
			int targetIndex = -1;
			do {
				targetIndex = indexOf(IO.readString("Ziel: "));
			} while (targetIndex < 0);
			boolean connection = false;
			for (int n = 0; n < num; n++) {
				if (connections[n][startIndex][targetIndex] > 0) {
					connection = true;
					break;
				}
			}
			if (connection) {
				IO.println("Zwischen " + cities[startIndex] + " und " + cities[targetIndex]
						+ " ist eine Verbindung vorhanden");
			} else {
				IO.println("Zwischen " + cities[startIndex] + " und " + cities[targetIndex]
						+ " ist eine Verbindung nicht vorhanden");
			}
		}

	}

	public static void main_(String[] args) {
		int[][] a = { { 0, 0, 1 }, { 0, 0, 1 }, { 1, 0, 0 } };
		arrPrint(a);
		a = mtrxMult(a, a);
		arrPrint(a);
	}

	static int indexOf(String s) {
		for (int i = 0; i < cities.length; i++) {
			if (cities[i].equals(s)) {
				return i;
			}
		}
		return -1;
	}

	static int[][] mtrxMult(int[][] a, int[][] b) {
		int l = a.length;
		int[][] ret = new int[l][l];
		for (int i = 0; i < l; i++) {
			for (int j = 0; j < l; j++) {
				for (int n = 0; n < l; n++) {
					ret[i][j] += a[i][n] * b[n][j];
				}
			}
		}
		return ret;
	}

	static void arrPrint(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				IO.print(arr[i][j]);
			}
			IO.print("\n");
		}
	}

}
