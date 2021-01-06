
public class RailVehicle {
	int length;

	Car next = null;

	protected RailVehicle(int length) {
		this.length = length;
	}

	String print() {
		if (next == null) {
			return this.toString();
		}
		return this.toString() + "\n" + next.print();
	}

	void add(Car c) {
		if (next == null) {
			next = c;
		} else {
			next.add(c);
		}
	}

	int getAllPassengers() {
		if (next == null) {
			return 0;
		}
		return 0 + next.getAllPassengers();
	}

	int getFullLength() {
		if (next == null) {
			return length;
		}
		return length + next.length;
	}
}
